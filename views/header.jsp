<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Pastebin</title>
  <link href="http://localhost:8080/pastebin/views/bootstrap/bootstrap.min.css" rel="stylesheet">
  <script src="http://localhost:8080/pastebin/views/bootstrap/jquery.slim.min.js" ></script>
  <script src="http://localhost:8080/pastebin/views/bootstrap/bootstrap.bundle.min.js" ></script>
  </head>
<body class="bg-dark text-light">
    
  <nav class="navbar bg-dark shadow shadow-lg sticky-top border-bottom border-secondary navbar-dark">
    <div class="container-fluid">
      <a class="navbar-brand" href="/pastebin/app/">Pastebin</a>

      <div class="d-flex">
        <a class="btn btn-sm btn-success me-1" href="/pastebin/app/"> +Paste</a>

        <%
        if((Integer)session.getAttribute("user") == null){ %>
        <a class="btn btn-outline-light me-1 btn-sm" href="/pastebin/app/u/signup">Signup</a>
        <a class="btn btn-secondary btn-sm" href="/pastebin/app/u/login">Signin</a>
        <%}
        else {%>
          <div class="dropdown ">
            <a class="btn btn-secondary btn-sm dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-expanded="false">
              Menu
            </a>
            <%
            String username=(String)session.getAttribute("username"); 
            int role = 1;
            role = (Integer)session.getAttribute("role"); 
            
            %>
            <div class="dropdown-menu dropdown-menu-left" style="
            left: -120%;
        ">
              <a class="dropdown-item" href="/pastebin/app/u/profile">Profile</a>
              <%if(role == 0){%><a class="dropdown-item" href="/pastebin/app/u/users">Users</a><%}%>
              <a class="dropdown-item" href="/pastebin/app/u/<%=username%>">My Pastes</a>
              <a class="dropdown-item" href="/pastebin/app/u/logout">Logout</a>
            </div>
          </div>
          
    <%}%>
      </div>
    </div>
  </nav>

  <%
  if(session != null){
  String error = (String)session.getAttribute("error");
  error = (error == null)?"":error;
  String[] errorList = error.split(",");
  if(error != null && !error.equals("")){
%>
<div class="p-3" style="font-size: smaller;" id="errorContainer">
<div class="alert alert-danger small text-danger m-0 alert-dismissible fade show" role="alert">
  <ul class="m-0">
  <%
  for(String err : errorList){
    if(err.equals(""))continue;
  %>
    <li><%=err%></li>
  <%}%>
</ul>
  <button type="button" class="btn-close" data-bs-dismiss="alert" onClick="document.getElementById('errorContainer').style.display='none';" aria-label="Close"></button>
</div>
</div>
<%}}
session.removeAttribute("error");
%>