package com.roy;

public class Test5 {
	
	public static void main(String args[]) throws Exception{
		
		int arr[] = {1,2,3,4,5,6,7,8,9,10};
		
		BinaryFind bf = new BinaryFind();
		bf.find(0, arr.length, 10, arr);
	}	
}

class BinaryFind{
	
	public void find(int leftIndex, int rightIndex, int val, int arr[]) {
		
		int midIndex = (leftIndex + rightIndex)/2;
		int midVal = arr[midIndex];
		
		if(rightIndex >= leftIndex){
			if(midVal > val){
				find(leftIndex, midIndex-1, val, arr);
			}else if(midVal < val){
				find(midIndex+1, rightIndex, val, arr);
			}else if(midVal == val){
				System.out.println("found, index: "+midIndex);
			}
		}		
	}
}