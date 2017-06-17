package com.roy;

public class Test1 {
	
	public static void main(String args[]){
		System.out.println("hello world");
	}	
}


interface Fish{
	public void swimming();
}
interface Bird{
	public void fly();
}

class Monkey{
	
	String name;
	
	public void jump() {
		System.out.println("monkey jump jump");
	}
}
class LittleMonkey extends Monkey implements Fish,Bird{

	@Override
	public void swimming() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fly() {
		// TODO Auto-generated method stub
		
	}
}