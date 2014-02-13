package com.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class Processor extends Thread {

	private String defaultPath = "E:\\j2ee\\html";
	private InputStream inputStream = null;
	private PrintStream out = null;

	public Processor(Socket socket) {
		try {
			inputStream = socket.getInputStream();
			out = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			String[] head = in.readLine().split(" ");
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sdf.format(new Date()));
			System.out.println(head[1]);
			while (in.ready()) {
				System.out.println(in.readLine());
			}*/
			String fileName = head[1];
			File file = new File(defaultPath + fileName);
			if (!file.exists()) {
				error(404, "没有找到页面！");
				return;
			}
			InputStream fis = new FileInputStream(file);
			byte[] content = new byte[(int) file.length()];
			fis.read(content);
			fis.close();
			out.write(content);
			out.flush();
			out.close();
			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 错误处理
	 * 
	 * @param errorCode
	 * @param errorMessage
	 */
	public void error(int errorCode, String errorMessage) {
		out.println(errorCode);
		out.println(errorMessage);
		out.flush();
		out.close();
	}

}
