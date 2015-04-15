package com.santhi.temp;

import java.net.MalformedURLException;

import java.net.URL;
import java.rmi.RemoteException;

import com.vmware.vim25.ConfigTarget;
import com.vmware.vim25.DatastoreSummary;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.NetworkSummary;
import com.vmware.vim25.VirtualCdrom;
import com.vmware.vim25.VirtualCdromIsoBackingInfo;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecFileOperation;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualDisk;
import com.vmware.vim25.VirtualDiskFlatVer2BackingInfo;
import com.vmware.vim25.VirtualIDEController;
import com.vmware.vim25.VirtualMachineConfigInfo;
import com.vmware.vim25.VirtualMachineConfigOption;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineDatastoreInfo;
import com.vmware.vim25.VirtualMachineNetworkInfo;
import com.vmware.vim25.VirtualMachineRuntimeInfo;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.EnvironmentBrowser;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

public class VmCdOp {
		public void addDeleteCD(String vmName,String opn) throws Exception{
		
		String vmname = vmName;
		String op = opn;

		VCenterServiceInstance instance = new VCenterServiceInstance();
		ServiceInstance si = instance.getvCenterInstance();
		Folder rootFolder = si.getRootFolder();
		VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder)
				.searchManagedEntity("VirtualMachine", vmname);
		if (vm == null) {
			System.out.println("No VM " + vmname + " found");
			si.getServerConnection().logout();
			return;
		}
		
		VirtualMachineConfigSpec vmConfigSpec = new VirtualMachineConfigSpec();

		if ("add".equalsIgnoreCase(op)) {
			String dsName = "datastore1";
			String iso = "ubuntu-14.04.1-desktop-i386";
			VirtualDeviceConfigSpec cdSpec = createAddCdConfigSpec(vm, dsName,
					iso);
			vmConfigSpec
					.setDeviceChange(new VirtualDeviceConfigSpec[] { cdSpec });
		} else if ("remove".equalsIgnoreCase(op)) {
			String cdName = "CD/DVD drive 1";
			VirtualDeviceConfigSpec cdSpec = createRemoveCdConfigSpec(vm,
					cdName);
			vmConfigSpec
					.setDeviceChange(new VirtualDeviceConfigSpec[] { cdSpec });
		} else {
			System.out.println("Invlaid device type [disk|cd]");
			return;
		}

		Task task = vm.reconfigVM_Task(vmConfigSpec);
		task.waitForMe();
	}

	static VirtualDeviceConfigSpec createAddCdConfigSpec(VirtualMachine vm,
			String dsName, String isoName) throws Exception {
		VirtualDeviceConfigSpec cdSpec = new VirtualDeviceConfigSpec();

		cdSpec.setOperation(VirtualDeviceConfigSpecOperation.add);

		VirtualCdrom cdrom = new VirtualCdrom();
		VirtualCdromIsoBackingInfo cdDeviceBacking = new VirtualCdromIsoBackingInfo();
		DatastoreSummary ds = findDatastoreSummary(vm, dsName);
		cdDeviceBacking.setDatastore(ds.getDatastore());
		cdDeviceBacking
				.setFileName("[nf3team11] __DEPLOY__/ESXI_HOST_FILES/iso-linux/ubuntu-14.04.1-desktop-i386.iso");
		VirtualDevice vd = getIDEController(vm);
		cdrom.setBacking(cdDeviceBacking);
		cdrom.setControllerKey(vd.getKey());
		cdrom.setUnitNumber(vd.getUnitNumber());
		cdrom.setKey(-1);
		cdSpec.setDevice(cdrom);

		return cdSpec;
	}

	static VirtualDeviceConfigSpec createRemoveCdConfigSpec(VirtualMachine vm,
			String cdName) throws Exception {
		VirtualDeviceConfigSpec cdSpec = new VirtualDeviceConfigSpec();
		cdSpec.setOperation(VirtualDeviceConfigSpecOperation.remove);
		VirtualCdrom cdRemove = (VirtualCdrom) findVirtualDevice(
				vm.getConfig(), cdName);
		if (cdRemove != null) {
			cdSpec.setDevice(cdRemove);
			return cdSpec;
		} else {
			System.out.println("No device available " + cdName);
			return null;
		}
	}

	private static VirtualDevice findVirtualDevice(
			VirtualMachineConfigInfo vmConfig, String name) {
		VirtualDevice[] devices = vmConfig.getHardware().getDevice();
		for (int i = 0; i < devices.length; i++) {
			if (devices[i].getDeviceInfo().getLabel().equals(name)) {
				return devices[i];
			}
		}
		return null;
	}

	static DatastoreSummary findDatastoreSummary(VirtualMachine vm,
			String dsName) throws Exception {
		DatastoreSummary dsSum = null;
		VCenterServiceInstance instance = new VCenterServiceInstance();
		ServiceInstance si = instance.getvCenterInstance();
		Folder rootFolder = si.getRootFolder();
		ManagedEntity[] hosts = new InventoryNavigator(rootFolder)
		.searchManagedEntities(
				new String[][] { { "HostSystem", "name" }, }, true);
		HostSystem host=(HostSystem)hosts[2];
		VirtualMachineRuntimeInfo vmRuntimeInfo = vm.getRuntime();
		EnvironmentBrowser envBrowser = vm.getEnvironmentBrowser();
		ManagedObjectReference hmor = vmRuntimeInfo.getHost();

		if (hmor == null) {
			System.out.println("No Datastore found");
			return null;
		}
		HostSystem host1=	new HostSystem(vm.getServerConnection(),
				hmor);
		System.out.println();
		ConfigTarget configTarget = envBrowser.queryConfigTarget(null);
		VirtualMachineDatastoreInfo[] dis = configTarget.getDatastore();
		
		for (int i = 0; dis != null && i < dis.length; i++) {
			dsSum = dis[i].getDatastore();
			if (dsSum.isAccessible() && dsName.equals(dsSum.getName())) {
				break;
			}
		}
		return dsSum;
	}

	static VirtualDevice getIDEController(VirtualMachine vm) throws Exception {
		VirtualDevice ideController = null;
		VirtualDevice[] defaultDevices = getDefaultDevices(vm);
		for (int i = 0; i < defaultDevices.length; i++) {
			if (defaultDevices[i] instanceof VirtualIDEController) {
				ideController = defaultDevices[i];
				break;
			}
		}
		return ideController;
	}

	static VirtualDevice[] getDefaultDevices(VirtualMachine vm)
			throws Exception {
		VirtualMachineRuntimeInfo vmRuntimeInfo = vm.getRuntime();
		EnvironmentBrowser envBrowser = vm.getEnvironmentBrowser();
		ManagedObjectReference hmor = vmRuntimeInfo.getHost();
		VirtualMachineConfigOption cfgOpt = envBrowser.queryConfigOption(null,
				null);
		VirtualDevice[] defaultDevs = null;
		if (cfgOpt != null) {
			defaultDevs = cfgOpt.getDefaultDevice();
			if (defaultDevs == null) {
				throw new Exception("No Datastore found in ComputeResource");
			}
		} else {
			throw new Exception(
					"No VirtualHardwareInfo found in ComputeResource");
		}
		return defaultDevs;
	}
}
