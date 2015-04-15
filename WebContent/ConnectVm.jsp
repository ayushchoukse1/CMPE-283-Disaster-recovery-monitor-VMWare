<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@page import="com.santhi.temp.GetVMList"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-theme.css" rel="stylesheet">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="custom_css/mycss.css" rel="stylesheet">
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>

<%
		String username = session.getAttribute("username").toString();
		List<String> list = new ArrayList<String>();
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/cmpe283", "root", "1234");
		PreparedStatement ps = con
				.prepareStatement("select * from mapping WHERE username=?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			list.add(rs.getString("vmname"));
		}
		GetVMList obj = new GetVMList();
		List list3 = obj.getVmList(list);
		HashMap<String, ArrayList<String>> map = obj.getMap();
		HashMap<String, String> ipmap = obj.getIpmap(); 
		session.setAttribute("vmlist", list3);
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
    
    	<div class="container-fluid" style="padding-left: 120px; padding-top: 25px;">
		<div class="row" style="paddin-top:50px;">
  		<div class="col-md-4" style="padding-top: 85px;">
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
	<form action="getssh" method="get" style="padding-top: 70px; padding-left: 130px;" >

		<%
			for (int i = 0; i < list3.size(); i++) {
		%><br>
		<%
			String str=obj.getVMIp(list3.get(i).toString());
			out.println(list3.get(i));
		%> <%=str %> 
		 </br><input type="submit" style="padding:bottom: 120px;" name="GetSSH" value="">
			
			
		<%
			
			}
		%>

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
 
</body>
</html>