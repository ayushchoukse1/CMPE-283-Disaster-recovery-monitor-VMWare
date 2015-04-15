package com.santhi.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class getssh extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public getssh() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ip=request.getParameter("GetSSH");
		System.out.println(ip);
		String Command = "C:/Program Files (x86)/PuTTY/putty.exe -ssh "+ip+" -P 22 -l ayush -pw 12!@qwQW";
		Process currentProcess;
		currentProcess = Runtime.getRuntime().exec(Command);
		RequestDispatcher rd = request
				.getRequestDispatcher("/Homepage.jsp");
		rd.forward(request, response);
	}

}
