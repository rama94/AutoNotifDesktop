package autonotif;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionHelper {
	
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static InputStreamReader inputStreamReader;
	private static BufferedReader bufferedReader;
	private static String message;
	private static final int port = 51094;

	protected ConnectionHelper() throws IOException {
		ArrayList<InetAddress> listAddr = new ArrayList<InetAddress>(); 	
		
		Enumeration<NetworkInterface> e = null;
		try {
			e = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e2) {
			System.out.println("Couldn't get any network!!");
		}		
		
		while(e.hasMoreElements()){		    
			NetworkInterface n = (NetworkInterface) e.nextElement();
			
			if (n.isLoopback() || !n.isUp() || n.getDisplayName().contains("VirtualBox") ) continue;
			
			Enumeration<InetAddress> ee = n.getInetAddresses();
			while (ee.hasMoreElements()){
				InetAddress i = (InetAddress) ee.nextElement();
				if (i instanceof Inet6Address) continue;
				listAddr.add(i);
				}
			}
		
		createListener(listAddr);
		
	}
	
	private void createListener(ArrayList<InetAddress> listAddr) {
		for(InetAddress addr : listAddr) {
			
			ExecutorService executorService  = Executors.newFixedThreadPool(1);
			executorService.execute(new Runnable() {
			    public void run() {
					boolean connected = false;
					
					try {
						serverSocket = new ServerSocket(port, 50, addr);
						connected = true;
					} catch (IOException e1) {
						System.out.println("Couldn't create socket in "+addr.getHostAddress()+" with port "+port);
					}
					
					while (connected) {
				        try {
				        	
				        	System.out.println("Server started. Listening to "+addr.getHostAddress()+" with port "+port);
		
				            clientSocket = serverSocket.accept();   //accept the client connection
				            inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
				            bufferedReader = new BufferedReader(inputStreamReader); //get client msg                    
				            message = bufferedReader.readLine();
		
				            System.out.println(message);
				            inputStreamReader.close();
				            clientSocket.close();
				            
				            if (!message.equals("Establishing connection")) new NotifFrame(message);
				            
//				            } 
		
				        } catch (IOException ex) {
				            System.out.println("Problem in message reading!");
				        }
				    }
					
			    }
			});
			executorService.shutdown();
		}
	}
	
}
