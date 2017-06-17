package com.test1;

/*
 * Ticket Window Example
 */

public class Demo2 {

	public static void main(String[] args) {
		
		TicketWindow tw = new TicketWindow();
        Thread t1 = new Thread(tw,"window 1");   
        Thread t2 = new Thread(tw,"window 2");   
        Thread t3 = new Thread(tw,"window 3");
        t1.start(); 
        t2.start(); 
        t3.start(); 
	}
}

class TicketWindow implements Runnable{
    
	private int piao = 100;    
    public void run(){
    	while(this.piao > 0){
            this.fun();
            try{
                Thread.sleep(1000);
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
    }
    public synchronized void fun(){
        if(this.piao > 0)
            System.out.println(Thread.currentThread().getName() + " has: " + (this.piao--));
    }
}