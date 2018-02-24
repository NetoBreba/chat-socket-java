package client;

import java.io.IOException;

public class SocketReader implements Runnable{
	private Client client;

	public SocketReader(Client client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				String message = this.client.getIn().readUTF();
				System.out.println(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
