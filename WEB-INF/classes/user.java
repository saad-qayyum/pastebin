import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class user {

  public static void handleRequest(
    HttpServletRequest req,
    HttpServletResponse res,
    String subPath
  )
    throws ServletException, IOException, SQLException, ClassNotFoundException {
    PrintWriter out = res.getWriter();
    String method = req.getMethod();

    switch (method) {
      case "GET":
        handleGetRequests(req, res, subPath);
        break;
      case "POST":
        handlePostRequests(req, res, subPath);
        break;
      default:
        out.println("404");
    }
  }

  public static void handleGetRequests(
    HttpServletRequest req,
    HttpServletResponse res,
    String subPath
  )
    throws ServletException, IOException, SQLException, ClassNotFoundException {
    PrintWriter out = res.getWriter();
    HttpSession session = req.getSession(false);

    switch (subPath) {
      case "/":
        res.sendRedirect("/pastebin/app/");
        break;
      case "/signup":
        if (
          session != null && (Integer) session.getAttribute("user") != null
        ) res.sendRedirect("/pastebin/app/u/pastes");
        RequestDispatcher rd = req.getRequestDispatcher("/auth.jsp");
        rd.forward(req, res);
        break;
      case "/login":
        if (
          session != null && (Integer) session.getAttribute("user") != null
        ) res.sendRedirect("/pastebin/app/u/pastes");
        rd = req.getRequestDispatcher("/auth.jsp");
        req.setAttribute("page", "Login");
        rd.forward(req, res);
        break;
      case "/logout":
        session.removeAttribute("user");
        session.removeAttribute("type");
        res.sendRedirect("/pastebin/app/");
        break;
      default:
          String username = subPath.replaceAll("/","");
          if(username.equals("")) res.sendRedirect("/pastebin/app/");
          else
          UserService.showPastes(req,res,username);
    }
  }

  public static void handlePostRequests(
    HttpServletRequest req,
    HttpServletResponse res,
    String subPath
  )
    throws ServletException, IOException, SQLException, ClassNotFoundException {
    PrintWriter out = res.getWriter();
    HttpSession session = req.getSession(false);
    switch (subPath) {
      case "/signup":
        if (
          session != null && (Integer) session.getAttribute("user") != null
        ) res.sendRedirect("/pastebin/app/u/pastes");
        UserService.saveUser(req, res);
        break;
      case "/login":
        if (
          session != null && (Integer) session.getAttribute("user") != null
        ) res.sendRedirect("/pastebin/app/u/pastes");
        UserService.loginUser(req, res);
        break;
    }
  }
}
