<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Bootstrap demo</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
  </head>
<body class="bg-dark text-light">
    
  <nav class="navbar bg-dark shadow shadow-lg sticky-top border-bottom border-secondary navbar-dark">
    <div class="container-fluid">
      <a class="navbar-brand" href="/pastebin/app/">Pastebin</a>
      <div class="d-flex">
        <%
        if((Integer)session.getAttribute("user") == null){ %>
        <a class="btn btn-outline-light me-1 btn-sm" href="/pastebin/app/u/signup">Signup</a>
        <a class="btn btn-secondary btn-sm" href="/pastebin/app/u/login">Signin</a>
        <%}
        else {%>
          <div class="dropdown ">
            <a class="btn btn-secondary dropleft dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-expanded="false">
              Menu
            </a>
            <%
            String username=(String)session.getAttribute("username"); 
            %>
            <div class="dropdown-menu  dropdown-menu-left" style="
            left: -100%;
        ">
              <a class="dropdown-item" href="/pastebin/app/u/<%=username%>">Profile</a>
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
<div class="p-3" style="font-size: smaller;">
<div class="alert alert-danger small text-danger m-0 alert-dismissible fade show" role="alert">
  <ul class="m-0">
  <%
  for(String err : errorList){
    if(err.equals(""))continue;
  %>
    <li><%=err%></li>
  <%}%>
</ul>
  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>
</div>
<%}}
session.removeAttribute("error");
%>