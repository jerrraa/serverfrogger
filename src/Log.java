
import javax.swing.JLabel;

public class Log extends Sprite implements Runnable {
	private Boolean moving, direction;
	private JLabel Loglabel;
	private int speed;
	private Thread t;
	private Frog1 frog1;
	private JLabel froglabel;
	public Log () {
		this.speed = 0;
		this.moving = false;
		this.direction = false;
	}

	public Boolean getMoving() {
		return moving;
	}

	public void setMoving(Boolean moving) {
		this.moving = moving;
	}
	public void SetLogLabel(JLabel log) {
		this.Loglabel = log;
	}
	public void GrabPlayerFrog(Frog1 frog1) {
		this.frog1 = frog1;
	}
	public void GrabFrogLabel(JLabel temp) {
		this.froglabel = temp;
	}
	public void GrabGame(FroggerServer temp) {
	}
	public void SetSpeed(int s) {
		this.speed = s;
	}
	public int GetSpeed() {
		return this.speed;
	}
	public void setDirection(Boolean temp) {
		this.direction = temp;
		
	}
	public Boolean GetDirection() {
		return this.direction;
	}
	public void StartMoving() {
		this.moving = false;
		this.ThreadMove(this.moving);
	}
	public void ThreadMove(Boolean move) {
		if (!move) {
			t = new Thread(this, "Vehicle1");
			t.start();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.moving = true;
		while (this.moving) {
			int horizontal = this.getX();
			int vertical = this.getY();
			int speed = this.GetSpeed();
			
			if (GetDirection() == true) {
				horizontal -= speed;
				if (horizontal <= Gameproperties.SCREEN_WIDTH-1200) {
					horizontal = 8 * this.width;
				}
				this.setX(horizontal);
				this.setY(vertical);
			} else {
				horizontal += speed;
				if (horizontal >= Gameproperties.SCREEN_WIDTH+350) {
					horizontal = -1 * this.width;
				}
				this.setX(horizontal);
				this.setY(vertical);
			}
			this.detectLog();
			this.Loglabel.setLocation(this.getX(), this.getY());
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				
			}
		}
		this.moving = false;
	}
	public void detectLog() {
		if (this.rect.intersects(frog1.rect)) {
			if (GetDirection() == true) {
				froglabel.setLocation(frog1.getX()-this.GetSpeed(), frog1.getY());
				frog1.setX(frog1.getX()-this.GetSpeed());
			} else {
				froglabel.setLocation(frog1.getX()+this.GetSpeed(), frog1.getY());
					frog1.setX(frog1.getX()+this.GetSpeed());
			}
		}
	}
}
