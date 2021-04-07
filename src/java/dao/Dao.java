/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;

/**
 *
 
 */
public class Dao{
    private Connection conn = null;

    public Connection getConn() {
        if(conn == null)
            conn = DBConnection.getConnection();
        
        return conn;
    }    
}
