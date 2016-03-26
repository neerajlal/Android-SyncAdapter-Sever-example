/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neeraj.sync.main.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author n33raj
 */
public class DbHelper {

    public static Connection getDBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Driver myDriver = new org.apache.derby.jdbc.ClientDriver();
        DriverManager.registerDriver(myDriver);
        return DriverManager.getConnection("jdbc:derby://localhost:1527/Score");
    }

    public static ResultSet select(Connection conn, String query) throws SQLException {
        return conn.createStatement().executeQuery(query);
    }

    public static int insert(Connection conn, String query) throws SQLException {
        return conn.createStatement().executeUpdate(query);
    }

    public static int delete(Connection conn, String query) throws SQLException {
        return conn.createStatement().executeUpdate(query);
    }

    public static int update(Connection conn, String query) throws SQLException {
        return conn.createStatement().executeUpdate(query);
    }
}
