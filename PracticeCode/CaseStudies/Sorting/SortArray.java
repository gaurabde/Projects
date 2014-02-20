package Sorting;

import java.util.Arrays;

public class SortArray {
	
	int[] sortTwoArray(int[] a,int[] b){
		int[] mer=new int[a.length+b.length];
		int i=a.length-1,j=b.length-1,k=mer.length-1;
		while(i>=0&&j>=0){
			if(a[i]>b[j]){
				mer[k--]=a[i--];
			}else{
				mer[k--]=b[j--];
			}
		}
		while(j>=0){
			mer[k--]=b[j--];
		}
		return mer;
	}
	
	public static void main(String[] args){
		SortArray sa=new SortArray();
		int[] a=sa.sortTwoArray(new int[]{1,3,4,5,9,11,25}, new int[]{0,2,6,7,12,13,14,15,18,20});
		for(int i:a){
			System.out.print(i+"--");
			
		}
		int[] c=new int[]{1,1,2,1,3,2,4,1,6,23,5};
		Arrays.sort(c);
		System.out.println();
		for(int i:c){
			System.out.print(i+"--");
			
		}
	}

}
