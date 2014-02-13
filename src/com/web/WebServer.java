package com.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	
	public void startServer(){
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(9999);
			while(true){
				Socket socket = serverSocket.accept();
				new Processor(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		new WebServer().startServer();
	}

}
