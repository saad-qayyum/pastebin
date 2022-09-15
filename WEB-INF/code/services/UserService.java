package services;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.*;
import dao.DB;
import dto.PasteDto;
import dto.UserDto;
import helpers.*;

public class UserService {

  public static void saveUser(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException, SQLException, ClassNotFoundException {
    DB db = new DB();
    HttpSession session = req.getSession(true);
    String error = "";
    int user = 0;
    if (session != null && (Integer) session.getAttribute("user") != null) {
      user = (Integer) session.getAttribute("user");
    }
    PrintWriter out = res.getWriter();
    String name = req.getParameter("name");
    String action = req.getParameter("action");
    error +=
      (name == null || name.equals("")) ? "Name should not be empty." : "";
    String password = md5.getMd5(req.getParameter("password"));
    error +=
      (!isValidPassword(req.getParameter("password")))
        ? ",Password is not strong enough."
        : "";

    if (action.equals("Edit Profile") && user != 0) {
      if (error.equals("")) {
        String tb = "user";
        String toUpdateValues =
          " name = '" + name + "' , password = '" + password + "'";
        String cond = " id = " + user;
        db.update(tb, toUpdateValues, cond);
        session.setAttribute("name", name);
        res.sendRedirect("/pastebin/app/u/profile");
      } else {
        session.setAttribute("error", error);
        res.sendRedirect("/pastebin/app/");
      }
    }

    String username = req.getParameter("username");
    error +=
      (username == null || username.equals(""))
        ? ",Username should not be empty."
        : "";

    //no username should repeat

    try {
      LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
      map.put("select", "*");
      map.put("from", "user");
      map.put("where", "username = '" + username + "'");
      ResultSet rs = db.select(map);
      if (rs.next()) {
        error += ",Username is already taken.";
        session.setAttribute("error", error);
        res.sendRedirect("/pastebin/app/u/signup");
      }
    } catch (Exception e) {
      //todo error page server failure
    }
    //end of no username repeat

    if (error.equals("")) {
      String table = "user";
      String keys = "name,username,type,password";
      String values =
        "'" + name + "'," + "'" + username + "'," + "1," + "'" + password + "'";
      int result = db.insert(table, keys, values);
      if (result == 1) {
        RequestDispatcher rd = req.getRequestDispatcher("/app/u/login");
        rd.forward(req, res);
      }
    }
  }

  public static void loginUser(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException, SQLException, ClassNotFoundException {
    DB db = new DB();
    String error = "";
    PrintWriter out = res.getWriter();
    String username = req.getParameter("username");
    String password = md5.getMd5(req.getParameter("password"));

    //no username should repeat
    HttpSession session = req.getSession(true);

    try {
      LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
      map.put("select", "*");
      map.put("from", "user");
      map.put(
        "where",
        "username = '" + username + "' and password ='" + password + "'"
      );
      ResultSet rs = db.select(map);
      if (rs.next()) {
        session.setAttribute("user", rs.getInt("id"));
        session.setAttribute("username", rs.getString("username"));
        session.setAttribute("role", rs.getInt("type"));
        session.setAttribute("name", rs.getString("name"));
        res.sendRedirect("/pastebin/app/u/" + username);
      } else {
        error += "Username or password is incorrect.";
        session.setAttribute("error", error);
        res.sendRedirect("/pastebin/app/u/login");
      }
    } catch (Exception e) {
      //todo error page server failure
    }
    //end of no username repeat

  }

  public static void showPastes(
    HttpServletRequest req,
    HttpServletResponse res,
    String username
  )
    throws ServletException, IOException, SQLException, ClassNotFoundException {
    DB db = new DB();
    String error = "";
    int isUserHimself = 0;
    String action = req.getParameter("action");
    HttpSession session = req.getSession(false);
    int user = 0;int type=1;
    if (
      session != null && (Integer) session.getAttribute("user") != null
    ){
      user = (Integer) session.getAttribute("user");
      type = (Integer) session.getAttribute("role");
    } 
    try {
      LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
      map.put("select", "*");
      map.put("from", "user");
      map.put("where", "username = '" + username + "'");
      PrintWriter out = res.getWriter();
      ResultSet rs = db.select(map);
      if (rs.next()) {
        user = rs.getInt("id");
        int tempType = rs.getInt("type");
        if (
          (Integer) session.getAttribute("user") != null &&
          user == (Integer) session.getAttribute("user")
        ) isUserHimself = 1;
        if (action != null && tempType != 0) {
          if (action.equals("delete") && type==0) {
            db.delete("user", "id = " + user);
            res.sendRedirect("/pastebin/app/u/users");
          }
        }
        map.put("select", "*");
        map.put("from", "pastes");
        String forPrivate = (isUserHimself == 1) ? " " : " and privacy = 0";
        map.put("where", "user = " + user + forPrivate);
        ResultSet pastes = db.select(map);
        ArrayList<PasteDto> pasteList = new ArrayList<PasteDto>();
        while (pastes.next()) {
          PasteDto pd = new PasteDto();
          pd.setPasteUri(pastes.getString("pasteUri"));
          pd.setAdded(pastes.getString("createdAt"));
          pd.setTitle(pastes.getString("title"));
          pd.setHits(pastes.getInt("hits"));
          pd.setExpiration(pastes.getInt("exposure"));
          pd.setUser(pastes.getInt("user"));
          pd.setContent(pastes.getString("content"));
          pd.setSyntax(pastes.getString("syntax"));
          pasteList.add(pd);
        }
        map.put("select", "count(*) count, sum(hits) hits");
        map.put("from", "pastes");
        map.put("where", "user = " + user);
        ResultSet aggregation = db.select(map);
        aggregation.next();

        RequestDispatcher rd = req.getRequestDispatcher("/views/pastes.jsp");
        req.setAttribute("pastes", pasteList);
        req.setAttribute("count", aggregation.getInt("count"));
        req.setAttribute("hits", aggregation.getInt("hits"));
        req.setAttribute("isUserHimself", isUserHimself);
        req.setAttribute("pastes", pasteList);
        req.setAttribute("username", username);
        rd.forward(req, res);
      } else {
        error += "Username does not exists.";
        session.setAttribute("error", error);
        res.sendRedirect("/pastebin/app/");
      }
    } catch (Exception e) {
      //todo error page server failure
    }
    //end of no username repeat

  }


  public static void showUsers(
    HttpServletRequest req,
    HttpServletResponse res
  )
    throws ServletException, IOException, SQLException, ClassNotFoundException {
    DB db = new DB();
    String error = "";
    PrintWriter out = res.getWriter();
    HttpSession session = req.getSession(true);
    try {
      LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
      map.put("select", "username,user.id idx,count(hits) pasteCount, COALESCE(sum(hits),0) hitsCount");
      map.put("from", "user");
      map.put("left join", "pastes");
      map.put("on", "user.id = pastes.user");
      map.put("where", "user.type != 0");
      map.put("group by", "user.id");
      ResultSet rs = db.select(map);

      ArrayList<UserDto> users = new ArrayList<UserDto>();
      boolean isNext= rs.next(); 
      if(isNext){
      while (isNext) {
          UserDto pd = new UserDto();
          pd.setUsername(rs.getString("username"));
          pd.setHits(rs.getInt("hitsCount"));
          pd.setCount(rs.getInt("pasteCount"));
          pd.setId(rs.getInt("idx"));
          users.add(pd);
          isNext = rs.next();
        }
      }
      RequestDispatcher rd = req.getRequestDispatcher("/views/users.jsp");
      req.setAttribute("users", users);
      rd.forward(req, res);
      // else {
      //   error += "Some error occured.";
      //   session.setAttribute("error", error);
      //   res.sendRedirect("/pastebin/app/");
      // }
    } catch (Exception e) {
      out.println(e);
      //todo error page server failure
    }
    //end of no username repeat

  }



  public static boolean isValidPassword(String password) {
    String regex =
      "^(?=.*[0-9])" +
      "(?=.*[a-z])(?=.*[A-Z])" +
      "(?=.*[@#$%^&+=])" +
      "(?=\\S+$).{8,20}$";
    Pattern p = Pattern.compile(regex);
    if (password == null) {
      return false;
    }
    Matcher m = p.matcher(password);
    return m.matches();
  }
}
