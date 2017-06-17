package com.tank;

// 0. Format code: ctrl + shift + f
// 1. Shot & MyPanel & EnemyTank => Thread


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TankGame extends JFrame implements ActionListener{
	
	MyPanel mp = null;
	
	MyStartPanel msp = null;
	
	// Menu
	JMenuBar jmb = null;
	JMenu jm1 = null;
	JMenuItem jmi1 = null;
	JMenuItem jmi2 = null;
	JMenuItem jmi3 = null;
	JMenuItem jmi4 = null;
	
	public static void main(String args[]) {
		TankGame tg = new TankGame();
	}
	
	public TankGame(){
		
		
		
		msp = new MyStartPanel();
		Thread t = new Thread(msp);
		t.start();
		this.add(msp);
		
		jmb = new JMenuBar();
		jm1 = new JMenu("Game(G)");
		jm1.setMnemonic('G');
		
		jmi1 = new JMenuItem("New Game(N)");
		jmi1.setMnemonic('N');
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newgame");
		
		jmi2 = new JMenuItem("Exit(E)");
		jmi2.setMnemonic('E');
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		
		jmi3 = new JMenuItem("Save exit(S)");
		jmi3.setMnemonic('S');
		jmi3.addActionListener(this);
		jmi3.setActionCommand("saveexit");
		
		jmi4 = new JMenuItem("Resume(R)");
		jmi4.setMnemonic('R');
		jmi4.addActionListener(this);
		jmi4.setActionCommand("resume");
		
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm1.add(jmi3);
		jm1.add(jmi4);
		
		jmb.add(jm1);
		this.setJMenuBar(jmb);
		
		this.setSize(600,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("newgame")){
			
			mp = new MyPanel("newGame");
			Thread t = new Thread(mp);
			t.start();
			this.remove(msp);
			this.add(mp);	
			//register key listener
			this.addKeyListener(mp);
			this.setVisible(true);
		}else if(e.getActionCommand().equals("exit")){
			
			// Save records
			Recorder.keepRecording();
			System.exit(0);
		}else if(e.getActionCommand().equals("saveexit")){
			
			// Save records and enemy tank
			Recorder recorder = new Recorder();
			recorder.setEts(mp.ets);
			
			Recorder.keepRecAndEnemyTank();
			System.exit(0);
		}else if(e.getActionCommand().equals("resume")){
			
			// Resume enemy tank from file
			mp = new MyPanel("resume");
			Thread t = new Thread(mp);
			t.start();
			this.remove(msp);
			this.add(mp);	
			//register key listener
			this.addKeyListener(mp);
			this.setVisible(true);
		}
	}
}


class MyStartPanel extends JPanel implements Runnable{
	
	int times = 0;
	public void paint(Graphics g){
		super.paint(g);
		
		g.fillRect(0, 0, 400, 300);
		
		if(times%2==0){			
			g.setColor(Color.yellow);
			Font myFont = new Font("Arial",Font.BOLD,30);
			g.setFont(myFont);
			g.drawString("stage: 1", 150, 150);
		}
	}

	public void run() {
		while (true) {
			try {
				
				Thread.sleep(500);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			times++;
			this.repaint();
		}
	}
}

class MyPanel extends JPanel implements KeyListener,Runnable{
		
	Hero hero = null;
	int enSize = 3;
	Vector<EnemyTank> ets = new Vector<EnemyTank>();
	Vector<Node> nodes = new Vector<Node>();
	
