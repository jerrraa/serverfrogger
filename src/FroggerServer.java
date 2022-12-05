
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

public class FroggerServer extends JFrame implements KeyListener{
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
		
		int SERVER_PORT = 5556;
		ServerSocket server = new ServerSocket(SERVER_PORT);
		System.out.println("Waiting for clients to connect...");
		while(true) {
			Socket s = server.accept();
			System.out.println ("Client connected! On port: "+SERVER_PORT);
			
			GameServer myService2 = new GameServer(s);
			Thread t = new Thread(myService2);
			t.start();
		}
	}

	//comment test

	public static void main(String[] args) throws IOException {
		FroggerServer game = new FroggerServer();
		
		game.setVisible(true);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int x = frog1.getX(); int y = frog1.getY();
		if (frog1.getMoving() == true) {
		//modify position
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			y -= Gameproperties.CHARACTER_STEP;

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			y += Gameproperties.CHARACTER_STEP;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			x -= Gameproperties.CHARACTER_STEP-49;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			x += Gameproperties.CHARACTER_STEP-49;
		} else {
			System.out.println("invalid operation");
		}
		//check if it reaches out of bounds
		DetectBorder(x, y);
		frog1Label.setLocation(frog1.getX(), frog1.getY());
		//a function in frog1 class to check if it interacts with top grass
		frog1.CheckforTop();
		}
	}
	public void DetectBorder(int x, int y) {
		//right side of the screen detection
		if (x >= 973) {
			x = 933;
		} 
		//bottom side of the screen
		if (y >= 1000) {
			y = 914;
		}
		//left side of the screen
		if (x <= -30) {
			x = -10;
		}
		//top side of the screen
		if (y <= -50) {
			y = 14;
		}
		frog1.setX(x);
		frog1.setY(y);
	}
	public void DisplayContents() {
		//declare player 1 frog
		frog1 = new Frog1();
		frog1.setX(460); frog1.setY(914);
		frog1.setWidth(67); frog1.setHeight(55);
		frog1.setImage("greenfrog.png");
		frog1.SetLives(lifes);
		frog1.GetGame(this);
		frog1.setMoving(true);
		//set up screen
		setSize(Gameproperties.SCREEN_WIDTH, Gameproperties.SCREEN_HEIGHT+45);
		content = getContentPane();
		content.setBackground(Color.white);
		setLayout(null);
		
		//frog label
		frog1Label = new JLabel();
		frog1Image = new ImageIcon(getClass().getResource("textures/greenfrog.png"));
		frog1Label.setIcon(frog1Image);
		frog1Label.setSize(frog1.getWidth(), frog1.getHeight());
		frog1Label.setLocation(frog1.getX(), frog1.getY());
		// score label
		LifeText = new JLabel("");
		LifeText.setFont(new Font("Calibri", Font.BOLD, 30));
		LifeText.setText("Lifes: " + lifes);
		LifeText.setForeground(Color.WHITE);
		LifeText.setSize(200, 200);
		LifeText.setLocation(10, -56);
		//score label
		ScoreText = new JLabel("");
		ScoreText.setFont(new Font("Calibri", Font.BOLD, 30));
		ScoreText.setText("Score: " + DataScore.INSTANCE.GetScore());
		ScoreText.setForeground(Color.WHITE);
		ScoreText.setSize(200, 200);
		ScoreText.setLocation(10, -27);
		//background of panel
		JLabel Backgroundlab = new JLabel();
		ImageIcon Backgroundimg = new ImageIcon(getClass().getResource("textures/background.png"));
		Backgroundlab.setIcon(Backgroundimg);
		Backgroundlab.setSize(Gameproperties.SCREEN_WIDTH, Gameproperties.SCREEN_HEIGHT);
		Backgroundlab.setLocation(0,5);
		setLocationRelativeTo(null);  
		//insert all functions
		add(LifeText);
		add(ScoreText);
		InsertVehicleRows();
		InsertVehicleRows1();
		InsertVehicleRows2();
		add(frog1Label);
		InsertLogRows();
		InsertLogRows2();
		InsertLogRows3();
		InsertLogRows4();
		InsertLogRows5();
		ThreadForLanes();
		add(Backgroundlab);
		//user input
		content.addKeyListener(this);
		content.setFocusable(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		
	}
	//initializing
	public void ThreadForLanes() {
		for(ReverseVehicle one : vehiclelane1) {
			one.GrabFrog1(frog1);
			one.GrabGame(this);
			one.StartMoving();
		}
		for(Vehicle two : vehiclelane) {
			two.GrabFrog1(frog1);
			two.GrabGame(this);
			two.StartMoving();
		}
		for(Vehicle three : vehiclelane2) {
			three.GrabFrog1(frog1);
			three.GrabGame(this);
			three.StartMoving();
		}
		for(Log log1 : LogLane) {
			log1.GrabPlayerFrog(frog1);
			log1.GrabFrogLabel(frog1Label);
			log1.GrabGame(this);
			log1.StartMoving();
		}
		for(Log log2 : LogLane1) {
			log2.GrabPlayerFrog(frog1);
			log2.GrabFrogLabel(frog1Label);
			log2.GrabGame(this);
			log2.StartMoving();
		}
		for(Log log3 : LogLane2) {
			log3.GrabPlayerFrog(frog1);
			log3.GrabFrogLabel(frog1Label);
			log3.GrabGame(this);
			log3.StartMoving();
		}
		for(Log log4 : LogLane3) {
			log4.GrabPlayerFrog(frog1);
			log4.GrabFrogLabel(frog1Label);
			log4.GrabGame(this);
			log4.StartMoving();
		}
		for(Log log5 : LogLane4) {
			log5.GrabPlayerFrog(frog1);
			log5.GrabFrogLabel(frog1Label);
			log5.GrabGame(this);
			log5.StartMoving();
		}
	}
	//this belongs to middle row
	public void InsertVehicleRows() {
		vehiclelane = new Vehicle[4];
		int Xoffset = 0;
		for (int i = 0; i<4; i++ ) {
			vehiclelane[i] = new Vehicle();
			VEHICLElabel = new JLabel(); 
			VEHICLElabel.setSize(widthveh, heightveh);
			vehiclelane[i].SetVehicleLabel(VEHICLElabel);
			vehiclelane[i].setHeight(heightveh); vehiclelane[i].setWidth(widthveh);
			vehiclelane[i].setX(vehiclelane[i].getX() + Xoffset);
			vehiclelane[i].setY(710);
			vehiclelane[i].SetSpeed(Gameproperties.CHARACTER_STEP-60);
			VEHICLElabel.setLocation(vehiclelane[i].getX(), vehiclelane[i].getY());
			
			add(VEHICLElabel);
			Xoffset += offset-20;
		}
	}
	//this belongs to top row
	public void InsertVehicleRows1(){
		vehiclelane1 = new ReverseVehicle[4];
		int Xoffset = 0;
		for (int i = 0; i<4; i++ ) {
			vehiclelane1[i] = new ReverseVehicle();
			VEHICLElabel2 = new JLabel(); 
			VEHICLElabel2.setSize(widthveh, heightveh);
			vehiclelane1[i].SetVehicleLabel(VEHICLElabel2);
			vehiclelane1[i].setHeight(heightveh); vehiclelane1[i].setWidth(widthveh);
			vehiclelane1[i].setX(vehiclelane1[i].getX() + Xoffset);
			vehiclelane1[i].setY(620);
			vehiclelane1[i].SetSpeed(Gameproperties.CHARACTER_STEP-80);
			VEHICLElabel2.setLocation(vehiclelane1[i].getX(), vehiclelane1[i].getY());
			add(VEHICLElabel2);
			Xoffset += offset+20;
		}
	}
	//this belongs to bottom row
	public void InsertVehicleRows2(){
		vehiclelane2 = new Vehicle[4];
		int Xoffset = 0;
		for (int i = 0; i<4; i++ ) {
			vehiclelane2[i] = new Vehicle();
			vehiclelane2[i].GrabFrog1(frog1);
			vehiclelane2[i].GrabGame(this);
			VEHICLElabel3 = new JLabel(); 
			VEHICLElabel3.setSize(widthveh, heightveh);
			vehiclelane2[i].SetVehicleLabel(VEHICLElabel3);
			vehiclelane2[i].setHeight(heightveh); vehiclelane2[i].setWidth(widthveh);
			vehiclelane2[i].setX(vehiclelane2[i].getX() + Xoffset);
			vehiclelane2[i].setY(800);
			vehiclelane2[i].SetSpeed(Gameproperties.CHARACTER_STEP-40);
			VEHICLElabel3.setLocation(vehiclelane2[i].getX(), vehiclelane2[i].getY());
			add(VEHICLElabel3);
			Xoffset += offset+50;
		}
	}
	public void InsertLogRows() {
		LogLane = new Log[4];
		int Loffset = 0;
		for (int i = 0; i<4; i++) {
			LogLane[i] = new Log();
			LOGlabel = new JLabel();

			LOGlabel.setSize(200, 80);
			LogLane[i].setHeight(90);
			LogLane[i].setWidth(160);
			LogLane[i].setX(LogLane[i].getX() + Loffset+70);
			LogLane[i].setY(454);
			LogLane[i].SetSpeed(19);
			LogLane[i].setDirection(true);
			LOGlabel.setLocation(LogLane[i].getX(), LogLane[i].getY());
			LogLane[i].SetLogLabel(LOGlabel);
			add(LOGlabel);
			Loffset += offset+80;
		}
	}
	public void InsertLogRows2() {
		LogLane1 = new Log[4];
		int Loffset = 0;
		for (int i = 0; i<4; i++) {
			LogLane1[i] = new Log();
			LOGlabel2 = new JLabel();
			LOGlabel2.setSize(200, 80);
			LogLane1[i].setHeight(95);
			LogLane1[i].setWidth(160);
			LogLane1[i].setX(LogLane1[i].getX() + Loffset);
			LogLane1[i].setY(364);
			LogLane1[i].SetSpeed(15);
			LOGlabel2.setLocation(LogLane1[i].getX(), LogLane1[i].getY());
			LogLane1[i].SetLogLabel(LOGlabel2);
			add(LOGlabel2);
			Loffset += offset+80;
		}
	}
	public void InsertLogRows3() {
		LogLane2 = new Log[4];
		int Loffset = 0;
		for (int i = 0; i<4; i++) {
			LogLane2[i] = new Log();
			LOGlabel3 = new JLabel();
			LOGlabel3.setSize(200, 80);
			LogLane2[i].setHeight(95);
			LogLane2[i].setWidth(160);
			LogLane2[i].setX(LogLane2[i].getX() + Loffset-40);
			LogLane2[i].setY(274);
			LogLane2[i].SetSpeed(30);
			LOGlabel3.setLocation(LogLane2[i].getX(), LogLane2[i].getY());
			LogLane2[i].SetLogLabel(LOGlabel3);
			add(LOGlabel3);
			Loffset += offset+80;
		}
	}
	public void InsertLogRows4() {
		LogLane3 = new Log[4];
		int Loffset = 0;
		for (int i = 0; i<4; i++) {
			LogLane3[i] = new Log();
			LOGlabel4 = new JLabel();
			LOGlabel4.setSize(200, 80);
			LogLane3[i].setHeight(95);
			LogLane3[i].setWidth(160);
			LogLane3[i].setDirection(true);
			LogLane3[i].setX(LogLane3[i].getX() + Loffset+50);
			LogLane3[i].setY(184);
			LogLane3[i].SetSpeed(10);
			
			LOGlabel4.setLocation(LogLane3[i].getX(), LogLane3[i].getY());
			LogLane3[i].SetLogLabel(LOGlabel4);
			add(LOGlabel4);
			Loffset += offset+80;
		}
	}
	public void InsertLogRows5() {
		LogLane4 = new Log[4];
		int Loffset = 0;
		for (int i = 0; i<4; i++) {
			LogLane4[i] = new Log();
			LOGlabel5 = new JLabel();
			LOGlabel5.setSize(200, 80);
			LogLane4[i].setHeight(95);
			LogLane4[i].setWidth(160);
			LogLane4[i].setX(LogLane4[i].getX() + Loffset);
			LogLane4[i].setY(94);
			LogLane4[i].SetSpeed(40);
			LOGlabel5.setLocation(LogLane4[i].getX(), LogLane4[i].getY());
			LogLane4[i].SetLogLabel(LOGlabel5);
			add(LOGlabel5);
			Loffset += offset+80;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}

