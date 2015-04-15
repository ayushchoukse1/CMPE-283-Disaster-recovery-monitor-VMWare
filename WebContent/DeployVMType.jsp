<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%= session.getAttribute("username")%>
		<form action="deployubuntuvm" method="get">

		<div class="row">
			<h1>Deploy Ubuntu VM</h1>
	<label for="name">Enter VM Name:</label><br /> <input id="name"
				class="input" name="name" type="text" value="" size="30" /><br />
				
		<button type="submit">Create VM</button>
	</form>
	<form action="deployWinVM" method="get">

		<div class="row">
			<h1>Deploy Windows VM</h1>
	<label for="name">Enter VM Name:</label><br /> <input id="name"
				class="input" name="name" type="text" value="" size="30" /><br />
				
		<button type="submit">Create VM</button>
	</form>

</body>
</html>