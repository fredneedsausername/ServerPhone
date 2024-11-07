package fredver.clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private static InetSocketAddress askSocketAddress() {
		
		boolean successfulServerConnection = false;
		int serverPortInt = -1;
		InetAddress ia = null;
		SUCCESSFULSERVERCONNECTIONLOOP:
		while(!successfulServerConnection) {
			try {
				BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
				
				boolean enteredValidIpAddress = false;
				ia = InetAddress.getLocalHost();
				ENTEREDVALIDIPADDRESSLOOP:
				while(!enteredValidIpAddress) {
					try {
						System.out.println("Enter server ip address");
						String ipAddress = inputReader.readLine();
						
						ia = InetAddress.getByName(ipAddress);
						enteredValidIpAddress = true;
					} catch (UnknownHostException e) {
						System.out.println("Invalid or not found ip address, try again");
						continue ENTEREDVALIDIPADDRESSLOOP;
					}
				}
				
				
				boolean enteredValidPortNumber = false;
				ENTEREDVALIDPORTNUMBERLOOP:
				while(!enteredValidPortNumber) {
					System.out.println("Enter server connection port");
					String serverPort = inputReader.readLine();
					try {
						serverPortInt = Integer.parseInt(serverPort);
						enteredValidPortNumber = true;
					} catch (NumberFormatException e) {
						System.out.println("Entered port number is not an integer, try again");
						continue ENTEREDVALIDPORTNUMBERLOOP;
					}
					
					try {
						System.out.println("Testing connection...");
						Socket s = new Socket(ia, serverPortInt);
						successfulServerConnection = true;
						s.close();
					} catch (IllegalArgumentException e) {
						System.out.println("Invalid port number");
						continue ENTEREDVALIDPORTNUMBERLOOP;
					}
				}
				
			} catch (UnknownHostException e) {
				System.out.println("localhost not found");
				continue SUCCESSFULSERVERCONNECTIONLOOP;
			} catch (ConnectException e) {
				System.out.println("Cannot connect, server either refused or inexistent");
				continue SUCCESSFULSERVERCONNECTIONLOOP;
			}
			catch (IOException e) {
				System.out.println("IOException");
				e.printStackTrace();
				continue SUCCESSFULSERVERCONNECTIONLOOP;
			}
		}
		
		return new InetSocketAddress(ia, serverPortInt);
	}
	
	private static Socket connectToServer(InetSocketAddress serverAddress) throws IOException {
		Socket s = new Socket();
		s.connect(serverAddress);
		return s;
	}
	
}
