package dao;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;


public class DB {
  private String url;
  private Connection con;
  private Statement st;

 public DB() throws IOException,SQLException,ClassNotFoundException{
      Class.forName("com.mysql.jdbc.Driver");
      url = "jdbc:mysql://127.0.0.1/paste";
      con=DriverManager.getConnection(url, "root", "");
      st=con.createStatement();
  }

public int insert(String tableName,String keys, String values) throws IOException {
  try{
     String query = "INSERT INTO "+tableName+" ("+keys+") VALUES ("+values+")";
     return st.executeUpdate( query );
    }catch(Exception e){
      return -1;
    }
}  
  

public int delete(String tableName,String condition) throws IOException {
  try{
     String query = "Delete from "+tableName+" where "+condition+"";
     return st.executeUpdate( query );
    }catch(Exception e){
      return -1;
    }
} 

public int update(String tableName,String values,String condition) throws IOException {
  try{
     String query = "Update "+tableName+" SET "+values+" where "+condition+"";
     return st.executeUpdate( query );
    }catch(Exception e){
      return -1;
    }
} 
  
  public ResultSet select(LinkedHashMap<String, String> queryMap) throws IOException, Exception {
    String query = "";
  try{
     for (String i : queryMap.keySet()) {
         query +=" "+i+" "+ (String)queryMap.get(i)+" ";
    } 
     return st.executeQuery( query );
    }catch(Exception e){
      throw new Exception("Error occured while retrieving data form db.");
    }

  }
  
}