	Vector<Bomb> bombs = new Vector<Bomb>();
	// Define Bomb images
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	
	public MyPanel(String flag){
		
		// Resume the records
		Recorder.getRecording();
		
		this.hero = new Hero(100, 100);
		
		if(flag.equals("newGame")){
		
			for(int i = 0; i < enSize; i++){
				EnemyTank et = new EnemyTank((i+1) * 50, 0);
				et.setColor(0);
				et.setDirect(2);
				
				et.setEts(ets);
				
				//Start Enemy Tank Thread
				Thread t = new Thread(et);
				t.start();
				
				//Add Bullet to Enemy Tank
				Shot s = new Shot(et.x+10, et.y+30, 2);
				et.ss.add(s);
				Thread t2 = new Thread(s);
				t2.start();
				
				ets.add(et);
			}
		}else {
			Recorder recorder = new Recorder();
			nodes = recorder.getNodesAndEnNums();
			
			for(int i = 0; i < nodes.size(); i++){
				
				Node node = nodes.get(i);
				EnemyTank et = new EnemyTank(node.x, node.y);
				et.setColor(0);
				et.setDirect(node.direct);
				
				et.setEts(ets);
				
				//Start Enemy Tank Thread
				Thread t = new Thread(et);
				t.start();
				
				//Add Bullet to Enemy Tank
				Shot s = new Shot(et.x+10, et.y+30, 2);
				et.ss.add(s);
				Thread t2 = new Thread(s);
				t2.start();
				
				ets.add(et);
			}
		}
		
		// Background audio
		AePlayWave apw = new AePlayWave("d:/Myeclipse/start.wav");
		apw.start();
		
		
		image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
		image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
		image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
	}
	
