package controllers;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import dao.*;

public class MainController extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      handleRequest(request, response);
    } catch (Exception e) {
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      handleRequest(request, response);
    } catch (Exception e) {
    }
  }

  public void handleRequest(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException, SQLException, ClassNotFoundException ,Exception{
    PrintWriter out = res.getWriter();
    HttpSession session = req.getSession(false);
    if (session != null) {
      int user = 0;
      if (session != null && (Integer) session.getAttribute("user") != null) {
        user = (Integer) session.getAttribute("user");
        if(user != 0){
          DB db = new DB();
          LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
          map.put("select", "*");
          map.put("from", "user");
          map.put("where", "id = "+user);
          ResultSet rs = db.select(map);
          if(!rs.next()){
            session.invalidate();
            res.sendRedirect("/pastebin/app/");
          }
        }
      }
    }
    try {
      String path = req.getRequestURI();
      String pathChanged = (path.indexOf("app") >= 0) ? path.replaceAll("/pastebin/app", "")
          : path.replaceAll("/pastebin", "");
      int idx = pathChanged.indexOf("/", 1);
      String subPath = pathChanged;
      if (idx != -1)
        subPath = pathChanged.substring(0, idx);
      subPath = subPath.equals("") ? "/" : subPath;
      switch (subPath) {
        case "/":
          home.handleRequest(req, res, subPath);
          break;
        case "/u":
          String temp = pathChanged.replaceFirst("/u", "");
          String route = temp.equals("") ? "/" : temp;
          user.handleRequest(req, res, route);
          break;
        default:
          home.handleRequest(req, res, subPath);

      }

    }

    catch (Exception e) {
      out.println(e);
    }
  }
}
