/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neeraj.sync.main;

import com.google.gson.Gson;
import com.neeraj.sync.main.database.ScoreVO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.neeraj.sync.main.database.DbHelper;
import java.util.List;

/**
 *
 * @author n33raj
 */
public class InsertScoresServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            try {
                if (sb.length() > 0) {
                    Connection conn = DbHelper.getDBConnection();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<ScoreVO>>() {
                    }.getType();
                    List<ScoreVO> localList = gson.fromJson(sb.toString(), listType);
                    int i = 0;
                    for (ScoreVO item : localList) {
                        try {
                            String query = null;
                            if (item.getDirty() == 0) {
                                //INSERT
                                query = "insert into score_tbl (id, name, score, dirty) values (" + item.getId() + ",'" + item.getName() + "', '" + item.getScore() + "', 0)";
                            } else if (item.getDirty() == 1) {
                                //DELETE
                                query = "DELETE FROM score_tbl WHERE ID = " + item.getId();
                            } else if (item.getDirty() == 2) {
                                //UPDATE
                                query = "UPDATE score_tbl SET id = '" + item.getId() + "', name = '" + item.getName() + "', score = '" + item.getScore() + "', dirty = 0";
                            }
                            if (query != null) {
                                i += DbHelper.insert(conn, query);
                            }
                        } catch (SQLException e) {
                            System.out.println("Duplicate id#" + item.getId());
                            e.printStackTrace();
                        }
                    }
                    conn.close();
                    out.println(i);
                } else {
                    out.println("-1");
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InsertScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error: unable to load driver class!");
                out.println("-1");
                System.exit(1);
            } catch (SQLException ex) {
                Logger.getLogger(InsertScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error: SQLException!");
                out.println("1");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
