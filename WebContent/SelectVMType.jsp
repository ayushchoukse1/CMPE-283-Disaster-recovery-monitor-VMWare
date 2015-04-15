<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

	<link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.css" rel="stylesheet">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="custom_css/mycss.css" rel="stylesheet">
    
<%String username=session.getAttribute("username").toString();%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Choose an Operating System</title>
</head>
<body>


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
    
    
    <div class="container-fluid" style="padding-left: 110px; padding-top: 25px;">
		<div class="row" style="padding-top: 50px;">
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
  
  
  <div class="col-md-4">
  <img src="Winlogo.jpg" class="img-responsive" alt="Windows Logo" width="30" height="23">
	<form action="CreateWindowsVM" method="get">
		<div class="row">
			<h3>Create Windows VM</h3>
			<label for="name">VM Name: </label><br /> <input id="name"
				class="input" name="name" type="text" value="" size="30" /><br />
		</div>
		<div class="row">
			<label for="memory">Memory(Max 500Mb):</label><br /> <input
				id="email" class="input" name="memory" type="text" value="" size="30" /><br />
		</div>
		<div class="row">
			<label for="cpu">CPU Count (Max 1):</label><br /> <input id="cpu"
				class="input" name="cpu" type="text" value="" size="30" /><br />
		</div>
		<div class="row">
			<label for="storage">Storage in kb (Max 1,000,000 Kb): </label><br /> <input
				id="cpu" class="input" name="storage" type="text" value="" size="30" /><br />
		</div></br>
		<button type="submit">Create VM</button>
	</form>
	</div>
	
	<div class="col-md-4">
	<img src="UbuntuLogo.jpg" class="img-responsive" alt="Ubuntu Logo" width="30" height="23">
	<form action="createvm" method="get">
		<div class="row">
			<h3>Create Ubuntu VM</h3>
			<label for="name">VM Name:</label><br /> <input id="name"
				class="input" name="name" type="text" value="" size="30" /><br />
		</div>
		<div class="row">
			<label for="memory">Memory(Max 500Mb):</label><br /> 
			<input id="memory" class="input" name="memory" type="text" value="" size="30" /><br />
		</div>
		<div class="row">
			<label for="cpu">CPU Count (Max 1):</label><br />
			 <input id="cpu" class="input" name="cpu" type="text" value="" size="30" /><br />
		</div>
		<div class="row">
			<label for="storage">Storage in kb (Max 1,000,000 Kb):</label><br /> 
			<input id="storage" class="input" name="storage" type="text" value="" size="30" /><br />
		</div></br>
		<button type="submit">Create VM</button>
	</form>
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