package services;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import dao.DB;

public class HomeService {

  public static void savePaste(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException, SQLException, ClassNotFoundException {
    DB db = new DB();
    PrintWriter out = res.getWriter();
    String content = req.getParameter("content");
    String exposure = req.getParameter("exposure");
    String privacy = req.getParameter("privacy");
    String syntax = req.getParameter("syntax");
    String action = req.getParameter("action");
    String pasteUri = req.getParameter("pasteUri");
    String title = req.getParameter("title").equals("")
      ? "Untitled"
      : req.getParameter("title");
      if (action.equals("edit") && pasteUri != null) {
        String tempTable = "pastes";
        String tempValues = " content = '" + content + "', title = '"+title+"', privacy = "+privacy+" , exposure = "+exposure+" , syntax = '"+syntax+"'";
        String where = " pasteUri = '" + pasteUri + "'";
        db.update(tempTable, tempValues, where);
        res.sendRedirect("/pastebin/app/"+pasteUri);
      }
else{
    // String password=(!req.getParameter("password").equals(""))?(md5.getMd5(req.getParameter("password"))):"";
    String password = "";

    HttpSession session = req.getSession(false);
    int user = 0;
    if (
      session != null && (Integer) session.getAttribute("user") != null
    ) user = (Integer) session.getAttribute("user");

    String randStr = getAlphaNumericString(9);
    String table = "pastes";
    String keys =
      "content,exposure,privacy,password,title,pasteUri,syntax,user";
    String values =
      "'" +
      content.replaceAll("'", "") +
      "'," +
      "'" +
      exposure +
      "'," +
      "'" +
      privacy +
      "'," +
      "'" +
      password +
      "'," +
      "'" +
      title +
      "','" +
      randStr +
      "','" +
      syntax +
      "'," +
      user;
    out.println(values);
    int result = db.insert(table, keys, values);
    if (result == 1) {
      res.sendRedirect("/pastebin/app/" + randStr);
    }
}
  }

  public static void showPaste(
    HttpServletRequest req,
    HttpServletResponse res,
    String subPath
  )
    throws ServletException, IOException, SQLException, ClassNotFoundException {
    DB db = new DB();
    PrintWriter out = res.getWriter();
    HttpSession session = req.getSession(true);
    String action = req.getParameter("action");

    int user = 0;int type=1;
    String username="";
    if (
      session != null && (Integer) session.getAttribute("user") != null
    ){
      user = (Integer) session.getAttribute("user");
      type = (Integer) session.getAttribute("role");
      username = (String) session.getAttribute("username");
    } 
    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
    map.put("select", "*");
    map.put("from", "pastes");
    map.put("where", "pasteUri = '" + subPath + "'");
    ResultSet rs;
    String rdUrl = "/views/index.jsp";
    String content = "";
    String syntax = "basic";
    int privacy = 0;
    int hits = 0;
    String title="Untitled";
    int pasteUser = 0;
    int exposure = 0;
    String show = "show";
    try {
      rs = db.select(map);
      rs.next();
      content = rs.getString("content");
      title = rs.getString("title");
      syntax = rs.getString("syntax");
      privacy = rs.getInt("privacy");
      pasteUser = rs.getInt("user");
      if (privacy == 1 && pasteUser != user) {
        session.setAttribute("error", "Paste is private.");
        res.sendRedirect("/pastebin/app/");
      }
      if (action != null) {
        if (action.equals("delete") && (pasteUser == user || type==0)) {
          db.delete("pastes", "pasteUri = '" + subPath + "'");
          if(type == 0){
            LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
            map1.put("select", "username");
            map1.put("from", "user");
            map1.put("where", "id = " +pasteUser);
            ResultSet u = db.select(map1);
            u.next();
            username = u.getString("username"); 
          }
          res.sendRedirect("/pastebin/app/u/"+username);
        }
        if (action.equals("edit") && pasteUser == user) {
          show = "edit";
        }
      } else {
        int time = tsToSec8601(rs.getString("createdAt"));
        long unixTime = System.currentTimeMillis() / 1000L;
        int arr[] = {
          600,
          3600,
          86400,
          86400 * 7,
          86400 * 7 * 2,
          86400 * 30,
          86400 * 30 * 6,
          86400 * 30 * 12,
        };
        exposure = rs.getInt("exposure");
        hits = rs.getInt("hits") + 1;

        String table = "pastes";
        String values = " hits = " + hits;
        String where = " pasteUri = '" + subPath + "'";
        db.update(table, values, where);

        boolean burn = false;
        if (exposure == 0); else if (exposure == 1) burn = true; else if (
          exposure > 1 && arr[(exposure - 2)] <= (unixTime - time)
        ) burn = true;
        if (burn) {
          db.delete("pastes", "pasteUri = '" + subPath + "'");
        }
      }
    } catch (Exception e) {
      res.sendRedirect("/pastebin/app/");
    }

    RequestDispatcher rd = req.getRequestDispatcher(rdUrl);
    req.setAttribute("show", show);
    req.setAttribute("content", content);
    req.setAttribute("privacy", privacy);
    req.setAttribute("exposure", exposure);
    req.setAttribute("title", title);
    req.setAttribute("syntax", syntax);
    req.setAttribute("pasteUri", subPath);
    rd.forward(req, res);
  }

  static String getAlphaNumericString(int n) {
    // chose a Character random from this String
    String AlphaNumericString =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

    // create StringBuffer size of AlphaNumericString
    StringBuilder sb = new StringBuilder(n);

    for (int i = 0; i < n; i++) {
      // generate a random number between
      // 0 to AlphaNumericString variable length
      int index = (int) (AlphaNumericString.length() * Math.random());

      // add Character one by one in end of sb
      sb.append(AlphaNumericString.charAt(index));
    }

    return sb.toString();
  }

  public static Integer tsToSec8601(String timestamp) {
    if (timestamp == null) return 0;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date dt = sdf.parse(timestamp);
      long epoch = dt.getTime();
      return (int) (epoch / 1000);
    } catch (Exception e) {
      return null;
    }
  }
}
