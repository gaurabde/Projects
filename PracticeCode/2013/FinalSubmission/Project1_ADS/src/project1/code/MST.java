/*
 * @Author: Gaurab Dey
 * @Version: 3.2
 * @Date: 10/25/2013
 * @Class: MST
 * @Description: Main Class for getting user inputs for running different versions of schemes (Simple/F-Heap)
 */

package project1.code;

import java.io.IOException;
import java.util.Random;

import project1.code.RandomMatrixGenerator;
import project1.code.SimpleSchema;
import project1.code.FHeapTreeMethods;

public class MST {
	public static void main(String args[]) throws IOException{
				
		String[] inputData=args;
		String type, filepath;
		int noVert, noEdges, density;
		Random rm=new Random();
		long startTime=0,stopTime=0,totalTime=0;
		
		if(inputData.length==3){
			type=inputData[0];
			noVert= Integer.parseInt(inputData[1]);
			density=Integer.parseInt(inputData[2]);
			RandomMatrixGenerator rmg=new RandomMatrixGenerator();
			rmg.startRandGen(noVert, density);
			SimpleSchema ss=new SimpleSchema();
			FHeapTreeMethods fheap=new FHeapTreeMethods();
			noEdges=(int)rmg.getNoEdge();
			
			startTime=System.currentTimeMillis();
			//ss.startMST(rmg.getMatrix(), noVert, noEdges);
			
			fheap.startHeap(rmg.getMatrix(), noVert, noEdges);
			stopTime=System.currentTimeMillis();
			
			System.out.println("Total Time taken for Random: "+(stopTime-startTime));
		}else{
			if(inputData.length==2){
				type=inputData[0];
				filepath=inputData[1];
				
				if(type=="-s"){
					SimpleSchema ss=new SimpleSchema();
					startTime=System.currentTimeMillis();
					
					ss.startMST(filepath);
					
					stopTime=System.currentTimeMillis();
					
					System.out.println("Total Time taken for Simple-Scheme (file Input): "+(startTime-startTime));
					
				}else{
					if(type=="-f"){
						FHeapTreeMethods fheap=new FHeapTreeMethods();
						startTime=System.currentTimeMillis();
						
						fheap.startHeap(filepath);
						
						stopTime=System.currentTimeMillis();
						
						System.out.println("Total Time taken for F-Heap (file Input): "+(startTime-startTime));
						
					}else{
						printInstruction();
					}			
				}
			}else{
				noVert= Integer.parseInt(inputData[1]);
				density=Integer.parseInt(inputData[2]);
				RandomMatrixGenerator rmg=new RandomMatrixGenerator(true);
				rmg.startRandGen(noVert, density);
				printInstruction();
			}
		}
	}
	
	public static void printInstruction(){
		System.out.println("Please provide correct command line argument (format as below)\n=========================");
		System.out.println("1. For Random (will randomly select F-Heap or Simple-Scheme):  ");
		System.out.println(" \tjava MST -r <n: noOfVertices> <d: %density> ");
		System.out.println("\n\"Depending on no. of Vertics and Density the Random Graph generation will take little time\" \n=========================");
		System.out.println("2.\tFor User Input Mode \n\ta. Simple-Scheme : java MST -s <file: fullFilePath>");
		System.out.println("\tb. F-Heap Schema : java MST -f <file: fullFilePath>");
	}

}
