package Sorting;

class QuickSortDemo{
	private int[] data;
	private int num;
	
	public void sort(int[] values){
		if(values==null|| values.length==0){
			return;
		}
		this.data=values;
		this.num=values.length;
		quickSort(0,num-1);
		for(int i=0;i<data.length;i++) System.out.print(data[i]+" ");
	}
	public void quickSort(int low,int high){
		int i=low,j=high;
		int pivot=data[(low+(high-low)/2)];
		while(i<=j){
			while(data[i]<pivot) i++;
			while(data[j]>pivot) j--;
			
			if(i<=j){
				exchange(i,j);
				i++; j--;
			}
			if(low<j)
				quickSort(low,j);
			if(i<high)
				quickSort(i,high);
		}
	}
	public void exchange(int i,int j){
		int tmp=data[i];
		data[i]=data[j];
		data[j]=tmp;
	}
}
public class QuickSort {
	public static void main(String[] args){
		int[] data={12,33,4,11,8,34,20,16,10,5,3,1};
		QuickSortDemo qs=new QuickSortDemo();
		qs.sort(data);
		//qs.
	}
}
