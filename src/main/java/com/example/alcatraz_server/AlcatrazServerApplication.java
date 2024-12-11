package com.example.alcatraz_server;

import com.example.alcatraz_server.spread_server.Server;
import com.example.alcatraz_server.spread_server.ServerListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import spread.SpreadConnection;
import spread.SpreadException;

import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class AlcatrazServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlcatrazServerApplication.class, args);
		/*ServerListener serverListener = new ServerListener();
		Server server = new Server(username, serverListener);
		while (true) {
			//server.polling();
			Thread.sleep(1000);
		}*/
	}

}
