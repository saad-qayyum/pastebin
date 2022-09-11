import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.*;

public class UserService{

public static void saveUser(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, SQLException,ClassNotFoundException {
    DB db = new DB();
    String error = "";
    PrintWriter out = res.getWriter();
    String name=req.getParameter("name");
    error+=(name == null || name.equals(""))?"Name should not be empty.":"";
    String username=req.getParameter("username");
    error+=(username == null || username.equals(""))?",Username should not be empty.":"";

    String password=md5.getMd5(req.getParameter("password"));
    error+=(!isValidPassword(req.getParameter("password")))?",Password is not strong enough.":"";


    //no username should repeat
    HttpSession session = req.getSession(true);

try{
      HashMap<String,String> map = new HashMap<String,String>();
      map.put("select","*");
      map.put("from","user");
      map.put("where","username = '"+username+"'");
      ResultSet rs = db.select(map);
      if(rs.next()){
      error+=",Username is already taken.";
      session.setAttribute("error", error);
      res.sendRedirect("/pastebin/app/u/signup");	
      }
     }
     catch(Exception e){
      //todo error page server failure
     }
     //end of no username repeat 


if(error.equals("")){
    String table= "user";
    String keys = "name,username,type,password";
    String values = "'"+name+"',"+"'"+username+"',"+"1,"+"'"+password+"'";
    int result = db.insert(table,keys,values);
    if(result == 1){
      RequestDispatcher rd = req.getRequestDispatcher("/app/u/login");	
	    rd.forward(req,res);  
    }
    }
}



public static void loginUser(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, SQLException,ClassNotFoundException {
    DB db = new DB();
    String error = "";
    PrintWriter out = res.getWriter();
    String username=req.getParameter("username");
    String password=md5.getMd5(req.getParameter("password"));


    //no username should repeat
    HttpSession session = req.getSession(true);

try{
      HashMap<String,String> map = new HashMap<String,String>();
      map.put("select","*");
      map.put("from","user");
      map.put("where","username = '"+username+"' and password ='"+password+"'");
      ResultSet rs = db.select(map);
      if(rs.next()){
        session.setAttribute("user",rs.getInt("id"));
        session.setAttribute("role",rs.getInt("type"));
        res.sendRedirect("/pastebin/app/u/profile");	
      }
      else{
        error+="Username or password is incorrect.";
        res.sendRedirect("/pastebin/app/u/login");	

      }

     }
     catch(Exception e){
      //todo error page server failure
     }
     //end of no username repeat 

}











 public static boolean isValidPassword(String password)
    {
        String regex = "^(?=.*[0-9])"+"(?=.*[a-z])(?=.*[A-Z])"+"(?=.*[@#$%^&+=])"+"(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }
}