<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.css" rel="stylesheet">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="custom_css/mycss.css" rel="stylesheet">
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Dashboard</title>
</head>

<body style="padding-top: 50px;">
<%
/*  String username = request.getParameter("username");
System.out.println("Username: "+ username);  */

 String username=session.getAttribute("username").toString();
%>

<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="Homepage.jsp">Welcome <%=username%></a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
          <li><a href="Login_SignUp.jsp">Logout</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>


	<div class="container-fluid" style="padding-left: 210px; padding-top: 25px;">
		<div class="row">
  		<div class="col-md-4">
		<div class="btn-group-vertical btn-lg" role="group" aria-label="..." style="width: 250px;">
  		<div class="btn-group" role="group" style="padding-bottom: 35px;">
  		<button type="button" class="btn btn-default" id="create">CREATE VM</button>
 	 </div>
  <div class="btn-group" role="group" style="padding-bottom: 35px;">
    <button type="button" class="btn btn-default" id="deploy">DEPLOY VM</button>
  </div>
  <div class="btn-group" role="group" style="padding-bottom: 35px;">
    <button type="button" class="btn btn-default" id="connect">CONNECT VM</button>
  </div>
   <div class="btn-group" role="group" style="padding-bottom: 35px;">
    <button type="button" class="btn btn-default" id="listVM">LIST VMs</button>
  </div>
</div>
  </div>
  	  	</div> 	
	</div>

<script>
 document.getElementById("create").onclick = function(){
	 location.href= "SelectVMType.jsp";
 };
 </script>
 <script>
 document.getElementById("deploy").onclick = function(){
	 location.href= "Deploy.jsp";
 };
 </script>
 <script>
 document.getElementById("connect").onclick = function(){
	 location.href= "ConnectVm.jsp";
 };
 </script>
 <script>
 document.getElementById("listVM").onclick = function(){
	 location.href= "ListVM.jsp";
 };
 </script>
  
    <!--Footer--> 
<div class="navbar navbar-default navbar-fixed-bottom">
     <div class="container">

   </div> <!-- container-->
   </div> <!-- navbar navbar-default navbar-fixed-bottom" --> 



    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</body>
</html>