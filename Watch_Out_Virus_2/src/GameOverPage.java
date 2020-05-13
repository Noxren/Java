import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

public class GameOverPage extends JPanel {
	public static BufferedImage gameover;
	public static BufferedImage coin;
	private JFrame f;
	private JLabel yourScoreLabel;
	private JLabel highestScoreLabel;
	private JLabel moneyLabel;
	private JButton exitBtn;
	private int score;
	private int highestScore = 0;
	private int money;
	static {
		try {
			gameover=ImageIO.read(new File("D:\\大學作業\\大三下\\程式設計\\專案設計\\Watch_Out_Virus_2\\gameover.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	/**
	 * Create the application.
	 */
	public GameOverPage(JFrame f, GamePage g) {
		this.f = f;
		money = g.getMoney();
		score = g.getScore();
		setLayout(null);
		createBtn();
		createYourScoreLabel();
		createHighestScoreLabel();
		createMoneyLabel();
	}
	public void createYourScoreLabel() {
		yourScoreLabel = new JLabel();
		yourScoreLabel.setText("Your score: " + String.format("%09d", this.score));
		yourScoreLabel.setFont(new Font("Dialog", 1, 40));
		yourScoreLabel.setForeground(Color.white);
		yourScoreLabel.setBounds(80, 430, 500, 50);
		add(yourScoreLabel);
	}
	public void createHighestScoreLabel() {
		highestScoreLabel = new JLabel();
		highestScoreLabel.setText("Highest Score: " + String.format("%09d", this.highestScore));
		highestScoreLabel.setFont(new Font("Dialog", 1, 40));
		highestScoreLabel.setForeground(Color.white);
		highestScoreLabel.setBounds(40, 490, 500, 50);
		add(highestScoreLabel);
	}
	public void createMoneyLabel() {
		moneyLabel = new JLabel();
		moneyLabel.setText("+ $" + this.money);
		moneyLabel.setFont(new Font("Dialog", 1, 40));
		moneyLabel.setForeground(Color.white);
		moneyLabel.setBounds(250, 550, 500, 50);
		add(moneyLabel);
	}
	public void createBtn() {
		exitBtn = new JButton("返回");
		exitBtn.setBounds(240, 650, 136, 39);
		class ExitBtnListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				//返回主畫面
			}
		}
		ActionListener exitBtnListener = new ExitBtnListener();
		exitBtn.addActionListener(exitBtnListener);
		add(exitBtn);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(gameover, 0, 0, null);
	}
}