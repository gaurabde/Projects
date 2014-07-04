/**
 * @author: Gaurab Dey
 * @UFID: 78029140
 * @Class: DFSoriginal.java
 * @Description: DFSorignal check the input matrix is connected or not and return TRUE if connected and FLASE if not
 *
 */

package project1.code;



import java.io.*;
import java.util.*;


public class DFSoriginal{
	Stack<Integer> st;
	int vFirst;
	int[][] adjMatrix;
	int[] isVisited;
	int counter=1;
	public DFSoriginal(){
		
	}	
	
	//Constructor for initialization
	public DFSoriginal(int[][] Matrix, int noVert){
		System.out.println("Readched DFS");
		this.adjMatrix = Matrix;
		this.isVisited=new int[noVert];
		st = new Stack<Integer>();
		int i;
		
	} 
	//Main DFS function
	public boolean depthFirst(int vFirst,int n){
		int v,i;
		st.push(vFirst);
		boolean flag=false;
		while(!st.isEmpty()){
			v = st.pop();
							
			if(isVisited[v]==0){
				System.out.print("\n"+(v+1));
				isVisited[v]=1;
			}		
			for ( i=0;i<n;i++){
				if((adjMatrix[v][i] != -99) && (isVisited[i] == 0)){
						st.push(v);
						isVisited[i]=1;
						System.out.print(" " + (i+1));
						v = i;
						counter++;
				}
			}
		}
		if (counter==n){
			System.out.println("Graph connected \nCounter: "+counter);
			flag=true;
			return flag;
		}
		else{
			System.out.println("Graph not connected \nCounter: "+counter);
			return flag;
		}
				
	}//end of depthFirst
}//end of class		