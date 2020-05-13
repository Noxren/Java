
public class Bullet extends MovingObject{
	public Bullet(int x, int y) {
		this.x = x;
		this.y = y; 
		this.image = GamePage.bullet;
		width = image.getWidth();
		height = image.getHeight();
	}
	public void move() {
		this.y = y-1;
	}
	public boolean shot(Virus v) {
		int vx = v.getX();
		int vy = v.getY();
		return x>vx-width/2 && x<vx+v.getWidth() && y<vy+v.getHeight() && y>vy;
	}
	public boolean outOfBound() {
		return y < -height;
	}
}
