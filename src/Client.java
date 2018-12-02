import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.*;
import java.io.*;

/* 
 * GUI Portion of the Client
 * This GUI interacts with the client 
 * Written by: Emerson Moniz
 * Date: 11/30/2018
 */
 
 /*
 * Client Main Function Send and receive message from multiple clients 
 * Written by: Alex J. Monteiro De Pina
 * Date: 11/29/2018
 */

public class Client extends Application{
	private static String userName;
	private static int socket;
	/**
	 * Implemented by Alex J. Monteiro De Pina
	 * Constructor for the client, this is not final yet may need changes
	 * @param s the user name of the client 
	 * @param num the socket number of the Chat is going to join
	 */
	public Client(String s, int num) { 
		userName = s;
		socket = num;
	}
	/**
	 * Implemented by Alex J. Monteiro De Pina
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Socket cs = new Socket("localhost", socket); // server
			System.out.println("Client is Runnig");
			DataInputStream dins = new DataInputStream(cs.getInputStream());
			DataOutputStream douts = new DataOutputStream(cs.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			Thread sendMessage = new Thread(new Runnable() {
				public void run() {
					while (true) {
						String msgout;
						try {
							msgout = br.readLine();
							douts.writeUTF(msgout);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			});
			Thread readMessage = new Thread(new Runnable() {
				public void run() {
					while (true) {
						String msgin;
						try {
							msgin = dins.readUTF();
							System.out.println(msgin);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			});
			sendMessage.start();
			readMessage.start();
			//cs.close();
		} catch (Exception e) {
			System.out.println("\n" + e.getMessage());
			System.exit(1);
		}
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception{
//		Load the Client GUI  
		Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindowView.fxml"));
		primaryStage.setTitle("NP Chatroom");
		primaryStage.setScene(new Scene(mainWindow,400,150));
		primaryStage.show();
	}
}
