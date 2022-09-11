
<jsp:include page="header.jsp" />  

<%
String show = (String)request.getAttribute("show");
String content = "";
show = (show == null)?"":show;
if(!show.equals("")){
content = (String)request.getAttribute("content");
}
%>
  <!--body-->
  <div class="container-fluid m-0 p-0 overflow-hidden">
    <div class="row" style="min-height:100vh;">

      <div class="col-9 col-lg-9 p-0">
        <form method="post" class="d-flex justify-content-center flex-column p-4">
          <%
          if(!show.equals("show")){
           %>
          <h6>New Paste</h6>
          <%
}
%>
          <script language="javascript" type="text/javascript" src="http://localhost:8080/pastebin/edit_area/edit_area_full.js"></script>
          <script language="javascript" type="text/javascript">
            editAreaLoader.init({
              id: "textarea_1"		// textarea id
              , syntax: "css"			// syntax to be uses for highgliting
              , start_highlight: true		// to display with highlight mode on start-up
              , allow_toggle: false,
              <%= (show.equals("show")?"is_editable:false,":"")  %>
              toolbar: " undo, redo, |, select_font,|,syntax_selection,|"
            });
          </script>
        
          <textarea id="textarea_1" name="content" cols="60" rows="20"><%=content%></textarea>
          
          <br>
          <%
           if(!show.equals("show")){
            %>
          <h6 class="border-bottom border-light">Optional Paste Settings</h6>
          <div class="row g-3 align-items-center" style="font-size:smaller">
            <div class="col-3">
              <label for="inputPassword6" class="col-form-label small">Paste Expiration:</label>
            </div>
            <div class="col-3">
              <select class="form-select form-select-sm" name="exposure" style="font-size:smaller" aria-label=".form-select-sm example">
                <option selected value="0">Never</option>
                <option value="1">Burn after read</option>
                <option value="2">10 Minutes</option>
                <option value="3">1 Hour</option>
                <option value="4">1 Day</option>
                <option value="5">1 Week</option>
                <option value="6">2 Weeks</option>
                <option value="7">1 Month</option>
                <option value="8">6 Months</option>
                <option value="9">1 Year</option>
              </select>
            </div>
          </div>

          <div class="row g-3 align-items-center" style="font-size:smaller">
            <div class="col-3">
              <label for="inputPassword6" class="col-form-label small">Paste Exposure:</label>
            </div>
            <div class="col-3">
              <select class="form-select form-select-sm" name="privacy" style="font-size:smaller" aria-label=".form-select-sm example">
                <option selected value="0">Public</option>
                <option value="1">Unlisted</option>
                <option value="2">Private</option>
              </select>
            </div>
          </div>

          <div class="row g-3 align-items-center" style="font-size:smaller">
            <div class="col-3">
              <label for="inputPassword6" class="col-form-label small">Password: </label>
            </div>
            <div class="col-3">           
              <input class="form-control form-control-sm" name="password" style="font-size:smaller" />
            </div>
          </div>


          <div class="row g-3 align-items-center" style="font-size:smaller">
            <div class="col-3">
              <label for="inputPassword6" class="col-form-label small">Paste Name/Title: </label>
            </div>
            <div class="col-3">           
              <input class="form-control form-control-sm" name="title" style="font-size:smaller" />
            </div>
          </div>


          <div class="row g-3 pt-4 " style="font-size:smaller">
          <p>
            <button type="submit" class="btn btn-sm btn-light shadow">Create New Paste</button>
          </p>
          </div>
<%
}
%>

        </form>
      </div>


      <div class="col-3  col-lg-3 bg-success">
      </div>

    </div>

  </div>



  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8"
    crossorigin="anonymous"></script>
</body>

</html>