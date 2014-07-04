package project1.code;

import java.io.IOException;
import java.util.Random;

import project1.*;

public class MST {
	public static void main(String args[]) throws IOException{
		String[] inputData={"-r", "10", "10"};//args;
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
			//sm.startMST(randM.returnMatrix(),noVert, (int)randM.returnNoEdge());
		}else{
			
		}
	}

}
