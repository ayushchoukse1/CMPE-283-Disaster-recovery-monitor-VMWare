package com.santhi.temp;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vmware.vim25.VirtualMachineQuickStats;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

public class GetVMList {
	HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
	HashMap<String, String> ipmap=new HashMap<String, String>();
	
	
	public List getVmList(List<String> vmnamelist) throws RemoteException,
			MalformedURLException {
		VCenterServiceInstance instance = new VCenterServiceInstance();
		ServiceInstance si = instance.getvCenterInstance();

		Folder rootFolder = si.getRootFolder();

		ManagedEntity[] vms = new InventoryNavigator(rootFolder)
				.searchManagedEntities(new String[][] { { "VirtualMachine",
						"name" }, }, true);
		List list = new ArrayList();
		List list2 = vmnamelist;

		for (int i = 0; i < vms.length; i++) {
			VirtualMachine vm = (VirtualMachine) vms[i];
			VirtualMachineRuntimeInfo vmri = vm.getRuntime();
			if (list2.contains(vm.getName())) {
//				list.add(vm.getName().toString() + "  -Status-  "
//						+ vmri.getPowerState().toString());
				
				list.add(vm.getName().toString() );
					VirtualMachineQuickStats qs = vm.getSummary()
							.getQuickStats();
					ArrayList<String> arraylist = new ArrayList<String>();
					arraylist.add("OverallCpuUsage: "+ qs.getOverallCpuUsage().toString()+ "MHz");
					arraylist.add("GuestMemoryUsage: "+ qs.getGuestMemoryUsage().toString()+ "MB");
					arraylist.add(vmri.getMaxMemoryUsage().toString());
					map.put(vm.getName().toString(), arraylist);
					

					

			}
		}
		
		return list;
	}

	public HashMap<String, String> getIpmap() {
		return ipmap;
	}

	
	public HashMap<String, ArrayList<String>> getMap() {
		return map;
	}

	public String getVMIp(String vmname) throws RemoteException,
			MalformedURLException {
		String status;
		String ip=null;
		VCenterServiceInstance instance = new VCenterServiceInstance();
		ServiceInstance si = instance.getvCenterInstance();

		Folder rootFolder = si.getRootFolder();

		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder)
				.searchManagedEntity("VirtualMachine", vmname);
		if(vm instanceof VirtualMachine)
		 ip = vm.getGuest().getIpAddress().toString();
		
		return ip;
	}
	 public static void main(String[] args) throws RemoteException,
	 MalformedURLException{
	 GetVMList obj =new GetVMList();
	System.out.println(obj.getVMIp("deletevm"));
	 }
}
