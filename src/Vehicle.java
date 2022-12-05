
import javax.swing.JLabel;

public class Vehicle extends Sprite implements Runnable {
	private Frog1 frog1;
	private FroggerServer gamekill;
	private Boolean moving;
	private Thread newt;
	private JLabel vehicleLabel;
	private int speed;
	public Vehicle() {
		this.moving = false;
		this.speed = 0;
	}

	public Boolean getMoving() {
		return moving;
	}
	public void setMoving(Boolean moving) {
		this.moving = moving;
	}
	public void SetSpeed(int s) {
		this.speed = s;
	}
	public int GetSpeed() {
		return speed;
	}
	public void StartMoving() {
		this.moving = false;
		this.ThreadMove(this.moving);
	}
	public void SetVehicleLabel(JLabel temp) {
		this.vehicleLabel = temp;
	}
	public void GrabFrog1(Frog1 temp) {
		this.frog1 = temp;
	}
	public void GrabGame(FroggerServer gameprep) {
		this.gamekill = gameprep;
		
	}
	public void ThreadMove(Boolean move) {
		if (!move) {
			newt = new Thread(this, "Vehicle1");
			newt.start();
		}
	}
	@Override
	public void run() {
		this.moving = true;
		while (this.moving) {
			int xqw = this.getX();
			int yqw = this.getY();
			int speed = this.GetSpeed();
			xqw += speed;
			if (xqw >= Gameproperties.SCREEN_WIDTH+150) {
				xqw = -1 * this.width;
			}
			this.setX(xqw);
			this.setY(yqw);
			this.detectCollision();
			this.vehicleLabel.setLocation(xqw, yqw);
			
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				
			}
		}
		this.moving = false;
	}
	public void detectCollision() {
		if (this.rect.intersects(frog1.rect)) {
			System.out.println("touched");
			//gamekill.Resetfrogger();
		}
	}
}
//_______________________________________
//_______________________________________
//_______________________________________
//_______________________________________

class ReverseVehicle extends Sprite implements Runnable {
	private Boolean moving;
	private Thread fast;
	private JLabel vehicleLabel;
	private Frog1 frog1;
	private FroggerServer gamekill;
	private int speed;
	public ReverseVehicle() {
		this.moving = false;
		this.speed = 0;
	}
	public Boolean getMoving() {
		return moving;
	}
	public void SetSpeed(int s) {
		this.speed = s;
	}
	public int GetSpeed() {
		return speed;
	}
	public void setMoving(Boolean moving) {
		this.moving = moving;
	}
	public void StartMoving() {
		this.moving = false;
		this.ThreadMove(this.moving);
	}
	public void GrabFrog1(Frog1 temp) {
		this.frog1 = temp;
	}
	public void GrabGame(FroggerServer gameprep) {
		this.gamekill = gameprep;
		
	}
	public void SetVehicleLabel(JLabel temp) {
		this.vehicleLabel = temp;
	}
	public void ThreadMove(Boolean move) {
		if (!move) {
			fast = new Thread(this, "Vehicle1");
			fast.start();
		}
	}
	@Override
	public void run() {
		this.moving = true;
		while (this.moving) {
			int xqw = this.getX();
			int yqw = this.getY();
			int speed = this.GetSpeed();
			xqw -= speed;
			if (xqw <= Gameproperties.SCREEN_WIDTH-1200) {
				xqw = 8 * this.width;
			}
			this.setX(xqw);
			this.setY(yqw);
			this.detectCollision();
			this.vehicleLabel.setLocation(xqw, yqw);
			
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				
			}
		}
		this.moving = false;
	}
	public void detectCollision() {
		if (this.rect.intersects(frog1.rect)) {
			System.out.println("touched");
			//gamekill.Resetfrogger();
		}
	}
}
//_______________________________________
//_______________________________________
//_______________________________________
//_______________________________________

