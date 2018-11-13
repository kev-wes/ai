package org.camunda.bpm.aibot.auto_response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class DB_connector {

	
    private Connection connect() throws ClassNotFoundException {
    	// load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        // SQLite connection string
        String url = "jdbc:sqlite:C://Users/Administrator/Desktop/Database/aibot_database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public void insertInitialMail(String original_text, String sender_address, String recipient_address) {
        String sql = "INSERT INTO mails(original_text,mail_timestamp,sender_address,recipient_address) VALUES(?,?,?,?)";
 
        try {
        	Connection conn = this.connect();
        	PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, original_text);
            // Get current time stamp
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            pstmt.setString(2, timestamp.toString());
            pstmt.setString(3, sender_address);
            pstmt.setString(4, recipient_address);            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    public int getMaxId(){
    	 String sql = "SELECT max(id) AS max_id FROM mails";
    	 int id = 0;
         try (Connection conn = this.connect();
              Statement stmt  = conn.createStatement();
              ResultSet rs    = stmt.executeQuery(sql)){
             
             // loop through the result set
             while (rs.next()) {
                 id = (rs.getInt("max_id"));
             }
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
         catch (ClassNotFoundException e) {
             System.out.println(e.getMessage());
         }
         return id;
    }
}
