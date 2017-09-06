/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fril;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class SqliteJDBC {

    public SqliteJDBC() {
    }
    
    public Connection connect(){
        Connection conn = null;     
        try {
            String url = "jdbc:sqlite:D:\\Frill.jpGR1\\FrilApp_GR1\\db\\fril.db";
            conn = DriverManager.getConnection(url);
            
        } catch (SQLException ex) {
            Logger.getLogger(SqliteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    public boolean checkIfTableAlreadyExists(){
        String sql = "SELECT name from sqlite_master WHERE type = \'table\' AND name = \'SellingItem\'";
        Connection conn = connect();
        if(conn != null){
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = null;
                rs = stmt.executeQuery(sql);
                String name = rs.getString(1);
                if(name != null){
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(SqliteJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    } 
    
    public void createSellingItemTable(){
        String sql = "CREATE TABLE SellingItem (id text PRIMARY KEY, name text, image_paths text, "
                + "category_id integer, size_id integer, brand_id integer, status integer, carriage integer,"
                + "delivery_method integer, delivery_date integer, delivery_area integer, request_required text, "
                + "detail text, sell_prize integer, start_time text, stop_time text, repeat_period text);";
        Connection conn = connect();
        if(conn != null){
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if(rs == null){
                    JOptionPane.showMessageDialog(null, "Create table failed !");
                }
            } catch (SQLException e) {
            }
        }
    }
    
    public void insertIntoSellingItemTable(String id, String name, String image_paths, int category_id, int size_id, 
            int brand_id, int status, int carriage, int delivery_method, int delivery_date, int delivery_area, 
            String request_required, String detail, int sell_prize, String start_time, String stop_time, String repeat_period){
        String sql = "INSERT INTO SellingItem(id, name, image_paths, category_id, size_id, brand_id, status, carriage, "
                + "delivery_method, delivery_date, delivery_area, request_required, detail, sell_prize, start_time, stop_time"
                + "repeat_period) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, image_paths);
            pstmt.setInt(4, category_id);
            pstmt.setInt(5, size_id);
            pstmt.setInt(6, brand_id);
            pstmt.setInt(7, status);
            pstmt.setInt(8, carriage);
            pstmt.setInt(9, delivery_method);
            pstmt.setInt(10, delivery_date);
            pstmt.setInt(11, delivery_area);
            pstmt.setString(12, request_required);
            pstmt.setString(13, detail);
            pstmt.setInt(14, sell_prize);
            pstmt.setString(15, start_time);
            pstmt.setString(16, stop_time);
            pstmt.setString(17, repeat_period);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }
    
    public void updateSellingItemTable(String id, String name, String image_paths, int category_id, int size_id, 
            int brand_id, int status, int carriage, int delivery_method, int delivery_date, int delivery_area, 
            String request_required, String detail, int sell_prize, String start_time, String stop_time, String repeat_period){
        String sql = "UPDATE SellingItem SET id = ?, name = ?, image_paths = ?, category_id = ?, size_id = ?, "
                + "brand_id = ?, status = ?, carriage = ?, delivery_method = ?, delivery_date = ?, delivery_area = ?,"
                + "request_required = ?, detail = ?, sell_prize = ?, start_time = ?, stop_time = ?, repeat_period = ?";
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, image_paths);
            pstmt.setInt(4, category_id);
            pstmt.setInt(5, size_id);
            pstmt.setInt(6, brand_id);
            pstmt.setInt(7, status);
            pstmt.setInt(8, carriage);
            pstmt.setInt(9, delivery_method);
            pstmt.setInt(10, delivery_date);
            pstmt.setInt(11, delivery_area);
            pstmt.setString(12, request_required);
            pstmt.setString(13, detail);
            pstmt.setInt(14, sell_prize);
            pstmt.setString(15, start_time);
            pstmt.setString(16, stop_time);
            pstmt.setString(17, repeat_period);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }
    
    public ResultSet getItemFromTable(String id){
        String sql = "SELECT * from SellingItem WHERE id = ?";
        ResultSet rs = null;
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, id);
            rs = pstmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SqliteJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}
