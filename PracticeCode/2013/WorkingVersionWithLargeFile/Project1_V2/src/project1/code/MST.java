package project1.code;

import java.io.IOException;
import java.util.Random;

import project1.*;

public class MST {
	public static void main(String args[]) throws IOException{
		String[] inputData={"-r", "100", "60",""};//args;
		String type, filepath;
		int noVert, noEdges, density;
		Random rm=new Random();
		
		if(inputData.length==3){
			type=inputData[0];
			noVert= Integer.parseInt(inputData[1]);
			density=Integer.parseInt(inputData[2]);
			
			
			SimpleSchema sm=new SimpleSchema();
			FHeapTreeMethods fHeap=new FHeapTreeMethods();
			RandomAdjMatrix randM=new RandomAdjMatrix();
			randM.startRandomGen(noVert, density);
			
			fHeap.startHeap(randM.returnMatrix(), noVert,(int)randM.returnNoEdge());
			//fHeap.startHeap("/home/gaurabde/Documents/WorkSpace/Project1_V2/data/inputlarge.txt");
			//sm.startMST(randM.returnMatrix(),noVert, (int)randM.returnNoEdge());
			//sm.startMST("/home/gaurabde/Documents/WorkSpace/Project1_V2/data/inputlarge.txt");
			//int[][] test=randM.returnMatrix();
			//System.out.println(test.length);
		}else{
			if(inputData.length==2){
				
			}else{
				System.out.println("Please provide correct command line argument (format as below)\n=========================");
				System.out.println("1. For Random (will randomly select F-Heap or Simple-Scheme):  ");
				System.out.println(" \tjava MST -r <n: noOfVertices> <d: %density> ");
				System.out.println("\n\"Depending on no. of Vertics and Density the Random Graph generation will take little time\" \n=========================");
				System.out.println("2.\tFor User Input Mode \n\ta. Simple-Scheme : java MST -s <file: fullFilePath>");
				System.out.println("\tb. F-Heap Schema : java MST -f <file: fullFilePath>");
			}
		}
	}

}
