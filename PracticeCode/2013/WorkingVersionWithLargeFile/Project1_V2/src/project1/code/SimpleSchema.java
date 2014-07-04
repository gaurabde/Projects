package project1.code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Thread.State;
import java.math.BigInteger;
import java.security.acl.Permission;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


class SimpleNode{
	int data=0,from=0, to=0;
	SimpleNode nextNode;
	
	SimpleNode(){
		data=0;
		from=0;
		to=0;
		nextNode=null;
	}
	SimpleNode(int d,int f, int t, SimpleNode next){
		data=d;
		from=f;
		this.to=t;
		nextNode=next;
	}
	
}

class PriorityQueue{
	SimpleNode firstNode=null, lastNode=null;
	
	boolean enQueue(SimpleNode node){
		if(firstNode==null){
			firstNode=node;
			lastNode=node;
			lastNode.nextNode=null;
		}else{
			if(firstNode.data>node.data){
				SimpleNode prevNode=firstNode;
				firstNode=node;
				node.nextNode=prevNode;
			}else{
				if(lastNode.data<node.data){
					SimpleNode prevNode=lastNode;
					lastNode=node;
					prevNode.nextNode=node;
				}else{
					SimpleNode nodeMov=firstNode;
					SimpleNode tempNode=new SimpleNode();
					while(nodeMov.nextNode!=null){
						if((nodeMov.data<node.data && nodeMov.nextNode.data >node.data)||(nodeMov.data==node.data)){
							tempNode=nodeMov.nextNode;
							nodeMov.nextNode=node;
							nodeMov.nextNode.nextNode=tempNode;
							//System.out.println("Node: "+node.from+"--"+node.to+" added between:"+nodeMov.data+" -- "+tempNode.data);
							break;
						}else{		
							nodeMov=nodeMov.nextNode;
						}
					}
				}
			}
		}
		return true;
	}
	
	SimpleNode deQueue(){
		if(firstNode==null){
			return null;
		}
		SimpleNode temp=firstNode;
		
		if(firstNode.nextNode==null){
			System.out.println("Queue Empty");
			temp=firstNode;
			firstNode=firstNode.nextNode;
			return temp;
		}else{
			temp=firstNode;
			firstNode=firstNode.nextNode;
			return temp;
		}
	}
	
	void printQueue(){
		SimpleNode temp=firstNode;
		System.out.println("Data in queue: ");
		while(temp!=null){
			System.out.print(temp.from+"-"+temp.to+" data: "+temp.data+"\n");
			temp=temp.nextNode;
		}
	}
}

public class SimpleSchema{
	HashMap<String, Integer> treeData=new HashMap<String, Integer>();
	public int noEdges;
	public int noVerts;
	public SimpleNode minNode=new SimpleNode();
	public SimpleNode maxNode=new SimpleNode();
	
	public boolean startMST(String filePath) throws IOException{
		int[][] edgeList=readTreeData(filePath);
		Random rand=new Random();
		for(int i=1;i<=noVerts;i++){
			for(int j=1;j<=noVerts;j++){
				System.out.print("--"+edgeList[i][j]);
			}
			System.out.println(" ");
		}
		createMST(1, edgeList);
		return true;
	}
	
