package com.roy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Test6 extends JFrame implements ActionListener{
	
	MyPanel mp = null;
	JButton jb1 = null;
	JButton jb2 = null;
	
	public static void main(String args[]) {
		Test6 test6 = new Test6();
	}
	
	public Test6(){
		mp = new MyPanel();
		jb1 = new JButton("black");
		jb2 = new JButton("red");
		
//		this.add(jb1,BorderLayout.NORTH);
		this.add(mp);
//		this.add(jb2,BorderLayout.SOUTH);
		
		mp.setBackground(Color.black);
		
		// 1. key press action
		this.addKeyListener(mp);
		// 2. btn action
//		jb1.addActionListener(this);
//		jb1.setActionCommand("black");
//		jb2.addActionListener(this);
//		jb2.setActionCommand("red");
		
		
		this.setSize(400,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("black")){
			mp.setBackground(Color.black);
		}else if(e.getActionCommand().equals("red")){
			mp.setBackground(Color.red);
		}
	}
}

class MyPanel extends JPanel implements KeyListener{

	int x = 10;
	int y = 10;
	
	public void paint(Graphics g){
		super.paint(g);
		
		g.fillOval(x, y, 10, 10);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			y++;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			x--;
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			x++;
		}else if(e.getKeyCode() == KeyEvent.VK_UP){
			y--;
		}
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}