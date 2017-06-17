package com.roy;

public class Test2 {
	
	private int money = 0;
	
	public void sellCar(Car car) {
		System.out.println(car.getName() + ": " + car.getPrice());
		this.money += car.getPrice();
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public static void main(String args[]){
		
		Test2 t = new Test2();
		t.sellCar(new BMW());
		t.sellCar(new QQ());
		
		System.out.println("total money: "+t.getMoney());
	}	
}

interface Car{
	int getPrice();
	String getName();
}

class BMW implements Car{

	@Override
	public int getPrice() {
		return 123456789;
	}

	@Override
	public String getName() {
		return "i am bmw";
	}
}

class QQ implements Car{

	@Override
	public int getPrice() {
		return 99;
	}

	@Override
	public String getName() {
		return "i am qq";
	}
}