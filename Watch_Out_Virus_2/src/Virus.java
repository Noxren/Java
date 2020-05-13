
public class Virus extends MovingObject{
	private int life;
	private int score;
	private int money;
	public Virus(int score, int money, int life) {
		x = (int)(Math.random()*550);
		y = 0;
		this.score = score;
		this.money = money;
		this.life = life;
		if(score == 100) {
			image = GamePage.virus1;
		}
		else if(score == 200) {
			image = GamePage.virus2;
		}
		else if(score == 400) {
			image = GamePage.virus3;
		}
		width = image.getWidth();
		height = image.getHeight();
	}
	public void move() {
		this.y = y+1;
	}
	public void damaged(Character c) {
		life = life - c.getPower();
	}
	public boolean outOfBound() {
		return y > GamePage.FRAME_HEIGHT;
	}
	public int getLife() {
		return life;
	}
	public int getScore() {
		return score;
	}
	public int getMoney() {
		return money;
	}
}
