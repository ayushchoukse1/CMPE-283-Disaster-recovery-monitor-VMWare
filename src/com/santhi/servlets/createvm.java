package com.santhi.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santhi.temp.VCenterServiceInstance;
import com.santhi.temp.VmCdOp;
import com.vmware.vim25.Description;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecFileOperation;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualLsiLogicController;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineFileInfo;
import com.vmware.vim25.VirtualPCNet32;
import com.vmware.vim25.VirtualSCSISharing;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

public class createvm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public createvm() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("memory"));
		System.out.println(request.getParameter("cpu"));
		System.out.println(request.getParameter("storage"));
		String dcName = "T11-DC";
		String vmName = request.getParameter("name");
		long memorySizeMB =Long.parseLong(request.getParameter("memory"));
		int cupCount = Integer.parseInt(request.getParameter("cpu"));
		String guestOsId = "ubuntuGuest";
		long diskSizeKB = Integer.parseInt(request.getParameter("storage"));
		// mode: persistent|independent_persistent,
		// independent_nonpersistent
		String diskMode = "persistent";
		String datastoreName = "datastore1";
		String netName = "VM Network";
		String nicName = "Network Adapter 1";
		
		VCenterServiceInstance instance = new VCenterServiceInstance();
		ServiceInstance si = instance.getvCenterInstance();

		Folder rootFolder = si.getRootFolder();

		ManagedEntity[] dc = new InventoryNavigator(rootFolder)
				.searchManagedEntities(
						new String[][] { { "Datacenter", "name" }, }, true);
		Datacenter dc1 = (Datacenter) dc[0];
		Folder vmFolder = dc1.getVmFolder();
		ResourcePool rp = (ResourcePool) new InventoryNavigator(dc1)
				.searchManagedEntities("ResourcePool")[0];
		ManagedEntity[] hosts = new InventoryNavigator(rootFolder)
				.searchManagedEntities(
						new String[][] { { "HostSystem", "name" }, }, true);
		HostSystem host = (HostSystem) hosts[0];
		// create vm config spec
		VirtualMachineConfigSpec vmSpec = new VirtualMachineConfigSpec();
		vmSpec.setName(vmName);
		vmSpec.setAnnotation("VirtualMachine Annotation");
		vmSpec.setMemoryMB(memorySizeMB);
		vmSpec.setNumCPUs(cupCount);
		vmSpec.setGuestId(guestOsId);

		// create virtual devices
		int cKey = 1000;
		VirtualDeviceConfigSpec scsiSpec = createScsiSpec(cKey);
		VirtualDeviceConfigSpec diskSpec = createDiskSpec(datastoreName, cKey,
				diskSizeKB, diskMode);
		VirtualDeviceConfigSpec nicSpec;
		try {
			nicSpec = createNicSpec(netName, nicName);
			vmSpec.setDeviceChange(new VirtualDeviceConfigSpec[] { scsiSpec,
					diskSpec, nicSpec });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// create vm file info for the vmx file
		VirtualMachineFileInfo vmfi = new VirtualMachineFileInfo();
		vmfi.setVmPathName("[" + datastoreName + "]");
		vmSpec.setFiles(vmfi);

		// call the createVM_Task method on the vm folder
		Task task = vmFolder.createVM_Task(vmSpec, rp, host);
		String result = task.waitForMe();
		if (result == Task.SUCCESS) {
			System.out.println("VM Created Sucessfully");
			String username = request.getSession().getAttribute("username").toString();
				try {
					Class.forName("com.mysql.jdbc.Driver");
				
				Connection con = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/cmpe283", "root", "1234");
				PreparedStatement ps = con
						.prepareStatement("Insert into mapping(username, vmname) values(?,?)");
				ps.setString(1, username);
				ps.setString(2,vmName);
				ps.executeUpdate();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
		} else {
			System.out.println("VM could not be created. ");
		}
		VmCdOp obj=new VmCdOp();
		try {
			obj.addDeleteCD(vmName,"add");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(
				rootFolder).searchManagedEntity("VirtualMachine",
				vmName);
		Task task1 =vm.powerOnVM_Task(null);
		try {
			task1.waitForTask();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = request
				.getRequestDispatcher("/Homepage.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	static VirtualDeviceConfigSpec createScsiSpec(int cKey) {
		VirtualDeviceConfigSpec scsiSpec = new VirtualDeviceConfigSpec();
		scsiSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
		VirtualLsiLogicController scsiCtrl = new VirtualLsiLogicController();
		scsiCtrl.setKey(cKey);
		scsiCtrl.setBusNumber(0);
		scsiCtrl.setSharedBus(VirtualSCSISharing.noSharing);
		scsiSpec.setDevice(scsiCtrl);
		return scsiSpec;
	}

	static VirtualDeviceConfigSpec createDiskSpec(String dsName, int cKey,
			long diskSizeKB, String diskMode) {
		VirtualDeviceConfigSpec diskSpec = new VirtualDeviceConfigSpec();
		diskSpec.setOperation(VirtualDeviceConfigSpecOperation.add);
		diskSpec.setFileOperation(VirtualDeviceConfigSpecFileOperation.create);

		VirtualDisk vd = new VirtualDisk();
		vd.setCapacityInKB(diskSizeKB);
		diskSpec.setDevice(vd);
		vd.setKey(0);
		vd.setUnitNumber(0);
		vd.setControllerKey(cKey);

		VirtualDiskFlatVer2BackingInfo diskfileBacking = new VirtualDiskFlatVer2BackingInfo();
		String fileName = "[" + dsName + "]";
		diskfileBacking.setFileName(fileName);
		diskfileBacking.setDiskMode(diskMode);
		diskfileBacking.setThinProvisioned(true);
		vd.setBacking(diskfileBacking);
		return diskSpec;
	}

	static VirtualDeviceConfigSpec createNicSpec(String netName, String nicName)
			throws Exception {
		VirtualDeviceConfigSpec nicSpec = new VirtualDeviceConfigSpec();
		nicSpec.setOperation(VirtualDeviceConfigSpecOperation.add);

		VirtualEthernetCard nic = new VirtualPCNet32();
		VirtualEthernetCardNetworkBackingInfo nicBacking = new VirtualEthernetCardNetworkBackingInfo();
		nicBacking.setDeviceName(netName);

		Description info = new Description();
		info.setLabel(nicName);
		info.setSummary(netName);
		nic.setDeviceInfo(info);

		// type: "generated", "manual", "assigned" by VC
		nic.setAddressType("generated");
		nic.setBacking(nicBacking);
		nic.setKey(0);

		nicSpec.setDevice(nic);
		return nicSpec;
	}
}
