package com.tank;

import java.util.Vector;
import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

class AePlayWave extends Thread{
	
	private String filename;
	public AePlayWave(String wavefile){
		filename = wavefile;
	}
	
	public void run(){
		
		File soundFile = new File(filename);
		AudioInputStream audioInputStream = null;
		
		try {
			
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auLine = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		
		try {
			
			auLine = (SourceDataLine) AudioSystem.getLine(info);
			auLine.open(format);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} 
		
		auLine.start();
		int nBytesRead = 0;
		byte[] abData = new byte[1024];
		
		try {
			
			while(nBytesRead != -1){
				
				nBytesRead = audioInputStream.read(abData,0,abData.length);
				if(nBytesRead >= 0){
					
					auLine.write(abData, 0, nBytesRead);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally{
			
			auLine.drain();
			auLine.close();
		}
		
	}
}


class Node{
	
	int x;
	int y;
	int direct;
	
	public Node(int x, int y, int direct){
		
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
}

class Recorder{
	
	private static int enNum = 20;
	private static int myLife = 3;
	private static int allEnNum = 0;
	
	// Save
	private static FileWriter fw = null;
	private static BufferedWriter bw = null;
	
	private static FileReader fr = null;
	private static BufferedReader br = null;
	
	private static Vector<EnemyTank> ets = new Vector<EnemyTank>();
	
	// Resume
	private Vector<Node> nodes = new Vector<Node>();
	
	
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}	
	public static void reduceEnNum(){
		enNum--;
	}
	public static void addEnNum() {
		allEnNum++;
	}
	public static int getAllEnNum() {
		return allEnNum;
	}
	public static void setAllEnNum(int allEnNum) {
		Recorder.allEnNum = allEnNum;
	}
	// Save to file
	public static void keepRecording(){
		
		try {
			
			fw = new FileWriter("D:/Myeclipse/myRecording.txt");
			bw = new BufferedWriter(fw);
			bw.write(allEnNum+"\r\n");			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			
			try {
				
				bw.close();
				fw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	// Read from file
	public static void getRecording(){
		
		try {
			
			fr = new FileReader("D:/Myeclipse/myRecording.txt");
			br = new BufferedReader(fr);
			
			String n = br.readLine();
			allEnNum = Integer.parseInt(n);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			
			try {
				
				br.close();
				fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	// Save enemy tank position and records to file
	public Vector<EnemyTank> getEts() {
		return ets;
	}
	public void setEts(Vector<EnemyTank> ets) {
		this.ets = ets;
	}
	public static void keepRecAndEnemyTank() {

		try {

			fw = new FileWriter("D:/Myeclipse/myRecording.txt");
			bw = new BufferedWriter(fw);
			bw.write(allEnNum + "\r\n");
			
			// Save Enemy Tanks number and position
			for(int i = 0; i < ets.size(); i++){
				
				EnemyTank et = ets.get(i);
				if(et.isLive){
					
					String record = et.x + " " + et.y + " " + et.direct;
					bw.write(record+"\r\n");
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {

				bw.close();
				fw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	// Resume Enemy Tank
	public Vector<Node> getNodesAndEnNums(){
		
		try {
			
			fr = new FileReader("D:/Myeclipse/myRecording.txt");
			br = new BufferedReader(fr);
			
			String n = br.readLine();
			allEnNum = Integer.parseInt(n);
			
			while ( (n = br.readLine()) != null) {
				
				String []xyz = n.split(" ");
				Node node = new Node(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1]),Integer.parseInt(xyz[2]));
				this.nodes.add(node);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			
			try {
				
				br.close();
				fr.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		return this.nodes;
	}
	
}

class Bomb{
	
	int x;
	int y;
	int life = 9;
	boolean isLive = true;
	
	public Bomb(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void lifeDown(){
		if(life > 0){
			life--;
		}else{
			this.isLive = false;
		}
	}
}


class Shot implements Runnable{
	
	int x;
	int y;
	int direct;
	int speed = 1;
	boolean isLive = true;
	
	public Shot(int x, int y, int direct){
		this.x = x;
		this.y = y;
		this.direct = direct;
	}

	public void run() {
		
		while(true){
			
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			switch (direct) {
			case 0:
				y-=speed;
				break;
			case 1:
				x+=speed;
				break;
			case 2:
				y+=speed;
				break;
			case 3:
				x-=speed;
				break;
			default:
				break;
			}
			
			//System.out.println("Bullet position " + x + "," + y);
			
			if(x < 0 || x > 400 || y < 0 || y > 300){
				this.isLive = false;
				break;
			}
		}
	}
}


class Tank{
	
	// 1. tank position
	int x = 0;	
	int y = 0;
	// 2. tank direction
	// "0" : up
	// "1" : right
	// "2" : down
	// "3" : left
	int direct = 0;
	
	int color = 0;
	
	int speed = 1;
	
	boolean isLive = true;
	
	public Tank(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
}


class EnemyTank extends Tank implements Runnable{
	
	int times = 0;
	Vector<Shot> ss = new Vector<Shot>();
	
	public EnemyTank(int x, int y){
		super(x,y);
	}

	
	Vector<EnemyTank> ets = new Vector<EnemyTank>();
	public void setEts(Vector<EnemyTank> vv){
		this.ets = vv;
	}
	
	public boolean isTouchOtherEnemy(){
		
		boolean b = false;
		
		switch (this.direct) {
		// up
		case 0:
			
			for(int i = 0; i < ets.size(); i++){
				
				EnemyTank et = ets.get(i);
				if(et != this){
					
					// up || down
					if(et.direct == 0 || et.direct == 2){
						
						// upper left
						if(this.x >= et.x && this.x <= et.x+20 && this.y >= et.y && this.y <= et.y+30){
							return true;
						}
						// upper right
						if(this.x+20 >= et.x && this.x+20 <= et.x+20 && this.y >= et.y && this.y <= et.y+30){
							return true;
						}
					}
					// left || right
					if(et.direct == 1 || et.direct == 3){
						
						// upper left
						if(this.x >= et.x && this.x <= et.x+30 && this.y >= et.y && this.y <= et.y+20){
							return true;
						}
						// upper right
						if(this.x+20 >= et.x && this.x+20 <= et.x+30 && this.y >= et.y && this.y <= et.y+20){
							return true;
						}
					}
				}
			}
			
			break;
		// right	
		case 1:
					
			for(int i = 0; i < ets.size(); i++){
				
				EnemyTank et = ets.get(i);
				if(et != this){
					
					// up || down
					if(et.direct == 0 || et.direct == 2){
						
						// upper right
						if(this.x+30 >= et.x && this.x+30 <= et.x+20 && this.y >= et.y && this.y <= et.y+30){
							return true;
						}
						// bottom right
						if(this.x+30 >= et.x && this.x+30 <= et.x+20 && this.y+20 >= et.y && this.y+20 <= et.y+30){
							return true;
						}
					}
					// left || right
					if(et.direct == 1 || et.direct == 3){
						
						// upper right
						if(this.x+30 >= et.x && this.x+30 <= et.x+30 && this.y >= et.y && this.y <= et.y+20){
							return true;
						}
						// bottom right
						if(this.x+30 >= et.x && this.x+30 <= et.x+30 && this.y+20 >= et.y && this.y+20 <= et.y+20){
							return true;
						}
					}
				}
			}
			break;
		// down	
		case 2:
			
			for(int i = 0; i < ets.size(); i++){
				
				EnemyTank et = ets.get(i);
				if(et != this){
					
					// up || down
					if(et.direct == 0 || et.direct == 2){
						
						// bottom left
						if(this.x >= et.x && this.x <= et.x+20 && this.y+30 >= et.y && this.y+30 <= et.y+30){
							return true;
						}
						// bottom right
						if(this.x+20 >= et.x && this.x+20 <= et.x+20 && this.y+30 >= et.y && this.y+30 <= et.y+30){
							return true;
						}
					}
					// left || right
					if(et.direct == 1 || et.direct == 3){
						
						// bottom left
						if(this.x >= et.x && this.x <= et.x+30 && this.y+30 >= et.y && this.y+30 <= et.y+20){
							return true;
						}
						// bottom right
						if(this.x+20 >= et.x && this.x+20 <= et.x+30 && this.y+30 >= et.y && this.y+30 <= et.y+20){
							return true;
						}
					}
				}
			}
			break;
		// left	
		case 3:
			
			for(int i = 0; i < ets.size(); i++){
				
				EnemyTank et = ets.get(i);
				if(et != this){
					
					// up || down
					if(et.direct == 0 || et.direct == 2){
						
						// upper left
						if(this.x >= et.x && this.x <= et.x+20 && this.y >= et.y && this.y <= et.y+30){
							return true;
						}
						// bottom left
						if(this.x >= et.x && this.x <= et.x+20 && this.y+20 >= et.y && this.y+20 <= et.y+30){
							return true;
						}
					}
					// left || right
					if(et.direct == 1 || et.direct == 3){
						
						// upper left
						if(this.x >= et.x && this.x <= et.x+30 && this.y >= et.y && this.y <= et.y+20){
							return true;
						}
						// bottom left
						if(this.x >= et.x && this.x <= et.x+30 && this.y+20 >= et.y && this.y <= et.y+20){
							return true;
						}
					}
				}
			}
			break;
		default:
			break;
		}
		
		return b;
	}
	
	

	public void run() {

		while (true) {			
			
			switch (this.direct) {
			case 0:
				for (int i = 0; i < 30; i++) {
					if(y>0 && !this.isTouchOtherEnemy()){
						y -= speed;
					}
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case 1:
				for (int i = 0; i < 30; i++) {
					if(x < 400 && !this.isTouchOtherEnemy()){
						x += speed;
					}
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case 2:
				for (int i = 0; i < 30; i++) {
					if(y<300 && !this.isTouchOtherEnemy()){
						y += speed;
					}
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			case 3:
				for (int i = 0; i < 30; i++) {
					if(x>0 && !this.isTouchOtherEnemy()){
						x -= speed;
					}
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			default:
				break;
			}
			
			
			this.times++;
			if(times%2 == 0){
				if(isLive){
					
					if(ss.size() < 5){
						
						Shot s = null;
						switch (direct) {
						case 0:
							s = new Shot(x+10, y, 0);
							ss.add(s);
							break;
						case 1:
							s = new Shot(x+30, y+10, 1);
							ss.add(s);
							break;
						case 2:
							s = new Shot(x+10, y+30, 2);
							ss.add(s);
							break;
						case 3:
							s = new Shot(x, y+10, 3);
							ss.add(s);
							break;
						default:
							break;
						}
						Thread t = new Thread(s);
						t.start();
					}
				}
			}
			
			// New direct of Enemy
			this.direct = (int)(Math.random()*4);			
			
			if(this.isLive == false){
				break;
			}
			
		}
	}
}


class Hero extends Tank{
	
	// Vector of Bullets
	Vector<Shot> ss = new Vector<Shot>();
	Shot s = null;
	
	public Hero(int x, int y){
		super(x, y);		
	}
	
	public void shotEnemy() {
		
		switch (this.direct) {
		case 0:
			s = new Shot(x + 10, y, 0);
			ss.add(s);
			break;
		case 1:
			s = new Shot(x + 30, y + 10, 1);
			ss.add(s);
			break;
		case 2:
			s = new Shot(x + 10, y + 30, 2);
			ss.add(s);
			break;
		case 3:
			s = new Shot(x, y + 10, 3);
			ss.add(s);
			break;
		default:
			break;
		}
		Thread t = new Thread(s);
		t.start();
	}
	
	public  void moveUp() {
		y -= speed;
	}
	public void moveRight(){
		x += speed;
	}
	public  void moveDown() {
		y += speed;
	}
	public  void moveLeft() {
		x -= speed;
	}
}