import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class HomeService{

public static void savePaste(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, SQLException,ClassNotFoundException {
    DB db = new DB();
    PrintWriter out = res.getWriter();
    String content=req.getParameter("content");
    String exposure=req.getParameter("exposure");
    String privacy=req.getParameter("privacy");
    String password=(!req.getParameter("password").equals(""))?(md5.getMd5(req.getParameter("password"))):"";
    String title=req.getParameter("title");

    String randStr = getAlphaNumericString(9);
    String table= "pastes";
    String keys = "content,exposure,privacy,password,title,pasteUri";
    String values = "'"+content+"',"+"'"+exposure+"',"+"'"+privacy+"',"+"'"+password+"',"+"'"+title+"','"+randStr+"'";
    out.println(values);
    int result = db.insert(table,keys,values);
    if(result == 1){
      res.sendRedirect("/pastebin/app/"+randStr);	
    }
}



public static void showPaste(HttpServletRequest req, HttpServletResponse res,String subPath) throws ServletException, IOException, SQLException,ClassNotFoundException {
    DB db = new DB();
    PrintWriter out = res.getWriter();
    HashMap<String,String> map = new HashMap<String,String>();
    map.put("select","*");
    map.put("from","pastes");
    map.put("where","pasteUri = '"+subPath+"'");
    ResultSet rs;
    String rdUrl = "/index.jsp";
    String content="";
     try{
      rs = db.select(map);
      rs.next();
      content = rs.getString("content");
      int time = tsToSec8601(rs.getString("createdAt"));
      long unixTime = System.currentTimeMillis() / 1000L;
      int arr[] = {600,3600,86400,86400*7,86400*7*2,86400*30,86400*30*6,86400*30*12};
      int exposure = rs.getInt("exposure");
      boolean burn = false;
      if(exposure == 0);
      else if(exposure == 1)burn = true;
      else if(exposure > 1 && arr[(exposure-2)] <= (unixTime-time)) burn = true;    
      if(burn){
        db.delete("pastes","pasteUri = '"+subPath+"'");
      }   
     }
     catch(Exception e){
       res.sendRedirect("/pastebin/app/");
     }
    RequestDispatcher rd = req.getRequestDispatcher(rdUrl);
    req.setAttribute("show","show");
    req.setAttribute("content",content);
	  rd.forward(req,res); 
}





  static String getAlphaNumericString(int n)
    {
  
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
  
        for (int i = 0; i < n; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
  
        return sb.toString();
    }
  



  public static Integer tsToSec8601(String timestamp){
  if(timestamp == null) return 0;
  try {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date dt = sdf.parse(timestamp);
    long epoch = dt.getTime();
    return (int)(epoch/1000);
  } catch(Exception e) {
     return null;
  }
  }
}