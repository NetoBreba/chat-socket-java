package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connection extends Thread {
	
	public static final int PORT_DEFAULT = 9000;
	
	public static void main(String[] args) {
		int serverPort = args.length > 0 ? Integer.parseInt(args[0]) : PORT_DEFAULT;
		try {
			ExecutorService executor = Executors.newCachedThreadPool();
			ServerSocket serverSocket = new ServerSocket(serverPort);
			System.out.println("Aguardando conexão no endereço: " + InetAddress.getLocalHost().getHostAddress() + ":" + serverPort);
			while(true) {
				Socket socket = serverSocket.accept();
				Connection connection = new Connection(socket, executor);
				SingletonSendToAll.getInstance().addConnection(connection);
				System.out.println("Conexão estabelecida com: " + socket.getInetAddress() + ":" + socket.getPort());
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private DataInputStream in;
	private DataOutputStream out;
	private Socket socket;
	
	public Connection(Socket socket, ExecutorService executor) {
		try {
			this.socket = socket;
			this.in = new DataInputStream(socket.getInputStream());
			this.out = new DataOutputStream(socket.getOutputStream());
			this.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				String message = this.in.readUTF();
				SingletonSendToAll.getInstance().sendAll(message, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public DataInputStream getIn() {
		return in;
	}

	public void setIn(DataInputStream in) {
		this.in = in;
	}

	public DataOutputStream getOut() {
		return out;
	}

	public void setOut(DataOutputStream out) {
		this.out = out;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
