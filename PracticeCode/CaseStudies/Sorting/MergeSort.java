package Sorting;

class MergeSortDemo{
	private int[] data;
	private int[] help;
	private int num;
	
	public void sort(int[] values){
		data=values;
		num=values.length;
		help=new int[num];
		mergeSort(0,num-1);
		for(int i=0;i<data.length;i++) System.out.print(data[i]+"--");

	}
	public void mergeSort(int low,int high){
		if(low<high){
			int mid=(low+high)/2;
			mergeSort(low,mid);
			mergeSort(mid+1,high);
			merge(low,mid,high);
		}
		for(int i=0;i<data.length;i++) System.out.print(data[i]+"--");
		System.out.println();

	}
	public void merge(int low,int mid,int high){
		for(int i=low;i<=high;i++)help[i]=data[i];
		int i=low,j=mid+1,k=low;
		while(i<=mid&&j<=high){
			if(help[i]<help[j]){
				data[k]=help[i];
				i++;
			}else{
				data[k]=help[j];
				j++;
			}
			k++;
		}
		while(i<=mid){
			data[k]=help[i];
			k++;
			i++;
		}
	}
}
public class MergeSort {
	public static void main(String[]args){
		int[] data={12,33,4,11,8,34,20,16,10bubble,5,3,1};
		MergeSortDemo ms=new MergeSortDemo();
		ms.sort(data);
		
	}
}
