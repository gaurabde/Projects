package project1.code;

import java.text.DateFormatSymbols;
import java.util.*;
import java.io.*;
import java.math.*;

public class RandomAdjMatrix {
	
	public int [][] randMetrix;
	public double density;
	public void startRandomGen(int vert, int den){
		System.out.println("Starting Random Edge creation:");
		int adjmat[][]= new int[vert][vert];
		double counter=0;
		double c=den;
		density=(int) Math.ceil((c/100)*vert*vert);;
		System.out.println("Density cal:"+density);
		
		for(int i=0;i<vert;i++){
			for(int j=0;j<vert;j++){
				adjmat[i][j]=-99;	
			}
		}
		Random generator = new Random();
		boolean dfsTrue=false;
		while(!dfsTrue){
			counter=0;
			for(int i=0;i<vert;i++){
				for(int j=0;j<vert;j++){
					adjmat[i][j]=-99;	
				}
			}
			System.out.println("in while loop:"+c);
			
			while(counter!=density){
				int r = generator.nextInt(vert);
		        int s = generator.nextInt(vert);
		        int t = generator.nextInt(1000)+1;
		        if (adjmat[r][s]== -99)
		        	{
			        	adjmat[r][s]=t;
			        	counter++;
		        	}
		    	System.out.println("2nd while loop:"+counter);
			}
			
		//point to call DFS
			DFSoriginal dfsnew = new DFSoriginal(adjmat,vert);
			if(dfsnew.depthFirst(0, vert)){
				System.out.println("DFS");
				System.out.println("-----------------------------------");
				System.out.println("        For DENSITY = " + c+"%"+" counter: "+counter);
				System.out.println("-----------------------------------");
				for(int m=0;m<vert;m++){
					for(int n=0;n<vert;n++){
						System.out.print(adjmat[m][n]+ "  ");
					}
					System.out.println();
				}
				dfsTrue=true;
				randMetrix=adjmat;
			}else{
				System.out.println("NOT DFS:"+density+"%");
				//c=c-10;
			}
		//dfsnew.setMatrixNodes(adjmat[10][10],10);*/
		}			
	}
	
	public int[][] returnMatrix(){
		return randMetrix;
	}
	public double returnNoEdge(){
		return density;
	}
}

