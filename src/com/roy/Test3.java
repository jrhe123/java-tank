package com.roy;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test3 {
	
	public static void main(String args[]) throws Exception{
		
		Dog dogs[] = new Dog[4];
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		for (int i = 0; i < dogs.length; i++) {
			dogs[i] = new Dog();
			
			System.out.println("please input dog's name: ");
			String name = br.readLine();
			
			System.out.println("please input dog's wight: ");
			String weight = br.readLine();
			float heavy = Float.parseFloat(weight);
			
			dogs[i].setNameString(name);
			dogs[i].setWeight(heavy);
		}
		float total = 0;
		for (int i = 0; i < dogs.length; i++) {
			total += dogs[i].getWeight();
		}
		
		float avgWeight = total / dogs.length;
		System.out.println("avg: "+avgWeight);
		
	}	
}

class Dog{
	private String nameString;
	private float weight;
		
	public String getNameString() {
		return nameString;
	}
	public void setNameString(String nameString) {
		this.nameString = nameString;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
}