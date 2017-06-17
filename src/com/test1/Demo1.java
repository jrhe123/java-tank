package com.test1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

public class Demo1 extends JFrame {

	MyPanel mp = null;
	public static void main(String args[]){
		
		Demo1 demo1 = new Demo1();
	}
	
	public Demo1(){
		mp = new MyPanel();
		
		this.add(mp);
		
		// 1. Mouse listener register
		this.addMouseListener(mp);
		// 2. Key listener register
		this.addKeyListener(mp);
		// 3. Mouse Motion Listener register
		this.addMouseMotionListener(mp);
		// 4. Window listener
		this.addWindowListener(mp);
		
		this.setSize(400,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

// # One class implements multiple listeners
// 1. Mouse listener
// 2. Key listener
// 3. Mouse motion listener
// 4. Window listener
class MyPanel extends JPanel implements MouseListener, KeyListener, MouseMotionListener, WindowListener{
	
	public void paint(Graphics g){
		
		super.paint(g);
	}

	// 1. Mouse Listener
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX() + "," + e.getY());
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
	// End of Mouse Listener
	
	
	// 2. Key Listener
	public void keyTyped(KeyEvent e) {
		System.out.println(e.getKeyChar());
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}
	// End of Key Listener

	
	// 3. Mouse Motion listener
	public void mouseDragged(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {

	}
	// End of Mouse Motion listener

	
	// 4.Window listener
	public void windowOpened(WindowEvent e) {
		
	}

	public void windowClosing(WindowEvent e) {
		
	}

	public void windowClosed(WindowEvent e) {
		
	}

	public void windowIconified(WindowEvent e) {
		
	}

	public void windowDeiconified(WindowEvent e) {
		
	}

	public void windowActivated(WindowEvent e) {
		
	}

	public void windowDeactivated(WindowEvent e) {
		
	}
	// 4.End of window listener
}
