import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameServer implements Runnable {
	final int CLIENT_PORT = 5656;

	private Socket s;
	private Scanner in;

	public GameServer (Socket aSocket) {
		this.s = aSocket;
	}
	public void run() {
		
		try {
			in = new Scanner(s.getInputStream());
			processRequest( );
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	//processing the requests
	public void processRequest () throws IOException {
		//if next request is empty then return
		while(true) {
			if(!in.hasNext( )){
				return;
			}
			String command = in.next();
			if (command.equals("Quit")) {
				return;
			} else {
				executeCommand(command);
			}
		}
	}
	
	public void executeCommand(String command) throws IOException{
		Socket s2 = new Socket("localhost", CLIENT_PORT);
		if ( command.equals("VERT")) {
			String direction = in.next();
		
			
			//Initialize data stream to send data out
			OutputStream outstream = s2.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);
			String commandOut = "VERT "+direction;
			//System.out.println("Sending: " + commandOut);
			out.println(commandOut);
			out.flush();
			s2.close();

		} else if (command.equals("SIDE")) {
			String direction = in.next();
			
			
			//Initialize data stream to send data out
			OutputStream outstream = s2.getOutputStream();
			PrintWriter out = new PrintWriter(outstream);
			String commandOut = "SIDE "+direction;
			//System.out.println("Sending: " + commandOut);
			out.println(commandOut);
			out.flush();
			s2.close();
		}
	}
}
