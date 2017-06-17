package com.student;

import javax.swing.*;
import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class Test1 extends JFrame{

	Vector rowData, columnNames;
	JTable jTable = null;
	JScrollPane jsp = null;
	
	public static void main(String[] args) {
		
		Test1 test1 = new Test1();
	}
	
	public Test1(){
		columnNames = new Vector();
		columnNames.add("stuNo");
		columnNames.add("name");
		columnNames.add("gender");
		columnNames.add("age");
		columnNames.add("country");
		columnNames.add("facility");
		
		rowData=new Vector();
		
		Vector row = new Vector();
		row.add("sp001");
		row.add("roy");
		row.add("male");
		row.add("500");
		row.add("china");
		row.add("computer");
		
		rowData.add(row);
		
		jTable = new JTable(rowData, columnNames);
		
		jsp = new JScrollPane(jTable);
		
		this.add(jsp);
		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