	public void showInfo(Graphics g){
		this.drawTank(80, 330, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getEnNum()+"", 110, 350);
		this.drawTank(140, 330, g, 0, 1);
		g.setColor(Color.black);
		g.drawString(Recorder.getMyLife()+"", 170, 350);
		
		g.setColor(Color.black);
		Font myFont = new Font("Arial",Font.BOLD,20);
		g.setFont(myFont);
		g.drawString("total points", 420, 30);
		this.drawTank(420, 60, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getAllEnNum()+"", 460, 80);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		// 1. Draw Background Rectangle
		g.fillRect(0, 0, 400, 300);
		
		// 1.1 Draw the game information
		showInfo(g);
		
		// 2. Hero
		if(hero.isLive){
			this.drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
		}
		
		// 2.1 Hero's Vector of Bullets
		for(int i = 0; i < hero.ss.size(); i++){
			
			// 2.1.1 draw the vector of bullets
			Shot myShot = hero.ss.get(i);
			if(myShot != null && myShot.isLive == true){
				g.draw3DRect(myShot.x, myShot.y, 1, 1, false);
			}
			
			// 2.1.2 remove the dead bullet from vector
			if(myShot.isLive == false){
				hero.ss.remove(myShot);
			}
		}
		
		
		// 3. Enemy
		for(int i = 0; i < this.ets.size(); i++){
			
			EnemyTank et = this.ets.get(i);
			if(et.isLive){
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), et.getColor());
				
				// Draw Enemy Bullet
				for (int j = 0; j < et.ss.size(); j++) {
					
					Shot enemyShot = et.ss.get(j);
					if(enemyShot.isLive){
						g.draw3DRect(enemyShot.x, enemyShot.y, 1, 1, false);
					}else{
						et.ss.remove(enemyShot);
					}
				}
			}
		}		
		
		
		// 4. Bombs
		for (int i = 0; i < this.bombs.size(); i++) {
			
			Bomb b = this.bombs.get(i);
			if(b.life > 6){
				g.drawImage(image1, b.x, b.y, 30, 30, this);
			}else if (b.life > 3) {
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			}else {
				g.drawImage(image3, b.x, b.y, 30, 30, this);
			}
			
			b.lifeDown();
			
			if(b.life == 0){
				this.bombs.remove(b);
			}
		}
	}	
	
	public void hitEnemyTank(){
		for (int i = 0; i < hero.ss.size(); i++) {
			
			Shot myShot = hero.ss.get(i);
			
			if(myShot.isLive){
				for (int j = 0; j < ets.size(); j++) {
					
					EnemyTank et = ets.get(j);
					
					if(et.isLive){
						
						if(this.hitTank(myShot, et)){
							
							Recorder.addEnNum();
							Recorder.reduceEnNum();
						}
					}
				}
			}
		}
	}
	
	public void hitMe(){
		
		for (int i = 0; i < ets.size(); i++) {
			
			EnemyTank et = ets.get(i);
			for(int j = 0; j < et.ss.size(); j++){
				
				Shot EnemyShot = et.ss.get(j);
				if (hero.isLive) {
					
					if(this.hitTank(EnemyShot, hero)){
						
					}
				}
			}
		}
	}
	
	public boolean hitTank(Shot s, Tank et){
		
		Boolean b = false;
		// 1. Tank direct
		switch (et.direct) {
		// hit
		// > Bullet dead
		// > Tank dead
		case 0:		
		case 2:
			if(s.x>et.x && s.x<et.x+20 && s.y>et.y && s.y<et.y+30){
				s.isLive = false;
				et.isLive = false;
				
				b = true;
				
				Bomb bomb = new Bomb(et.x, et.y);
				bombs.add(bomb);
			}
			break;
		case 1:	
		case 3:
			if(s.x>et.x && s.x<et.x+30 && s.y>et.y && s.y<et.y+20){
				s.isLive = false;
				et.isLive = false;
				
				b = true;
				
				Bomb bomb = new Bomb(et.x, et.y);
				bombs.add(bomb);
			}
			break;
		default:
			break;
		}
		return b;
	}
	
	
	public void drawTank(int x, int y, Graphics g, int direct, int type){
		
		switch (type) {
		case 0:
			g.setColor(Color.CYAN);
			break;
		case 1:
			g.setColor(Color.YELLOW);
			break;	
		default:
			break;
		}
		
		switch (direct) {
		// Up
		case 0:
			g.fill3DRect(x, y, 5, 30, false);
			
			g.fill3DRect(x+15, y, 5, 30, false);
			
			g.fill3DRect(x+5, y+5, 10, 20, false);
			
			g.fillOval(x+5, y+10, 10, 10);
			
			g.drawLine(x+10, y+5, x+10, y);
			break;
		// Right	
		case 1:
			g.fill3DRect(x, y, 30, 5, false);
			
			g.fill3DRect(x, y+15, 30, 5, false);
			
			g.fill3DRect(x+5, y+5, 20, 10, false);
			
			g.fillOval(x+10, y+5, 10, 10);
			
			g.drawLine(x+15, y+10, x+30, y + 10);
			break;
		// Down	
		case 2:
			g.fill3DRect(x, y, 5, 30, false);
			
			g.fill3DRect(x+15, y, 5, 30, false);
			
			g.fill3DRect(x+5, y+5, 10, 20, false);
			
			g.fillOval(x+5, y+10, 10, 10);
			
			g.drawLine(x+10, y+15, x+10, y+30);
			break;
		// left	
		case 3:
			g.fill3DRect(x, y, 30, 5, false);
					
			g.fill3DRect(x, y+15, 30, 5, false);
						
			g.fill3DRect(x+5, y+5, 20, 10, false);
						
			g.fillOval(x+10, y+5, 10, 10);
						
			g.drawLine(x, y+10, x+15, y + 10);
			break;	
		default:
			break;
		}		
	}

	
	

	// Key listener:
	public void keyTyped(KeyEvent e) {
		
	}
	// w: up
	// s: down
	// a: left
	// d: right
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A){
			
			this.hero.setDirect(3);
			this.hero.moveLeft();
		}else if(e.getKeyCode() == KeyEvent.VK_S){
			
			this.hero.setDirect(2);
			this.hero.moveDown();
		}else if(e.getKeyCode() == KeyEvent.VK_D){
			
			this.hero.setDirect(1);
			this.hero.moveRight();
		}else if(e.getKeyCode() == KeyEvent.VK_W){
			
			this.hero.setDirect(0);
			this.hero.moveUp();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_J){
			
			// maximum bullet: 5
			if(this.hero.ss.size() < 5){
				this.hero.shotEnemy();
			}
		}
		
		this.repaint();
	}

	public void keyReleased(KeyEvent e) {
		
	}
	// End of Key listener

	
	public void run() {
		
		while(true){
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// Watch enemy the bullet hit?
			this.hitEnemyTank();
			
			// Watch hero hit?
			this.hitMe();
			
			this.repaint();
		}
	}
}