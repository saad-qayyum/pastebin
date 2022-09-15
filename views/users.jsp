<%@page import="java.util.*"%>
<%@page import="dto.UserDto"%>


  <jsp:include page="header.jsp" />

 
    <!--body-->
    <div class="container-fluid m-0 p-0 overflow-hidden">
      <div class="row" style="min-height:100vh;">

        <div class="col-12 col-lg-12 col-md-8 col-sm-8 p-5">
<h3 class="border-bottom border-secondary shadow">Users </h3>
          <table class="table table-dark small">
            <thead>
              <tr>
                <th scope="col">Username</th>
                <th scope="col">Pastes</th>
                <th scope="col">Hits</th>
                <th scope="col"></th>
              </tr>
            </thead>
            <tbody>
              <% 
              ArrayList userList=(ArrayList)request.getAttribute("users"); 
              UserDto user = null;
                for(int i = 0;userList !=null && i<userList.size();i++){
                  user = (UserDto)userList.get(i);
              %>
                  <tr>
                    <td><a href="/pastebin/app/u/<%=user.getUsername()%>"><%=user.getUsername()%></a></td>
                    <td><%=user.getCount()%></td>  
                    <td><%=user.getHits()%></td>
                    <td><a  href="/pastebin/app/u/<%=user.getUsername()%>?action=delete">Delete</a> </td> 
                  </tr>
                  <%}%>
            </tbody>
          </table>

        </div>


      </div>

    </div>



    <jsp:include page="footer.jsp" />