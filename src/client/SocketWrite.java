package client;

import java.io.IOException;
import java.util.Scanner;

public class SocketWrite implements Runnable{
	
	private Client client; 
	private Scanner scanner;
	
	public SocketWrite(Client client) {
		this.client = client;
		this.scanner = new Scanner(System.in);
	}

	@Override
	public void run() {
		while(true) {
			String message = scanner.nextLine();
			try {
				this.client.getOut().writeUTF(client.getName() + ": " + message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
