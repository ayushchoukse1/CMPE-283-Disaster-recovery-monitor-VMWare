package com.santhi.temp;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

public class DeleteVm {

	public boolean deleteVM(String vmname) throws RemoteException,
			MalformedURLException, InterruptedException {
		String vmName = vmname;

		String datacenterName = "T11_DC";

		VCenterServiceInstance instance = new VCenterServiceInstance();
		ServiceInstance si = instance.getvCenterInstance();

		Folder rootFolder = si.getRootFolder();

		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder)
				.searchManagedEntity("VirtualMachine", vmName);

		Datacenter dc = (Datacenter) si.getSearchIndex().findByInventoryPath(
				datacenterName);

		if (vm == null || dc == null) {
			System.out
					.println("VirtualMachine or Datacenter path is NOT correct. Pls double check. ");
			 return false;
		}
		else {
			Task task1=vm.destroy_Task();
					task1.waitForTask();
					System.out.println("deleted");
					return true;
		}

	}
}