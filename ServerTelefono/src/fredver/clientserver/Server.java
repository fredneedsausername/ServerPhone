package fredver.clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLSocket;

import fredver.constants.Constants;

import javax.net.ssl.SSLServerSocketFactory;

public class Server {
	
	private static Scanner terminalScanner = new Scanner(System.in);
	private static boolean isServerStarted = false; // represents if the server is turned on or off
	private static SSLServerSocket serverSocket;
	private static Set<SSLSocket> connections = new HashSet<>();
	
	public static void main(String[] args) {
		 administrationConsole();	
	}
	
	private static void print(String message) { // to simplify code
		System.out.println(message);
	}
	
	private static void administrationConsole() {
		while(true) {
			OUTER_LOOP:
				while(!isServerStarted) {
					switch(printAdminConsoleMainMenu()) {
					case 0: 
						try {
							turnOffServer();
							print("Server successfully terminated");
						} catch (IOException e) {
							while(true) {
								print("There was a problem shutting the server down");
								print("It was only partially shut down");
								print("The exception's message is:");
								print(e.getMessage());
								print("Do you want to force the server shutdown?");
								print("this means transfer of files could be halted suddenly");
								print("leading to potential loss of data that is being received");
								print("or not complete transfer of data from the server");
								print("Write:");
								print("0: don't shutdown and go back to the main panel");
								print("1: shutdown and potentially lose data");
								
								switch(terminalScanner.nextLine().charAt(0)) {
								case '0':
									break OUTER_LOOP;
								case '1':
									System.exit(0);
								default:
									continue;
								}
							}
						}
						break;
					case 1: 
						printAdminManageOnlineStatus();
						break;
					case 2: // implementare manage users
						break;
					case 3: // implementare navigate folders
						break;
					case 4: // implementare manage active connections
						break;
					case 5: // implementare benchmark e specs
						break;
					}
				}
			while(isServerStarted) {
				switch(printAdminConsoleMainMenuOffline()) {
				case 0: 
					System.exit(0);
					break;
				case 1: 
					// implementare manage users
					break;
				case 2: 
					// implementare navigate folders
					break;
				case 3: 
					// implementare benchmark e specs
					break;
				case 4:
					printAdminManageOnlineStatus();
					break;
				}
			}
		}
		
	}	
	
	private static int printAdminConsoleMainMenu() {
		
		while(true) {
			print("Welcome to the admin console");
			print("write one of the following numbers:");
			print("0: exit and turn off server");
			print("1: manage server online status");
			print("2: manage users");
			print("3: navigate folders");
			print("4: manage active connections");
			print("5: server specs and benchmark");
			
			char answerChar = terminalScanner.nextLine().charAt(0);
			
			switch(answerChar) {
			case '0': 
			case '1': 
			case '2': 
			case '3':
			case '4':
			case '5':
				return answerChar - '0';
			default: 
				print("Invalid input, try again");
				continue;
			}
			
		}		
		
	}
	
	private static int printAdminConsoleMainMenuOffline() {
		while(true) {
			print("Welcome to the admin console");
			print("write one of the following numbers:");
			print("0: exit");
			print("1: manage users");
			print("2: navigate folders");
			print("3: server specs and benchmark");
			print("4: manage server online status");
			
			char answerChar = terminalScanner.nextLine().charAt(0);
			
			switch(answerChar) {
			case '0': 
			case '1': 
			case '2': 
			case '3':
			case '4':
				return answerChar - '0';
			default: 
				print("Invalid input, try again");
				continue;
			}
			
		}		
	}
	
	private static void printAdminManageOnlineStatus() {
		boolean exitToMainMenu = false;
		while(!exitToMainMenu) {
			print("Welcome to the online status management menu");
			print("write one of the following numbers:");
			print("0: go to main menu");
			print("1: check server's online status");
			print("2: turn server on");
			print("3: turn server off");
			
			char answerChar = terminalScanner.nextLine().charAt(0);
			
			switch(answerChar) {
			case '0':
				exitToMainMenu = true;
				continue;
			case '1':
				print(isServerStarted ? "Server is active" : "Server is turned off");
				continue;
			case '2':
				if(isServerStarted) {
					print("Server is already active");
				} else {
					print("Turning server on...");
					try {
						turnOnServer();
						print("Server successfully turned on");
					} catch (IOException e) {
						print("Problem binding the ServerSocket to the port " + Constants.SERVER_PORT_NUMBER);
						print("The exception's message is:");
						print(e.getMessage());
						break;
					}
				}
				exitToMainMenu = true;
				continue;
			case '3':
				if(isServerStarted) {
					print("Turning server off...");
					try {
						turnOffServer();
						print("Server successfully turned off");
					} catch (IOException e) {
						while(true) {
							print("There was a problem shutting the server down");
							print("It was only partially shut down");
							print("The exception's message is:");
							print(e.getMessage());
							print("Do you want to force the server shutdown?");
							print("this means transfer of files could be halted suddenly");
							print("leading to potential loss of data that is being received");
							print("or not complete transfer of data from the server");
							print("Write:");
							print("0: don't shutdown and go back to the main panel");
							print("1: shutdown and potentially lose data");
							
							switch(terminalScanner.nextLine().charAt(0)) {
							case '0':
								return;
							case '1':
								System.exit(0);
							default:
								continue;
							}
						}
					}
				} else {
					print("Server is already turned off");
				}
				exitToMainMenu = true;
				continue;
			default:
				print("Invalid input, try again");
				continue;
			}
		}
	}
	
	private static void turnOffServer() throws IOException { IMPLEMENT BETTER CLOSING OF SOCKETS WITH A LIST OF EXCEPTION THROWN
		for(SSLSocket s : connections) {
			connections.remove(s);
			s.close();
			isServerStarted = false;
		}
		serverSocket.close();
	}
	
	private static void turnOnServer() throws IOException {			
			serverSocket = (SSLServerSocket) 
					( (SSLServerSocketFactory) 
							SSLServerSocketFactory.getDefault() ).createServerSocket();
		serverSocket.bind(new InetSocketAddress("0.0.0.0", Constants.SERVER_PORT_NUMBER));
	}
}
