
public class Character extends MovingObject{
	private int life;
	private int power;
	public Character(int life, int power) {
		image = GamePage.character1;
		this.life = life;
		this.power = power;
		width = image.getWidth();
		height = image.getHeight();
		x = 250;
		y = 600;
		
	}
	public void moveTo(int x) {
		this.x = x - width/2;
	}
	public Bullet shoot() {
		Bullet b = new Bullet(x+this.width/2, y-this.height/2);
		return b;
	}
	public void damaged() {
		life = life - 1;
	}
	public void heal() {
		life = life + 1;
	}
	public void strengthen() {
		power = power + 1;
	}
	public boolean buffed(MovingObject obj) {
		int objX = obj.getX();
		int objY = obj.getY();
		return objX>x-obj.getWidth()/2 && objX<x+width && objY>y-obj.getHeight() && objY<y; 
	}
	public int getLife() {
		return life;
	}
	public int getPower() {
		return power;
	}
}
