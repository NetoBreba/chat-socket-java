package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.Connection;

public class Client{
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Your Name: ");
		String name = scanner.nextLine();
		System.out.println("Ip Server: ");
		String ipServer = scanner.nextLine();
		System.out.println("");
		ExecutorService executor = Executors.newCachedThreadPool();
		try {
			Socket socket = new Socket(ipServer, Connection.PORT_DEFAULT);
			Client client = new Client(name, socket, executor);
			client.startChat();
			System.out.println("Chat Iniciado...\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ExecutorService executor;
	private String name;
	private DataOutputStream out;
	private DataInputStream in;
	
	public Client(String name, Socket socket, ExecutorService executor) {
		this.name = name;
		this.executor = executor;
		try {
			this.in = new DataInputStream(socket.getInputStream());
			this.out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startChat() {
		this.executor.execute(new SocketWrite(this));
		this.executor.execute(new SocketReader(this));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataOutputStream getOut() {
		return out;
	}

	public void setOut(DataOutputStream out) {
		this.out = out;
	}

	public DataInputStream getIn() {
		return in;
	}

	public void setIn(DataInputStream in) {
		this.in = in;
	}
}
