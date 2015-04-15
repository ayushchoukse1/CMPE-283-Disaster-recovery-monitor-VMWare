<%@page import="com.santhi.temp.GetVMList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Listing All the VM's Created by You!!</h1>

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
		HashMap<String, ArrayList<String>> map=obj.getMap();
		session.setAttribute("vmlist", list3);
	%>
	<form action="vmdetail.jsp" method="get">
		<%
			for (int i = 0; i < list3.size(); i++) {
				out.println(list3.get(i));
						for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
							if(entry.getKey().contains(list.get(i))){
							 out.println(entry.getValue());    %><br><% 
							}
							}  	%><input type="submit"  name="Details" value="<%list3.get(i);%>"><br><%
			}
		
		%>
	</form>
</body>
</html>