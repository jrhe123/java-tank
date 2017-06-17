package com.roy;

import java.util.Calendar;

public class Test4 {
	
	public static void main(String args[]) throws Exception{
		
		int len = 10;
		int arr[] = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * 10000);
		}
		
		Calendar calendar = Calendar.getInstance();
		System.out.println("before: "+calendar.getTime());
		
		
//		SelectionSort ss = new SelectionSort();
//		ss.sort(arr);
		
//		BubbleSort bs = new BubbleSort();
//		bs.sort(arr);
		
//		InsertSort is = new InsertSort();
//		is.sort(arr);
		
		QuickSort qs = new QuickSort();
		qs.sort(0, arr.length-1, arr);
		
		
		
		calendar = Calendar.getInstance();
		System.out.println("after: "+calendar.getTime());
	}	
}


class QuickSort{
	
	public void sort(int left, int right, int arr[]) {
		
		int l = left;
		int r = right;
		int pivot = arr[(left+right)/2];
		int temp = 0;
		
		while(l<r){
			while(arr[l]<pivot) l++;
			while(arr[r]>pivot) r--;
			
			if(l >= r) break;
			
			temp = arr[l];
			arr[l] = arr[r];
			arr[r] = temp;
			
			if(arr[l] == pivot) --r;
			if(arr[r] == pivot) ++l;
		}
		
		if(l == r){
			l++;
			r--;
		}
		if(left < r) sort(left, r, arr);
		if(right > l) sort(l, right, arr);
	}
}

class InsertSort{
	
	public void sort(int arr[]) {
		// Default first number is sorted
		// Start sort from the second number
		for (int i = 1; i < arr.length; i++) {
			int insertVal = arr[i];
			
			int index = i - 1;
			while (index >= 0 && insertVal < arr[index]) {
				arr[index+1] = arr[index];
				index--;
			}
			arr[index+1] = insertVal;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
	
}

class BubbleSort{
	
	public void sort(int arr[]) {
		
		boolean swapped = true;
	    while (swapped) {
	       swapped = false;
	       for(int i=1; i<arr.length; i++) {
	           int temp=0;
	           if(arr[i-1] > arr[i]) {
	                temp = arr[i-1];
	                arr[i-1] = arr[i];
	                arr[i] = temp;
	                swapped = true;
	            }
	        }
	    }
	    for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
}

class SelectionSort{
	
	public void sort(int arr[]) {
		int min;
		int temp;
		for (int i = 0; i < arr.length; i++) {
			min = i;
			for (int j = i+1; j < arr.length; j++) {
				if(arr[j] < arr[min]){
					min = j;
				}
			}
			if(min != i){
				temp = arr[i];
				arr[i] = arr[min];
				arr[min] = temp;
			}
			System.out.println(arr[i]);
		}	
	}
}