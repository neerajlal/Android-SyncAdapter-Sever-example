/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neeraj.sync.main;

import com.google.gson.Gson;
import com.neeraj.sync.main.database.DbHelper;
import com.neeraj.sync.main.database.ScoreVO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author n33raj
 */
public class GetAllScoresServlet extends HttpServlet {

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
            
            Connection conn = DbHelper.getDBConnection();
            ResultSet rs = DbHelper.select(conn, "select * from score_tbl");
            List<ScoreVO> localList = new ArrayList<ScoreVO>();    
            int i = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                int dirty = rs.getInt("dirty");
                localList.add(new ScoreVO(id, rs.getString("name"), rs.getString("score"), rs.getInt("dirty")));
                String query = null;
                if(dirty == 3){
                    query = "DELETE FROM score_tbl WHERE id = " + id;
                } else if(dirty == 4){
                    query = "UPDATE score_tbl SET dirty = 0 where id = " + id;
                }
                if(query != null){
                    i += DbHelper.update(conn, query);
                }
            }
            conn.close();
            out.println(new Gson().toJson(localList));
        } catch (SQLException ex) {
            Logger.getLogger(GetAllScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetAllScoresServlet.class.getName()).log(Level.SEVERE, null, ex);
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
