package com.santhi.temp;

import java.io.IOException;

public class SSH {
	public static void main(String[] args) throws IOException,
			InterruptedException {
		String Command = "C:/Program Files (x86)/PuTTY/putty.exe -ssh 130.65.133.209 -P 22 -l ubuntu -pw 1";
		Process currentProcess;
		currentProcess = Runtime.getRuntime().exec(Command);

	}
}
