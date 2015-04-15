package com.santhi.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santhi.temp.DeleteVm;

public class deletevm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public deletevm() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//get the vm name to be deleted.
		String vmname="samplevm";
		DeleteVm obj=new DeleteVm();
		try {
			obj.deleteVM(vmname);
			//delete from database also.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
