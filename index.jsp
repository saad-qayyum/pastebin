<jsp:include page="header.jsp" />

<% String show=(String)request.getAttribute("show"); String content="" ;String syntax="basic" ; show=(show==null)?"":show;
  if(!show.equals("")){ content=(String)request.getAttribute("content");syntax=(String)request.getAttribute("syntax"); } %>
  <script>
    function validations() {
      console.log(editAreaLoader);
      if (editAreaLoader.getValue("textarea_1") == "") return false;
      document.getElementById("syntax").value = editAreas.textarea_1.settings.syntax;
      return true;
    }
  </script>
  <!--body-->
  <div class="container-fluid m-0 p-0 overflow-hidden">
    <div class="row" style="min-height:100vh;">

      <div class="col-12 col-lg-12 p-0">

        <form method="post" onsubmit="return validations()" name="pasteForm"
          class="d-flex justify-content-center flex-column p-4">
          <% if(!show.equals("show")){ %>
            <h6>New Paste</h6>
            <% } %>
              <script language="javascript" type="text/javascript"
                src="http://localhost:8080/pastebin/edit_area/edit_area_full.js"></script>
              <script language="javascript" type="text/javascript">

                editAreaLoader.init({
                  id: "textarea_1"		// textarea id
                  , syntax: "<%=syntax%>"		// syntax to be uses for highgliting
                  , start_highlight: true		// to display with highlight mode on start-up
                  , allow_toggle: false,
              <%=(show.equals("show") ? "is_editable:false," : "") %>
                toolbar: " undo, redo, |, select_font,|,syntax_selection,|"
            });
              </script>

              <textarea id="textarea_1" name="content" rows="20"><%=content%></textarea>
              <input type="hidden" name="syntax" value="basic" id="syntax" />
              <br>
              <% if(!show.equals("show")){ %>
                <h6 class="border-bottom border-light">Optional Paste Settings</h6>
                <div class="row g-3 align-items-center" style="font-size:smaller">
                  <div class="col-3">
                    <label for="inputPassword6" class="col-form-label small">Paste Expiration:</label>
                  </div>
                  <div class="col-3">
                    <select class="form-select form-select-sm" name="exposure" style="font-size:smaller"
                      aria-label=".form-select-sm example">
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
                    <select class="form-select form-select-sm" name="privacy" style="font-size:smaller"
                      aria-label=".form-select-sm example">
                      <option selected value="0">Public</option>
                      <%
                      out.println((Integer)session.getAttribute("user"));
                      if(session!=null && (Integer)session.getAttribute("user")!=null){
                      %>
                      <option value="1">Private</option>
                    <%}%>
                    </select>
                  </div>
                </div>

                <!-- <div class="row g-3 align-items-center" style="font-size:smaller">
                  <div class="col-3">
                    <label for="inputPassword6" class="col-form-label small">Password: </label>
                  </div>
                  <div class="col-3">
                    <input class="form-control form-control-sm" name="password" style="font-size:smaller" />
                  </div>
                </div> -->


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
                <% } %>

        </form>
      </div>


      

    </div>

  </div>



  <jsp:include page="footer.jsp" />