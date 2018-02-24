package server;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SingletonSendToAll {
	private static volatile SingletonSendToAll uniqueInstance;
	private List<Connection> connections;
	private DateFormat sdf;
	
	private SingletonSendToAll() {
		this.connections = new ArrayList<Connection>();
		this.sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	}
	
	public static SingletonSendToAll getInstance() {
		if(uniqueInstance == null) {
			synchronized (SingletonSendToAll.class) {
				if(uniqueInstance == null) uniqueInstance = new SingletonSendToAll();
			}
		}
		return uniqueInstance;
	}
	
	public Connection addConnection(Connection connection) {
		this.connections.add(connection);
		return connection;
		
	}
	
	public void sendAll(String message, Connection connection) {
		for(Connection c:this.connections) {
			if(c.hashCode() != connection.hashCode()) {
				try {
					Date date = new Date();
					message = sdf.format(date) + " - " + message;
					c.getOut().writeUTF(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
