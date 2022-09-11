import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;

public class MainController extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    try{
    handleRequest(request,response);
    }
    catch(Exception e){
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
try{
    handleRequest(request,response);
    }
    catch(Exception e){
    }  }

  public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException,SQLException,ClassNotFoundException{
    String path = req.getRequestURI();
    String pathChanged = (path.indexOf("app")>=0)? path.replaceAll("/pastebin/app", ""):path.replaceAll("/pastebin", "");
    int idx = pathChanged.indexOf("/",1);
    String subPath = pathChanged;
    if(idx != -1)
      subPath = pathChanged.substring(0,idx);
    PrintWriter out = res.getWriter();
    subPath = subPath.equals("")?"/":subPath;
    switch (subPath) {
         case "/":
              home.handleRequest(req,res,subPath);
            break;
         case "/u":
              String temp  = pathChanged.replaceAll("/u","");
              String route = temp.equals("")?"/":temp;
              user.handleRequest(req,res,route);
            break;
         case "/admin":
              out.println("in admin");
            break;
        default:
              home.handleRequest(req,res,subPath);

        }
 
  }

}
