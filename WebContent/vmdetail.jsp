<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.vmware.vim25.mo.ServiceInstance" %>
     <%@ page import="com.vmware.vim25.mo.Folder" %>
      <%@ page import="com.vmware.vim25.mo.VirtualMachine" %>
         <%@ page import="com.vmware.vim25.mo.InventoryNavigator" %>
     <%@ page import="com.santhi.temp.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
<% String vmname=request.getParameter("Details");
	out.println(vmname);
VCenterServiceInstance instance = new VCenterServiceInstance();
ServiceInstance si = instance.getvCenterInstance();
Folder rootFolder = si.getRootFolder();
VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder)
		.searchManagedEntity("VirtualMachine", vmname);
	out.println(vm.getName());
%>
</body>
</html>