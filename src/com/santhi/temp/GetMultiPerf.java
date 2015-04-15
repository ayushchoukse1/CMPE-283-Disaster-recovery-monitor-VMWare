package com.santhi.temp;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.vmware.vim25.PerfCompositeMetric;
import com.vmware.vim25.PerfCounterInfo;
import com.vmware.vim25.PerfEntityMetric;
import com.vmware.vim25.PerfEntityMetricBase;
import com.vmware.vim25.PerfEntityMetricCSV;
import com.vmware.vim25.PerfMetricId;
import com.vmware.vim25.PerfMetricIntSeries;
import com.vmware.vim25.PerfMetricSeries;
import com.vmware.vim25.PerfMetricSeriesCSV;
import com.vmware.vim25.PerfQuerySpec;
import com.vmware.vim25.PerfSampleInfo;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.PerformanceManager;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

public class GetMultiPerf {
	 static VirtualMachine vm;
	static List<List<String>> list4 = new ArrayList<List<String>>();
	static List<String> list1 = new ArrayList<String>();
	static List<String> list = new ArrayList<String>();
	static List<String> list3 = new ArrayList<String>();
	public static void main(String[] args) throws Exception{
	//public List<List<String>> getStats() throws Exception {
		VCenterServiceInstance instance = new VCenterServiceInstance();
		ServiceInstance si = instance.getvCenterInstance();

		String vmname = "T11-VM03-Ubu-Ayu";

		vm = (VirtualMachine) new InventoryNavigator(si.getRootFolder())
				.searchManagedEntity("VirtualMachine", vmname);
		String actualvmname = vm.getSummary().getVm().get_value();
		if (vm == null) {
			System.out.println("Virtual Machine " + vmname
					+ " cannot be found.");
			si.getServerConnection().logout();
			//return null;
		}

		PerformanceManager perfMgr = si.getPerformanceManager();

		int perfInterval = 300; // 30 minutes for PastWeek

		// retrieve all the available perf metrics for vm
		PerfMetricId[] pmis = perfMgr.queryAvailablePerfMetric(vm, null, null,
				perfInterval);
		for (PerfMetricId id : pmis)
			id.setInstance("");
		Calendar curTime = si.currentTime();

		PerfQuerySpec qSpec = new PerfQuerySpec();
		qSpec.setEntity(vm.getRuntime().getHost());
		// metricIDs must be provided, or InvalidArgumentFault
		qSpec.setMetricId(pmis);
		qSpec.setFormat("normal"); // optional since it's default
		qSpec.setIntervalId(perfInterval);

		Calendar startTime = (Calendar) curTime.clone();
		startTime.roll(Calendar.DATE, -1);
		System.out.println("start:" + startTime.getTime());
		qSpec.setStartTime(startTime);

		Calendar endTime = (Calendar) curTime.clone();
		endTime.roll(Calendar.DATE, 0);
		System.out.println("end:" + endTime.getTime());
		qSpec.setEndTime(endTime);

		PerfCompositeMetric pv = perfMgr.queryPerfComposite(qSpec);
		if (pv != null) {
			// printPerfMetric(pv.getEntity());
			PerfEntityMetricBase[] pembs = pv.getChildEntity();
			for (int i = 0; pembs != null && i < pembs.length; i++) {
				printPerfMetric(pembs[i]);
			}
		}
		si.getServerConnection().logout();
		//return list4;
	}

	public static void printPerfMetric(PerfEntityMetricBase val)
			throws RemoteException, MalformedURLException {
		String entityDesc = val.getEntity().getType() + ":"
				+ val.getEntity().get_value();
		System.out.println("Entity:" + entityDesc);
		if (val instanceof PerfEntityMetric) {
			if (val.getEntity().get_value()
					.equals(vm.getSummary().getVm().get_value())) {
				printPerfMetric((PerfEntityMetric) val);
			}
		} else if (val instanceof PerfEntityMetricCSV) {
			printPerfMetricCSV((PerfEntityMetricCSV) val);
		} else {
			System.out.println("UnExpected sub-type of "
					+ "PerfEntityMetricBase.");
		}
	}

	public static void printPerfMetric(PerfEntityMetric pem) throws RemoteException,
			MalformedURLException {
		PerfMetricSeries[] vals = pem.getValue();
		PerfSampleInfo[] infos = pem.getSampleInfo();
		
		int count = 0;
		VCenterServiceInstance instance = new VCenterServiceInstance();
		ServiceInstance si = instance.getvCenterInstance();

		PerformanceManager perfMgr = si.getPerformanceManager();
		PerfCounterInfo[] pcis = perfMgr.getPerfCounter();
		for (int j = 0; vals != null && j < vals.length; ++j) {

			// if counter id is 2 cpu.usage.avg
			if (vals[j].getId().getCounterId() == 2) {

				if (vals[j] instanceof PerfMetricIntSeries) {
					PerfMetricIntSeries val = (PerfMetricIntSeries) vals[j];
					long[] longs = val.getValue();
					for (int k = 0; k < longs.length; k++) {
						// System.out.print(longs[k] + " ");
					}
					System.out.println("Total:" + longs.length);
					list = new ArrayList<String>();
					for (int i = 0; i < longs.length; i++) {
						String formattedDate = new SimpleDateFormat(
								"yyyy,MM,dd,HH,mm").format(infos[i]
								.getTimestamp().getTime());

						list.add("[ new Date(" + formattedDate + "), "
								+ longs[i] + "],");

					}

					for (int i = 0; i < list.size(); i++) {
						System.out.println(list.get(i));
					}
					for (int k = 0; k < longs.length / 10; k++) {
						// System.out.print(longs[k] + " ");
						System.out.println();
					}

				} else if (vals[j] instanceof PerfMetricSeriesCSV) {
					PerfMetricSeriesCSV val = (PerfMetricSeriesCSV) vals[j];
					// System.out.println("CSV value:" + val.getValue());
				}
			}

			// getting values with value id 24
			if (vals[j].getId().getCounterId() == 24) {

				if (vals[j] instanceof PerfMetricIntSeries) {
					PerfMetricIntSeries val = (PerfMetricIntSeries) vals[j];
					long[] longs = val.getValue();
					for (int k = 0; k < longs.length; k++) {
						// System.out.print(longs[k] + " ");
					}
					System.out.println("Total:" + longs.length);
					list1 = new ArrayList<String>();
					for (int i = 0; i < longs.length; i++) {
						String formattedDate = new SimpleDateFormat(
								"yyyy,MM,dd,HH,mm").format(infos[i]
								.getTimestamp().getTime());

						list1.add("[ new Date(" + formattedDate + "), "
								+ longs[i] + "],");

					}

					for (int i = 0; i < list1.size(); i++) {
						System.out.println(list1.get(i));
					}

				} else if (vals[j] instanceof PerfMetricSeriesCSV) {
					PerfMetricSeriesCSV val = (PerfMetricSeriesCSV) vals[j];
					// System.out.println("CSV value:" + val.getValue());
				}
			}
			
			list4.add(list);
			list4.add(list1);

		}
		
	}

	public static void printPerfMetricCSV(PerfEntityMetricCSV pems) {

		PerfMetricSeriesCSV[] csvs = pems.getValue();
		for (int i = 0; i < csvs.length; i++) {

		}
	}
}