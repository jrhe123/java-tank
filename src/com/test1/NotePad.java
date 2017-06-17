package com.test1;
/*
 * NotePad example:
 * 
 * 1. open file
 * 2. save file
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class NotePad extends JFrame implements ActionListener{

	// 1. Text area
	JTextArea jta = null;
	// 2. Menu
	JMenuBar jmb = null;
	JMenu jm1 = null;
	JMenuItem jmi1 = null;
	JMenuItem jmi2 = null;
	
	public static void main(String[] args) {
		
		NotePad nPad = new NotePad();
	}

	public NotePad(){
		// 1.
		jta = new JTextArea();
		// 2.
		jmb = new JMenuBar();
		jm1 = new JMenu("File");
		jm1.setMnemonic('F');
		jmi1 = new JMenuItem("open");
		jmi1.addActionListener(this);
		jmi1.setActionCommand("open");
		
		jmi2 = new JMenuItem("save");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("save");
		
		this.setJMenuBar(jmb);
		jmb.add(jm1);
		jm1.add(jmi1);
		jm1.add(jmi2);
		
		
		this.add(jta);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("open")){
			
			JFileChooser jfc1 = new JFileChooser();
			jfc1.setDialogTitle("please choose a file..");
			jfc1.showOpenDialog(null);
			jfc1.setVisible(true);
			
			String fileName = jfc1.getSelectedFile().getAbsolutePath();
			FileReader fr = null;
			BufferedReader br = null;
			try {
				
				fr = new FileReader(fileName);
				br = new BufferedReader(fr);
				String s = "";
				String allCon = "";
				while((s = br.readLine()) != null){
					allCon += s + "\r\n";
				}
				jta.setText(allCon);
				
			} catch (Exception e2) {
				e2.printStackTrace();
			} finally{
				
				try {
					
					br.close();
					fr.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		}else if(e.getActionCommand().equals("save")){
			
			JFileChooser jfc2 = new JFileChooser();
			jfc2.setDialogTitle("save as..");
			jfc2.showSaveDialog(null);
			jfc2.setVisible(true);
			
			String fileName = jfc2.getSelectedFile().getAbsolutePath();
			FileWriter fw = null;
			BufferedWriter bw = null;
			
			try {
				
				fw = new FileWriter(fileName);
				bw = new BufferedWriter(fw);
				
				bw.write(this.jta.getText());
				
			} catch (Exception e2) {
				e2.printStackTrace();
			} finally{
				try {
					bw.close();
					fw.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		}
	}
}
