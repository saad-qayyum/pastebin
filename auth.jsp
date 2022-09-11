
<jsp:include page="header.jsp" />  
<% 
        String p = (String)request.getAttribute("page");
        if(p != null);
        else p = "Signup";
%>

  <!--body-->
  <div class="container-fluid m-0 p-0 overflow-hidden">
    <div class="row" style="min-height:100vh;">

      <div class="col-12 col-lg-4 col-md-6 col-sm-8 p-0 m-auto">
        <form method="post" class="d-flex justify-content-center rounded shadow shadow-lg flex-column p-4 m-auto">
          <h3 class="text-center shadow p-2 border-bottom border-secondary"><%=p%></h3>

      <div class="fieldsWrapper p-2">
        <% if(p != null && !p.equals("Login")){ %>
          <div class=" align-items-center" style="font-size:smaller">
         
              <label for="inputPassword6" class="col-form-label small">Full name: </label>
          
              <input class="form-control form-control-sm" required name="name" style="font-size:smaller" />
          </div>
<%}%>

          <div class=" align-items-center" style="font-size:smaller">
 
              <label for="inputPassword6" class="col-form-label small">Username: </label>
         
              <input class="form-control form-control-sm" required name="username" style="font-size:smaller" />
          </div>

          <div class=" align-items-center" style="font-size:smaller">

              <label for="inputPassword6" class="col-form-label small">Password: </label>
         
              <input class="form-control form-control-sm" required type="password" name="password" style="font-size:smaller" />
          </div>


          <div class="row g-3 pt-4 text-end" style="font-size:smaller">
          <p>
            <button type="submit" class="btn btn-sm btn-light shadow">Continue</button>
          </p>
          </div>

        </div>
        </form>
      </div>


    </div>

  </div>



  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8"
    crossorigin="anonymous"></script>
</body>

</html>