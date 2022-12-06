
import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.net.ServerSocket;
import java.net.Socket;

public class FroggerServer extends JFrame {
	private static final long serialVersionUID = 1L;
	//
	private Frog1 frog1;
	private Container content;
	private JLabel frog1Label;
	private ImageIcon frog1Image;
	
	private JLabel VEHICLElabel, VEHICLElabel2, VEHICLElabel3;;
	private Vehicle vehiclelane[];
	private ReverseVehicle vehiclelane1[];
	private Vehicle vehiclelane2[];
	
	private JLabel LOGlabel, LOGlabel2, LOGlabel3, LOGlabel4, LOGlabel5;
	
	private Log LogLane[];
	private Log LogLane1[];
	private Log LogLane2[];
	private Log LogLane3[];
	private Log LogLane4[];
	
	private int offset = 300;
	private int heightveh = 90;
	private int widthveh = 127;
	//life and score
	private JLabel LifeText, ScoreText;
	private int lifes = 3;
	private int score = 50;
	
	private int xreset = 400;
	private int yreset = 914;
	
	public FroggerServer() throws IOException {
	}

	//comment test
	public static void main(String[] args) throws IOException {
		final int SERVER_PORT = 5556;
		ServerSocket server = new ServerSocket(SERVER_PORT);
		System.out.println("Waiting for clients to connect...");
		while(true) {
			Socket s = server.accept();
			//System.out.println ("Client connected! On port: "+SERVER_PORT);
			
			GameServer myService2 = new GameServer(s);
			Thread t = new Thread(myService2);
			t.start();
		}
	}
}
