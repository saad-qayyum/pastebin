import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import javax.swing.*;
import controller.home;

public class MainController extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    handleRequest(request,response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    handleRequest(request,response);
  }

  public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
    String path = req.getRequestURI();
    String pathChanged = path.replaceAll("/pastebin", "");
    int idx = pathChanged.indexOf("/",1);
    String subPath = pathChanged;
    if(idx != -1)
      subPath = pathChanged.substring(0,idx);
    PrintWriter out = res.getWriter();
    subPath = subPath.equals("")?"/":subPath;
    switch (subPath) {
         case "/":
              home.handleRequest(req,res);
            break;
         case "/u":
              out.println("in user");
            break;
         case "/admin":
              out.println("in admin");
            break;
        default:
            out.println("404");
        }
 
  }

}
