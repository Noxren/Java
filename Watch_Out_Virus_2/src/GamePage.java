import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
public class GamePage extends JPanel{
	private int score = 0;
	private int money = 0;
	private Timer timer;
	private JFrame f;
	
	private static final int INTERVEL = 10;
	public static final int FRAME_WIDTH = 600;
	public static final int FRAME_HEIGHT = 800;
	
	public static BufferedImage background;
	public static BufferedImage life;
	public static BufferedImage character1;
	public static BufferedImage character2;
	public static BufferedImage character3;
	public static BufferedImage character4;
	public static BufferedImage bullet;
	public static BufferedImage virus1;
	public static BufferedImage virus2;
	public static BufferedImage virus3;
	public static BufferedImage health;
	public static BufferedImage vaccine;
	
	private Character character = new Character(3,1);
	private ArrayList<Bullet> bullets= new ArrayList<Bullet>();
	private ArrayList<Virus> virusEnemy = new ArrayList<Virus>();
	private ArrayList<Health> healthItem = new ArrayList<Health>();
	private ArrayList<Vaccine> vaccineItem = new ArrayList<Vaccine>();
	
	static {
		try {
			background = ImageIO.read(new File("D:\\大學作業\\大三下\\程式設計\\專案設計\\Watch_Out_Virus_2\\taipei-1851948_1920.jpg"));
			life = ImageIO.read(new File("D:\\大學作業\\大三下\\程式設計\\專案設計\\Watch_Out_Virus_2\\heart.png"));
			character1 = ImageIO.read(new File("D:\\大學作業\\大三下\\程式設計\\專案設計\\Watch_Out_Virus_2\\Tedros.png"));
			character2 = ImageIO.read(new File("D:\\大學作業\\大三下\\程式設計\\專案設計\\Watch_Out_Virus_2\\Trump.png"));
			character3 = ImageIO.read(new File("D:\\大學作業\\大三下\\程式設計\\專案設計\\Watch_Out_Virus_2\\ShiJong.png"));
			character4 = ImageIO.read(new File("D:\\大學作業\\大三下\\程式設計\\專案設計\\Watch_Out_Virus_2\\Tzuyu.png"));
			bullet = ImageIO.read(new File("D:\\大學作業\\大三下\\程式設計\\專案設計\\Watch_Out_Virus_2\\bullet.png"));
			virus1 = ImageIO.read(new File("D:\\大學作業\\大三下\\程式設計\\專案設計\\Watch_Out_Virus_2\\virus1.png"));
			virus2 = ImageIO.read(new File("D:\\大學作業\\大三下\\程式設計\\專案設計\\Watch_Out_Virus_2\\virus2.png"));
			virus3 = ImageIO.read(new File("D:\\大學作業\\大三下\\程式設計\\專案設計\\Watch_Out_Virus_2\\virus3.png"));
			health = ImageIO.read(new File("D:\\大學作業\\大三下\\程式設計\\專案設計\\Watch_Out_Virus_2\\healthcare-and-medical.png"));
			vaccine = ImageIO.read(new File("D:\\大學作業\\大三下\\程式設計\\專案設計\\Watch_Out_Virus_2\\vaccination.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		paintLife(g);
		paintCharacter(g);
		paintBullet(g);
		paintVirus(g);
		paintHealth(g);
		paintVaccine(g);
		paintScore(g);
		paintLifeAmount(g);
	}
	public void paintLife(Graphics g) {
		g.drawImage(life, 10, 5, null);
	}
	public void paintCharacter(Graphics g) {
		g.drawImage(character.getImage(), character.getX(), character.getY(), null);
	}
	public void paintBullet(Graphics g) {
		for(Bullet bs : bullets) {
			g.drawImage(bs.getImage(), bs.getX() - bs.getWidth()/2, bs.getY(), null);
		}
	}
	public void paintVirus(Graphics g) {
		for(Virus v : virusEnemy) {
			g.drawImage(v.getImage(), v.getX(), v.getY(), null);
		}
	}
	public void paintHealth(Graphics g) {
		for(Health h : healthItem) {
			g.drawImage(h.getImage(), h.getX(), h.getY(), null);
		}
	}
	public void paintVaccine(Graphics g) {
		for(Vaccine vac : vaccineItem) {
			g.drawImage(vac.getImage(), vac.getX(), vac.getY(), null);
		}
	}
	public void paintScore(Graphics g) {
		String yourScore = String.format("%010d", score);
		g.setFont(new Font("Dialog", 1, 40));
		g.drawString(yourScore, 350, 50);
	}
	public void paintLifeAmount(Graphics g) {
		g.setFont(new Font("Dialog", 1, 40));
		g.drawString("X" + character.getLife(), 80, 50);
	}
	public GamePage(JFrame f) {
		this.f = f;
	}
	public static void main(String args[]) {
		JFrame frame = new JFrame("小心疫疫");
		GamePage game = new GamePage(frame);
		frame.add(game);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		game.action();
	}
	public void action() {
		MouseAdapter m = new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				character.moveTo(x);
			}
			public void mouseClicked(MouseEvent e) {
				Bullet b = character.shoot();
				bullets.add(b);
			}
		};
		this.addMouseMotionListener(m);
		this.addMouseListener(m);
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				bulletMove();
				createVirus();
				virusMove();
				itemMove();
				bangAction();
				checkVirusLife();
				getItem();
				deleteOutOfBound();
				repaint();
				checkGameOver();
			}
		}, INTERVEL, INTERVEL);
	}
	public void bulletMove() {
		for(Bullet bs : bullets) {
			bs.move();
		}
	}
	public void virusMove() {
		for(Virus v : virusEnemy) {
			v.move();
		}
	}
	int itemIndex = 0;
	public void itemMove() {
		itemIndex++;
		for(Health h : healthItem) {
			if(itemIndex % 2 == 0) {
				h.move();
			}
		}
		for(Vaccine vac : vaccineItem) {
			if(itemIndex % 2 == 0) {
				vac.move();
			}
		}
	}
	int virusIndex = 0;
	public void createVirus() {
		virusIndex++;
		if(virusIndex % 200 == 0) {
			if(score < 1000) {
				Virus v1 = new Virus(100, 1, 3);
				virusEnemy.add(v1);
			}
			else if(score >= 1000 && score < 3000) {
				Virus v2 = new Virus(200, 3, 10);
				virusEnemy.add(v2);
			}
			else if(score >= 3000) {
				Virus v3 = new Virus(400, 10, 25);
				virusEnemy.add(v3);
			}
		}
	}
	public void bangAction() {
		for(Virus v : virusEnemy) {
			bang(v);
		}
	}
	public void checkVirusLife() {
		ArrayList<Virus> virusLive = new ArrayList<Virus>();
		for(Virus v : virusEnemy) {
			if(v.getLife() > 0) {
				virusLive.add(v);
			}
			else {
				score = score + v.getScore();
				money = money + v.getMoney();
				itemFall(v.getX(), v.getY());
			}
		}
		virusEnemy = virusLive;
	}
	public void getItem() {
		ArrayList<Health> healthLive = new ArrayList<Health>();
		ArrayList<Vaccine> vaccineLive = new ArrayList<Vaccine>();
		for(Health h : healthItem) {
			if(character.buffed(h)) {
				character.heal();
			}
			else {
				healthLive.add(h);
			}
		}
		for(Vaccine vac : vaccineItem) {
			if(character.buffed(vac)) {
				character.strengthen();
			}
			else {
				vaccineLive.add(vac);
			}
		}
		healthItem = healthLive;
		vaccineItem = vaccineLive;
	}
	public void deleteOutOfBound() {
		ArrayList<Virus> virusLive = new ArrayList<Virus>();
		for(Virus v : virusEnemy) {
			if(!v.outOfBound()) {
				virusLive.add(v);
			}
			else {
				character.damaged();
			}
		}
		virusEnemy = virusLive;
		
		ArrayList<Bullet> bulletLive = new ArrayList<Bullet>();
		for(Bullet b : bullets) {
			if(!b.outOfBound()) {
				bulletLive.add(b);
			}
		}
		bullets = bulletLive;
		
		ArrayList<Health> healthLive = new ArrayList<Health>();
		for(Health h : healthItem) {
			if(!h.outOfBound()) {
				healthLive.add(h);
			}
		}
		healthItem = healthLive;
		
		ArrayList<Vaccine> vaccineLive = new ArrayList<Vaccine>();
		for(Vaccine vac : vaccineItem) {
			if(!vac.outOfBound()) {
				vaccineLive.add(vac);
			}
		}
		vaccineItem = vaccineLive;
	}
	public void checkGameOver() {
		if(character.getLife() == 0) {
			GameOverPage gPanel = new GameOverPage(f, this);
			setVisible(false);
			f.add(gPanel);
			f.setVisible(true);
			timer.cancel();
		}
	}
	public void bang(Virus v) {
		for(Bullet b : bullets) {
			if(b.shot(v)) {
				bullets.remove(b);
				v.damaged(character);
				break;
			}
		}
	}
	public void itemFall(int x, int y) {
		double index = Math.random();
		if(index < 0.05) {
			Health h = new Health(x, y);
			healthItem.add(h);
		}
		if(index > 0.95) {
			Vaccine vac = new Vaccine(x, y);
			vaccineItem.add(vac);
		}
	}
	public int getMoney() {
		return money;
	}
	public int getScore() {
		return score;
	}
}
