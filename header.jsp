<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Bootstrap demo</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
</head>
<body class="bg-dark text-light">
    
  <nav class="navbar bg-dark shadow shadow-lg sticky-top border-bottom border-secondary navbar-dark">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">Pastebin</a>
      <div class="d-flex">
        <%
        if((Integer)session.getAttribute("user") == null){ %>
        <button class="btn btn-outline-light me-1 btn-sm">Signup</button>
        <button class="btn btn-secondary btn-sm">Signin</button>
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
<%}}%>