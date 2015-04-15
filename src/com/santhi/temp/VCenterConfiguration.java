package com.santhi.temp;

public class VCenterConfiguration {
	// Ip address of the Administrator's VCenter console.
	static String VcenterUrlAdmin = "130.65.132.19";

	// Ip address of the Student's VCenter console.
	static String VcenterUrl = "130.65.132.111";

	// Password string that is common throughout the each vCenters, vHosts,
	// VM's.
	static String Password = "12!@qwQW";

	// User name for our team's vCenter.
	static String VcenterUser = "administrator";

	// User name for our team's vCenter.
	static String VcenterUserAdmin = "student@vsphere.local";

	public static String getVcenterUserAdmin() {
		return VcenterUserAdmin;
	}

	public static String getVcenterUrlAdmin() {
		return "https://" + VcenterUrlAdmin + "/sdk";
	}

	public static String getVcenterUrl() {
		return "https://" + VcenterUrl + "/sdk";
	}

	public static String getPassword() {
		return Password;
	}

	public static String getVcenterUser() {
		return VcenterUser;
	}

}
