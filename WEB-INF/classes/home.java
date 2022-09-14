import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class home{
public static void handleRequest(HttpServletRequest req, HttpServletResponse res,String subPath) throws ServletException, IOException,SQLException,ClassNotFoundException {
PrintWriter out = res.getWriter();
String method = req.getMethod();

 switch(method){
    case "GET":
    handleGetRequests(req,res,subPath);
    break;
    case "POST":
    handlePostRequests(req,res,subPath);       
    break;
    default:
    out.println("404");    
 }
}



public static void handleGetRequests(HttpServletRequest req, HttpServletResponse res,String subPath) throws ServletException, IOException, SQLException,ClassNotFoundException {
    switch(subPath){
      case "/":
      RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");	
	    rd.forward(req,res);
      break;
      default:
      HomeService.showPaste(req,res,subPath.replaceAll("/",""));

    }
}


public static void handlePostRequests(HttpServletRequest req, HttpServletResponse res,String subPath) throws ServletException, IOException,SQLException,ClassNotFoundException {
PrintWriter out = res.getWriter();
  switch(subPath){
      case "/":
        HomeService.savePaste(req,res);
      // RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");	
	    // rd.forward(req,res);
      break;
      default:
        HomeService.savePaste(req,res);
    }
}



}