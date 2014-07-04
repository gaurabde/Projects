/*
 * @Author: Gaurab Dey
 * @Version: 3.2
 * @Date: 10/25/2013
 * @Class: RandomMatrixGenerator
 * @Description: Used to generate random connected graph(matrix) depending on density
 * 				 graph connected or not checked using DFS 
 */

package project1.code;

import java.util.*;


public class RandomMatrixGenerator {
	
	public int [][] randMetrix;
	public double density;
	public boolean printOnly=false;
	/* Start Method for Random Generation:
	 * vert: no. of vertices
	 * den: density for the random connected graph
	 */
	
	RandomMatrixGenerator(){
		
	}
	
	RandomMatrixGenerator(boolean printD){
		printOnly=printD;
	}
	
	public void startRandGen(int vert, int den){
		int adjMatrix[][]= new int[vert][vert];
		double counter=0;
		double c=den;
		
		//density calculation
		density=(int) Math.ceil((c/200)*vert*vert);;
		//System.out.println("Density cal:"+density);
		//initialization of the adjacent matrix to default value: -99
		for(int i=0;i<vert;i++){
			for(int j=0;j<vert;j++){
				adjMatrix[i][j]=-99;	
			}
		}
		Random generator = new Random();
		boolean dfsTrue=false;
		//loop will terminate as soon as the random connected graph is generated
		// most time consuming
		while(!dfsTrue){
			counter=0;
			for(int i=0;i<vert;i++){
				for(int j=0;j<vert;j++){
					adjMatrix[i][j]=-99;	
				}
			}
			//checking density in the 
			while(counter!=density){
				//random generation: vertices and edge weights
				int r = generator.nextInt(vert);
		        int s = generator.nextInt(vert);
		        int t = generator.nextInt(1000)+1;
		        if (adjMatrix[r][s]== -99)
		        	{
			        	adjMatrix[r][s]=t;
			        	counter++;
		        	}
			}
					
		//DFS call for checking the random generated graph is connected or not.
			DFSChecker dfsnew = new DFSChecker(adjMatrix,vert);
			if(dfsnew.depthFirstCheck(0, vert)){
				//System.out.println("DFS True: Random CONNECTED graph created");
				dfsTrue=true;
				randMetrix=adjMatrix;
				//to print the connected graph
				if(printOnly){
					System.out.println(vert+" "+(int)density);
					for(int i=0;i<vert;i++){
						for(int j=0;j<vert;j++){
							if(adjMatrix[i][j]!=-99)
							System.out.println(i+" "+j+" "+adjMatrix[i][j]);
						}
					}
				}
			}
		}			
	}
	
	public int[][] getMatrix(){
		return randMetrix;
	}
	public double getNoEdge(){
		return density;
	}
}

/*
 * DFSChecker: class which check the input graph is connected or not by DFS
 * DFSChecker(int[][] matrix, int noVert): constructor
 * matrix: input matrix
 * noVert: is not of vertices for the matrix
 */
class DFSChecker{
	Stack<Integer> stackData;
	int[] isVisited;
	int counter=1;
	int vFirst;
	int[][] adjMatrix;
	
	public DFSChecker(){
		
	}	
	
	//Constructor for initialization
	public DFSChecker(int[][] matrix, int noVert){
		System.out.println("Readched DFS");
		this.adjMatrix = matrix;
		this.isVisited=new int[noVert];
		stackData = new Stack<Integer>();
		int i;
		
	} 
	/*
	 * Main DFS method for checking the graph
	 * vFirst: initial position in the graph
	 * novert: no. of vertices
	 */
	public boolean depthFirstCheck(int vFirst,int noVert){
		int v,i;
		stackData.push(vFirst);
	
		while(!stackData.isEmpty()){
			v = stackData.pop();
							
			if(isVisited[v]==0){
				System.out.print("\n"+(v+1));
				isVisited[v]=1;
			}		
			for ( i=0;i<noVert;i++){
				if((adjMatrix[v][i] != -99) && (isVisited[i] == 0)){
						stackData.push(v);
						isVisited[i]=1;
						//System.out.print(" " + (i+1));
						v = i;
						counter++;
				}
			}
		}
		if (counter==noVert){
			System.out.println("Random-Graph connected \nCounter: "+counter);
			return true;
		}
		else{
			System.out.println("Random-Graph not connected \nCounter: "+counter);
			return false;
		}
				
	}//end of depthFirst
}//end of class		

