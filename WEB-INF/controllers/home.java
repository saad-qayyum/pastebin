package controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class home{

public static void main(String args[]){

}
public static void handleRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
PrintWriter out = res.getWriter();
out.println("in home");
String method = req.getMethod();
 switch(method){
    case "GET":

    break;
    case "POST":
    
    break;
    default:
    out.println("404")    
 }
}
}