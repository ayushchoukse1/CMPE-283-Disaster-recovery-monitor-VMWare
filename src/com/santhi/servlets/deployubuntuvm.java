package com.santhi.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santhi.temp.VMClone;

public class deployubuntuvm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public deployubuntuvm() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String cloneVmName = request.getParameter("ubuntuname");
		System.out.println(cloneVmName);
		String username = request.getSession().getAttribute("username").toString();
		System.out.println(username);
		VMClone obj = new VMClone();
		try {
			boolean result = obj.cloneVM(cloneVmName);
			if (result) {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/cmpe283", "root", "1234");
				PreparedStatement ps = con
						.prepareStatement("Insert into mapping(username, vmname) values(?,?)");
				ps.setString(1, username);
				ps.setString(2, cloneVmName);
				ps.executeUpdate();
				
				RequestDispatcher rd = request
						.getRequestDispatcher("/Homepage.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
