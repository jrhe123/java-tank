package com.student;

import javax.swing.*;

import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class Test2 extends JFrame{
	
	Vector rowData, columnNames;
	JTable jTable = null;
	JScrollPane jsp = null;
	
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public static void main(String[] args) {
		Test2 test2 = new Test2();
	}

	public Test2(){
		columnNames = new Vector();
		columnNames.add("stuNo");
		columnNames.add("name");
		columnNames.add("gender");
		columnNames.add("age");
		columnNames.add("country");
		columnNames.add("facility");
		
		rowData=new Vector();
		
		try {
			// 1. Load the driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// 2. Connect
			ct = DriverManager.getConnection(
					"jdbc:sqlserver://127.0.0.1:1433;DatabaseName=test", "sa",
					"123456");
			ps = ct.prepareStatement("select * from stu");
			rs = ps.executeQuery();
			
			while(rs.next()){
				Vector row = new Vector();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getInt(4));
				row.add(rs.getString(5));
				row.add(rs.getString(6));
				rowData.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(ps != null){
					ps.close();
				}
				if(ct != null){
					ct.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
				
		
		jTable = new JTable(rowData, columnNames);
		jsp = new JScrollPane(jTable);
		
		this.add(jsp);
		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