	public boolean startMST(int[][] matrix, int noVert, int noEdges) throws IOException{
		int[][] edgeList=readTreeData(matrix,noVert, noEdges);
		Random rand=new Random();
		for(int i=0;i<noVerts;i++){
			for(int j=0;j<noVerts;j++){
				System.out.print("--"+edgeList[i][j]);
			}
			System.out.println(" ");
		}
		createMSTFromMatrix(1, edgeList);
		return true;
	}
	public int[][] readTreeData(String filePath) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(filePath));
		int i=0;
		String stringInput = input.readLine();
		String[] stringSplit=stringInput.split(" ");
		noVerts=Integer.parseInt(stringSplit[0]);
		noEdges=Integer.parseInt(stringSplit[1]);
		int[][] vertEdge=new int[noVerts+1][noVerts+1];
		String key;
		
		System.out.println("No. of Vertices: "+noVerts+"\n No. Edges: "+noEdges);
		System.out.println("Reading Edges from file: "+filePath);
		
		
		for(int jj=0;jj<vertEdge.length;jj++){
			Arrays.fill(vertEdge[jj],-99);
			System.out.println("VertEdge Size"+vertEdge.length);
		}
		
		int vert1,vert2;
		
		for(i=0;i<noEdges;i++){
			stringSplit=input.readLine().split(" ");
			vert1=Integer.parseInt(stringSplit[0]);
			vert2=Integer.parseInt(stringSplit[1]);
			vertEdge[vert1][vert2]=Integer.parseInt(stringSplit[2]);
			vertEdge[vert2][vert1]=Integer.parseInt(stringSplit[2]);
			key=vert1+"-"+vert2;
			treeData.put(key, Integer.parseInt(stringSplit[2]));
			System.out.println("Added to the graph edge list: "+key+"\t Weight:"+stringSplit[2]);
		}
		i=0;
		input.close();
		return vertEdge;
	}
	
	public int[][] readTreeData(int[][] matrix, int vert, int edges) throws IOException{
		int i=0,j=0;
		noVerts=vert;
		noEdges=edges;
		int[][] vertEdge=new int[noVerts+1][noVerts+1];
		String key;
		System.out.println("Read Tree from Matrix");
		System.out.println("No. of Vertices: "+noVerts+"\n No. Edges: "+noEdges);
		System.out.println("Reading Edges from Random matrix: ");
		
		int vert1,vert2;
		for(i=0;i<noVerts;i++){
			for(j=0;j<noVerts;j++){
				if(matrix[i][j]!=-99){
					int data=matrix[i][j];
					key=i+" "+j;
					treeData.put(key, data);
					System.out.println(key+" "+data);
				}
			}
		}
		return matrix;
	}
	public void createMST(int randVert,int[][] edgeList){
		HashMap vertVisited=new HashMap();
		HashMap vertChecked=new HashMap();
		SimpleNode startNode=null;
		PriorityQueue pQueue=new PriorityQueue();
		int totalCost=0;
		//initial list from the random Node
		System.out.println("Intial Random Selection Node:"+randVert);
		for(int y=1;y<noVerts+1;y++){
			int data=edgeList[randVert][y];
			if(data!=-99){
				SimpleNode temp=new SimpleNode(data, randVert, y, null);
				pQueue.enQueue(temp);
				//pQueue.printQueue();
			}
		}
		
		//finding the MST
		System.out.println("Nodes in Queue: ");
		String path="";
		startNode=pQueue.deQueue();
		int checkCounter=0;
		while(startNode!=null||checkCounter==noVerts){
			if(vertVisited.containsKey(startNode.from+"-"+startNode.to)||vertVisited.containsKey(startNode.to+"-"+startNode.from)){	
				System.out.println("Path already visited"+startNode.from+"--"+startNode.to+" data:"+startNode.data);
				startNode=pQueue.deQueue();
			}else{
				if(vertChecked.containsKey(startNode.from)&&vertChecked.containsKey(startNode.to)){
					System.out.println("Node already visited"+startNode.from+"--"+startNode.to);
					startNode=pQueue.deQueue();					
				}else{
					int toNode=startNode.to;
					totalCost=totalCost+startNode.data;
					vertVisited.put(startNode.from+"-"+startNode.to, startNode.data);
					vertChecked.put(startNode.to, true);
					vertChecked.put(startNode.from, true);
					checkCounter++;
					System.out.println("Nodes DEQUEUE: "+startNode.from+" -- "+startNode.to+" weight: "+startNode.data+" totalCost: "+totalCost);
					path=path+"--"+startNode.from+"->"+startNode.to;
					for(int y=1;y<noVerts+1;y++){
						int data=edgeList[toNode][y];
						if(data!=-99){
							SimpleNode temp=new SimpleNode(data, toNode, y, null);
							pQueue.enQueue(temp);
							pQueue.printQueue();
						}
					}
					startNode=pQueue.deQueue();
				}
				
			}
		}
		
		System.out.println("\n===========================\nTotalCost: "+totalCost);
		String[] p=path.split("--");
		for(int i=0;i<p.length;i++){
			System.out.println(p[i]);
		}
		System.out.println("");
	}
	public void createMSTFromMatrix(int randVert,int[][] edgeList){
		HashMap vertVisited=new HashMap();
		HashMap vertChecked=new HashMap();
		SimpleNode startNode=null;
		PriorityQueue pQueue=new PriorityQueue();
		int totalCost=0;
		//initial list from the random Node
		System.out.println("Intial Random Selection Node:"+randVert);
		for(int y=0;y<noVerts;y++){
			int data11=edgeList[randVert][y];
			int data12=edgeList[y][randVert];
			if(data11!=-99){
				SimpleNode temp=new SimpleNode(data11, randVert, y, null);
				pQueue.enQueue(temp);
				//pQueue.printQueue();
			}else{
				if(data12!=-99){
					SimpleNode temp=new SimpleNode(data12, randVert, y, null);
					pQueue.enQueue(temp);
					//pQueue.printQueue();
				}
			}
		}
		
		//finding the MST
		System.out.println("Nodes in Queue: ");
		String path="";
		startNode=pQueue.deQueue();
		int checkCounter=0;
		while(startNode!=null||checkCounter==noVerts){
			if(vertVisited.containsKey(startNode.from+"-"+startNode.to)||vertVisited.containsKey(startNode.to+"-"+startNode.from)){	
				//System.out.println("Path already visited"+startNode.from+"--"+startNode.to+" data:"+startNode.data);
				startNode=pQueue.deQueue();
			}else{
				if(vertChecked.containsKey(startNode.to)){
					//System.out.println("Node already visited"+startNode.from+"--"+startNode.to);
					startNode=pQueue.deQueue();					
				}else{
					int toNode=startNode.to;
					totalCost=totalCost+startNode.data;
					vertVisited.put(startNode.from+"-"+startNode.to, startNode.data);
					vertChecked.put(startNode.to, true);
					vertChecked.put(startNode.from, true);
					checkCounter++;
					//System.out.println("Nodes DEQUEUE: "+startNode.from+" -- "+startNode.to+" weight: "+startNode.data+" totalCost: "+totalCost);
					path=path+"--"+startNode.from+"->"+startNode.to;
					for(int y2=0;y2<noVerts;y2++){
						int data21=edgeList[toNode][y2];
						int data22=edgeList[y2][toNode];
						if(data21!=-99){
							SimpleNode temp=new SimpleNode(data21, toNode, y2, null);
							pQueue.enQueue(temp);
							//pQueue.printQueue();
						}else{
							if(data22!=-99){
								SimpleNode temp=new SimpleNode(data22, toNode, y2, null);
								pQueue.enQueue(temp);
								//pQueue.printQueue();
							}
						}
					}
					startNode=pQueue.deQueue();
				}
				
			}
		}
		
		System.out.println("\n===========================\nTotalCost: "+totalCost);
		String[] p=path.split("--");
		for(int i=0;i<p.length;i++){
			System.out.println(p[i]);
		}
		System.out.println("");
	}
	
	
}
