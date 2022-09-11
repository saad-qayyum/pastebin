import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class user{
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
    PrintWriter out = res.getWriter();

HttpSession session = req.getSession(false);
    
    switch(subPath){
      case "/signup":
      if(session != null && (Integer)session.getAttribute("user") != null)res.sendRedirect("/pastebin/app/u/profile");
      RequestDispatcher rd = req.getRequestDispatcher("/auth.jsp");	
	    rd.forward(req,res);
      break;
      case "/login":
      if(session != null && (Integer)session.getAttribute("user") != null)res.sendRedirect("/pastebin/app/u/profile");
      rd = req.getRequestDispatcher("/auth.jsp");	
      req.setAttribute("page","Login");
	    rd.forward(req,res);
      break;
      case "/logout":
      session.removeAttribute("user");
      session.removeAttribute("type");
      res.sendRedirect("/pastebin/app/");
      break;
      default:
      out.println(subPath);
    }
}


public static void handlePostRequests(HttpServletRequest req, HttpServletResponse res,String subPath) throws ServletException, IOException,SQLException,ClassNotFoundException {
PrintWriter out = res.getWriter();
HttpSession session = req.getSession(false);
  switch(subPath){
      case "/signup":
      if(session != null && (Integer)session.getAttribute("user") != null)res.sendRedirect("/pastebin/app/u/profile");
        UserService.saveUser(req,res);
      break;
      case "/login":
      if(session != null && (Integer)session.getAttribute("user") != null)res.sendRedirect("/pastebin/app/u/profile");
        UserService.loginUser(req,res);
      break;
      
      
       
    }
}



}