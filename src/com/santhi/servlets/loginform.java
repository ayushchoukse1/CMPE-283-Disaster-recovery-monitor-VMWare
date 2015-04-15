package com.santhi.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.santhi.temp.GetVMList;

public class loginform extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public loginform() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		GetVMList obj = new GetVMList();
		//List<String> temp = obj.getVmList();
		List<String> list = new ArrayList<String>();
		//list.add(temp.get(0));
		//request.setAttribute("vm", list.get(0));
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		List<String> lst = new ArrayList<String>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/cmpe283", "root", "1234");
			PreparedStatement ps = con
					.prepareStatement("select * from user WHERE username=? && password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				lst.add(rs.getString("username"));
				HttpSession session = request.getSession();
			    
			    session.setAttribute("username", username);
				
				RequestDispatcher rd = request
						.getRequestDispatcher("/Homepage.jsp");
				rd.forward(request, response);
				lst.clear();
				out.close();
			} else {
				response.sendRedirect("Invalid.jsp");
			}
			rs.close();

		} catch (Exception e) {
			System.out.println(e);
		} 
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
