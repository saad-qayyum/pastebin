<%@page import="java.util.*"%>
<%@page import="dto.PasteDto"%>


  <jsp:include page="header.jsp" />

 
    <!--body-->
    <div class="container-fluid m-0 p-0 overflow-hidden">
      <div class="row" style="min-height:100vh;">

        <div class="col-12 col-lg-12 col-md-8 col-sm-8 p-5">
<%
String username=(String)request.getAttribute("username"); 
int count=(int)request.getAttribute("count"); 
int hits=(int)request.getAttribute("hits"); 
int isUserHimself=(int)request.getAttribute("isUserHimself"); 
username = username.substring(0,1).toUpperCase() + username.substring(1).toLowerCase();
%>
<h3 class="border-bottom border-secondary shadow"><%=username%>'s Pastes </h3>
<span class="small text-muted">Pastes: <%=count%></span>  <span class="small text-muted"> | Hits: <%=hits%></span> 
          <table class="table table-dark small">
            <thead>
              <tr>
                <th scope="col">Name/Title</th>
                <th scope="col">Added</th>
                <th scope="col">Expires</th>
                <th scope="col">Hits</th>
                <th scope="col">Syntax</th>
                <th scope="col"></th>
              </tr>
            </thead>
            <tbody>
              <% 
              ArrayList pasteList=(ArrayList)request.getAttribute("pastes"); 
              PasteDto pd = null;
              String expires[] = {"Never","Burn after read","10 Minutes","1 Hour","1 Day","1 Week","2 Weeks","1 Month","6 Months","1 Year"};
                for(int i = 0;pasteList !=null && i<pasteList.size();i++){
                  pd = (PasteDto)pasteList.get(i);
                  String[] date = pd.getAdded().split(" ");
              %>
                  <tr>
                    <td><a href="/pastebin/app/<%=pd.getPasteUri()%>"><%=pd.getTitle()%></a></td>
                    <td><%=date[0]%></td>
                    <td><%=expires[pd.getExpiration()]%></td>
                    <td><%=pd.getHits()%></td>
                    <td><%=pd.getSyntax()%></td>
                    <td>  
                    <% if(isUserHimself == 1){%><a href="/pastebin/app/<%=pd.getPasteUri()%>?action=edit">Edit</a>  | <%}%> 
                    <% if(isUserHimself == 1 || ((Integer)session.getAttribute("role") !=null && (Integer)session.getAttribute("role") == 0)){%>  <a  href="/pastebin/app/<%=pd.getPasteUri()%>?action=delete">Delete</a> <%}%> 
                    </td>
                  </tr>
                 <%}%>
            </tbody>
          </table>

        </div>


      </div>

    </div>



    <jsp:include page="footer.jsp" />