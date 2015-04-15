package com.santhi.temp;

import java.net.MalformedURLException;


import java.net.URL;
import java.rmi.RemoteException;

import com.santhi.temp.VCenterConfiguration;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.ServerConnection;
import com.vmware.vim25.mo.ServiceInstance;

public class VCenterServiceInstance {
	// Get configuration details of Admin and Student vCenter.
	VCenterConfiguration vCenterConfig = new VCenterConfiguration();

	ServiceInstance vCenterInstance = null;
	ServiceInstance AdminVCenterInstance = null;

	// Constructor
	public VCenterServiceInstance() throws RemoteException,
			MalformedURLException {
		setvCenterInstance();
		setAdminVCenterInstance();
	}

	// Getter method for Student's vCenter ServiceInstance
	public ServiceInstance getvCenterInstance() {
		return vCenterInstance;
	}

	// Setter method for Student's vCenter ServiceInstance
	public void setvCenterInstance() throws RemoteException,
			MalformedURLException {
		vCenterInstance = new ServiceInstance(new URL(
				vCenterConfig.getVcenterUrl()), vCenterConfig.getVcenterUser(),
				vCenterConfig.getPassword(), true);
	}

	// Setter method for Admin's vCenter ServiceInstance
	public void setAdminVCenterInstance() throws RemoteException,
			MalformedURLException {
		AdminVCenterInstance = new ServiceInstance(new URL(
				vCenterConfig.getVcenterUrlAdmin()),
				vCenterConfig.getVcenterUserAdmin(), vCenterConfig.getPassword(),
				true);
	}

	// Returns Team's vCenter RootFolder
	public Folder getVCenterRootFolder() throws RemoteException,
			MalformedURLException {

		return vCenterInstance.getRootFolder();
	}

	// Returns Admin's vCenter RootFolder
	public Folder getAdminVCenterRootFolder() throws RemoteException,
			MalformedURLException {

		return AdminVCenterInstance.getRootFolder();
	}
}
