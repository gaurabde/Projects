/*
 * @Author: Gaurab Dey
 * @Version: 3.2
 * @Date: 10/25/2013
 * @Class: FHeapTreeMethods
 * @Description: FHeapTreeMethods uses Fibonacci Heap get the MST from the given connected graph
 * @Input Support: support both random graphs as well as structured file input from user 
 */
package project1.code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/*
 * HeapNode: Basic Node structure for F-Heap with details regarding Edges and Vertices:
 * 				FromNode, ToNode, EdgeWeight, Parent Node,
 * 					Minimum Node
 */
class HeapNode{
	HeapNode parentNode=null;
	boolean root=false;
	int data=-99, from=-99, to=-99,degree=0;
	
	int noOfChild=0;
	HeapNode child;
	HeapNode loopNode;
	HeapNode nextNode;
	HeapNode prevNode;
	HeapNode(){
		child=null;
		loopNode=null;
		nextNode=null;
		prevNode=null;
	}
	HeapNode(int val, int f, int t){
		this.data=val;
		this.from=f;
		this.to=t;
		loopNode=null;
		root=true;
	}
	
	//Method for updating (adding) the child in HeapNode
	public boolean updateChilds(HeapNode data){
		if(child!=null){
			HeapNode temp=child;
			if(temp.loopNode==child){
				data.parentNode=this;
				child.loopNode=data;
				child.prevNode=data;
				child.loopNode.loopNode=child;
				child.loopNode.prevNode=child;
				this.degree++;
				noOfChild++;
			}else{
				data.loopNode=child.loopNode;
				data.prevNode=child;
				child.loopNode.prevNode=data;
				child.loopNode=data;
				this.degree++;
				noOfChild++;
			}
		}else{
			this.child=data;
			this.child.loopNode=data;
			this.child.prevNode=data;
			this.child.parentNode=this;
			this.degree++;
			noOfChild++;
		}
		
		return true;
	}
	
	//Method for update the loop of the HeapNode
	public boolean updateLoopNode(HeapNode data){
		loopNode=data;
		return true;
	}
	
	//Method for updating the Degree of HeapNode
	public boolean updateDegree(HeapNode current){
		while(current.parentNode!=null){
			current.degree++;
			current=current.parentNode;
		}
		current.degree++;
		return true;
	}
	
	//Method to print data of HeapNode at any stage of the Flow
	public void printData(){
		System.out.println(" ( "+this.data+","+this.from+","+this.to+","+this.degree+")");
	}
}

/*
 * FHeapTreeMethods: Main method for create, initiate and manage the F-Heap
 * 
 */
public class FHeapTreeMethods{
	public HeapNode minHeap=new HeapNode();
	public HeapNode startNode=new HeapNode();
	public HeapNode lastNode=new HeapNode();
	public int noEdges;
	public int noVerts;
	HashMap<String, Integer> treeData=new HashMap<String, Integer>();
	HashMap<String, Integer> verV=new HashMap<String, Integer>();
	int[][] edgeList;
	
	//Default constructor
	public FHeapTreeMethods() {
		minHeap=null;
		startNode=null;
	}
	
	//Method to start the F-Heap with the given Matrix and other details
	public boolean startHeap(int[][] matrix, int noV,int noE) throws IOException{
		//creating class readable form of matrix from given matrix
		edgeList=readHeapData(matrix,noV,noE);
		Random rand=new Random();
		//Print HeapData
		/*
		for(int i=0;i<noVerts;i++){
			for(int j=0;j<noVerts;j++){
				System.out.print("--"+edgeList[i][j]);
			}
			System.out.println(" ");
		}*/
		//call to insert first section of edges in F-Heap for starting the methods (getMin and meld)
		insertFHeapFromMatrix(rand.nextInt(noV-1)+1, edgeList);
		int totalCost=0;
		int vertVisted=1;
		HeapNode temp=getMin();
		String path="";
		
		//Loop to check the status of visit to all nodes
		while(vertVisted<noVerts){
			//condition to check the path is already visited or not
			if(treeData.containsKey(temp.from+"-"+temp.to)||treeData.containsKey(treeData.get(temp.to+"-"+temp.from))){
				//System.out.println("Already Visited: "+temp.from+"-->"+temp.to);
				temp=getMin();
			}else{
				//condition to check the node pairs are already visited or not
				if(treeData.containsKey(Integer.toString(temp.from))&&treeData.containsKey(Integer.toString(temp.to))){
					//System.out.println("Loop Nodes"+temp.from+"-->"+temp.to +" count: "+vertVisted);
					temp=getMin();
				}else{
					//Adding key values in HashMap to check the status
					treeData.put(temp.from+"-"+temp.to, temp.data);	
					treeData.put(Integer.toString(temp.from), 0);
					treeData.put(Integer.toString(temp.to), 0);
					totalCost=totalCost+temp.data;
					vertVisted++;
					System.out.println("Path: "+temp.from+"->"+temp.to+" data: "+temp.data+" no. of vertVisted: "+vertVisted+" cost: "+totalCost);
				
					path=path+"--"+temp.from+"->"+temp.to;
					insertFHeapFromMatrix(temp.to, edgeList);
					printHeap("Heap Print");
					if(vertVisted==noVerts){
						break;
					}else{
						temp=getMin();
					}
					
				}
				
			}
		}
		//Final output
		//Final output
		System.out.println("\n===========================\nF-HEAP Schema output");
		System.out.println("===========================\nTotalCost: "+totalCost);
		System.out.println("===========================\nPath(Below): \n---------------");
		String[] p=path.split("--");
		for(int i=0;i<p.length;i++){
			System.out.println(p[i]);
		}
		System.out.println("");
		return true;
	}
	
	//Method to start the F-Heap with the given file location containing structured vertices and edge data
	public boolean startHeap(String filePath) throws IOException{
		//call to extract data in class readable formate
		edgeList=readHeapData(filePath);
		Random rand=new Random();
		//Print HeapData
		/*
		for(int i=1;i<=noVerts;i++){
			for(int j=1;j<=noVerts;j++){
				System.out.print("--"+edgeList[i][j]);
			}
			System.out.println(" ");
		}*/
		//System.out.println(edgeList[1]);
		int r=rand.nextInt(noVerts);
		insertFHeap(r, edgeList[r]);
		int totalCost=0;
		int vertVisted=1;
		HeapNode temp=getMin();
		String path="";
		int loopcounter=15;
		String prevS="";
		while(vertVisted<noVerts){
			//condition to check the path is already visited or not
			if(treeData.containsKey(temp.from+"-"+temp.to)||treeData.containsKey(treeData.get(temp.to+"-"+temp.from))){
				//System.out.println("Already Visited: "+temp.from+"-"+temp.to);
				temp=getMin();
			}else{
				//condition to check the node pairs are already visited or not
				
				if(treeData.containsKey(Integer.toString(temp.from))&&treeData.containsKey(Integer.toString(temp.to))){
					//System.out.println("Loop return");
					
					temp=getMin();
				}else{
					//Adding key values in HashMap to check the status
					treeData.put(temp.from+"-"+temp.to, temp.data);	
					treeData.put(Integer.toString(temp.from), 0);
					treeData.put(Integer.toString(temp.to), 0);
					totalCost=totalCost+temp.data;
					vertVisted++;
					System.out.println("Path: "+temp.from+"->"+temp.to+" data: "+temp.data+" no. of vertVisted: "+vertVisted+" cost: "+totalCost);
					
					path=path+"--"+temp.from+"->"+temp.to;
					insertFHeap(temp.to, edgeList[temp.to]);
					printHeap("Heap Print");
					temp=getMin();

				}
				
			}
		}
		//printHeap("Final Heap");
		//Final output
		System.out.println("\n===========================\nF-HEAP Schema output");
		System.out.println("===========================\nTotalCost: "+totalCost);
		System.out.println("===========================\nPath(Below): \n---------------");
		String[] p=path.split("--");
		for(int i=0;i<p.length;i++){
			System.out.println(p[i]);
		}
		System.out.println("");
		return true;
	}
	
	//Method to check the node is visited or not
	//or to avoid any loop in the connection
	public boolean checkVertVisit(int i,int j){
		if(verV.containsKey(i+"-"+j)||verV.containsKey(j+"-"+i)){
			System.out.println("Exist in verV"+i+"-"+j);		
			return true;
		}else{
			return false;
		}
		
	}
	
	//Method to read the edge data from input file
	public int[][] readHeapData(String filePath) throws IOException{
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
		
		
		for(int jj=0;jj<noVerts+1;jj++){
			Arrays.fill(vertEdge[jj],-99);
			System.out.println("VertEdge Size: "+vertEdge.length+" -"+jj);
		}
		int vert1,vert2;
		
		for(i=0;i<noEdges;i++){
			stringSplit=input.readLine().split(" ");
			vert1=Integer.parseInt(stringSplit[0]);
			vert2=Integer.parseInt(stringSplit[1]);
			vertEdge[vert1][vert2]=Integer.parseInt(stringSplit[2]);
			vertEdge[vert2][vert1]=Integer.parseInt(stringSplit[2]);
			key=vert1+"-"+vert2;

		}
		edgeList=vertEdge;
		input.close();
		return vertEdge;
	}
	
	//Method to read edge data from the input matrix and convert to class readable formate
	public int[][] readHeapData(int[][] matrix, int v,int e) {
		int i=0,j=0;
		
		noVerts=v;
		noEdges=e;
		int[][] vertEdge=new int[noVerts+1][noVerts+1];
		String key;
		
		System.out.println("No. of Vertices: "+noVerts+"\n No. Edges: "+noEdges);
		System.out.println("Reading Edges from Random Matrix ");
		
		for(i=0;i<noVerts;i++){
			for(j=0;j<noVerts;j++){
				if(matrix[i][j]!=-99){
					int data=matrix[i][j];
					vertEdge[i][j]=data;
					vertEdge[j][i]=data;
					key=i+"-"+j;
					//treeData.put(key, data);
					//System.out.println("Added to the graph edge list: "+key+"\t Weight:"+data);
				}else{
					vertEdge[i][j]=-99;
					vertEdge[j][i]=-99;
				}
			}
		}
		edgeList=vertEdge;
		return matrix;
	}
	
	//Method inserting the random and visiting node edge weights in the F-Heap for getting the MST
	public void insertFHeap(int randNode,int[] data){
		int val=0;
		for(int i=0;i<data.length;i++){
			val=data[i];
			//System.out.println("=="+val);
			//check if the Node is already visited or not
			if(!checkVertVisit(i, randNode)){
				//check for Edge weight
				if(val!=-99){
					String key=randNode+"-"+i;
					//add key to the Hash for check later
					verV.put(key,val);	
					//System.out.println("Added to verV: "+randNode+"-"+i);
					HeapNode temp=new HeapNode(val, randNode, i);
					//System.out.println("Creating Node: "+temp.data+" "+temp.from+"->"+temp.to);
					
					//condition to check the location of the insert data depending on weight
					//tracking the Min value also
					if(startNode==null){
						temp.loopNode=temp;
						startNode=temp;
						startNode.loopNode=temp;
						startNode.prevNode=temp;
						minHeap=temp;
						lastNode=temp;
						//System.out.println("StartNode :"+temp.data);
					}else{
						if(startNode.loopNode==lastNode){
							if(minHeap.data>val){
								minHeap=temp;
								temp.prevNode=lastNode;
								lastNode.loopNode=temp;
								temp.loopNode=startNode;
								lastNode=temp;
								startNode.prevNode=temp;
							}else{
								temp.prevNode=lastNode;
								lastNode.loopNode=temp;
								temp.loopNode=startNode;
								lastNode=temp;
								startNode.prevNode=temp;
							}
						}else{
							if(minHeap.data>val){
								minHeap=temp;
								temp.prevNode=lastNode;
								lastNode.loopNode=temp;
								temp.loopNode=startNode;
								lastNode=temp;
								startNode.prevNode=temp;
							}else{
								temp.prevNode=lastNode;
								lastNode.loopNode=temp;
								temp.loopNode=startNode;
								lastNode=temp;
								startNode.prevNode=temp;
							}
						}
					}
				}
			}else{
				System.out.println("Path already added:"+randNode+"-"+i);
			}
			
		}
		//printHeap("After Insert");
		//return heapData;
	}
	//Method inserting the random and visiting node edge weights in the F-Heap for getting the MST
	public void insertFHeapFromMatrix(int randNode,int[][] data){
		int val=0,val2=0;
		int ii=0;
		for(ii=0;ii<data.length;ii++){
			val=data[randNode][ii];
			val2=data[ii][randNode];
				
			if(val==-99){
				if(val2!=-99){
 					val=val2;
				}
			}
	//insert nodes of random selection to the F-Heap
			if(!checkVertVisit(ii, randNode)){
				if(val!=-99){
					String key=randNode+"-"+ii;
					verV.put(key,val);	
					System.out.println("Added to verV: "+randNode+"-"+ii);
					HeapNode temp=new HeapNode(val, randNode, ii);
					System.out.println("Creating Node"+temp.data);
					if(startNode==null){
						temp.loopNode=temp;
						startNode=temp;
						startNode.loopNode=temp;
						startNode.prevNode=temp;
						minHeap=temp;
						lastNode=temp;
						//System.out.println("StartNode :"+temp.data);
					}else{
						if(startNode.loopNode==lastNode){
							if(minHeap.data>val){
								minHeap=temp;
								temp.prevNode=lastNode;
								lastNode.loopNode=temp;
								temp.loopNode=startNode;
								lastNode=temp;
								startNode.prevNode=temp;
							}else{
								temp.prevNode=lastNode;
								lastNode.loopNode=temp;
								temp.loopNode=startNode;
								lastNode=temp;
								startNode.prevNode=temp;
							}
						}else{
							if(minHeap.data>val){
								minHeap=temp;
								temp.prevNode=lastNode;
								lastNode.loopNode=temp;
								temp.loopNode=startNode;
								lastNode=temp;
								startNode.prevNode=temp;
							}else{
								temp.prevNode=lastNode;
								lastNode.loopNode=temp;
								temp.loopNode=startNode;
								lastNode=temp;
								startNode.prevNode=temp;
							}
						}
					}
				}
			}else{
				//System.out.println("Path already added:"+randNode+"-"+ii);
			}
			
		}
		ii=0;
		//printHeap("After insert");
		//findMin();
		//return heapData;
	}
	
	//Method to get the Minimum value from the F-Heap
	public HeapNode getMin(){
		HeapNode returnMin= minHeap;
		//printHeap("Before getMin");
		if(minHeap==null){
			System.out.println("Matrix problem min is NULL");
			return null;
		}
		//condition to check various stages of min before remove form F-Heap
		if(minHeap.degree==0){
			HeapNode tempMin=minHeap;
			if(minHeap==startNode){
				//System.out.println("Min=start Node= startNode");			
				if(minHeap==lastNode){
					lastNode=null;
					startNode=null;
					minHeap=null;
				}else{
					startNode=minHeap.loopNode;
					startNode=minHeap.loopNode;
					lastNode.loopNode=startNode;
					minHeap=null;
				}
			}else{
				if(minHeap==lastNode){
					//System.out.println("Min=last Node= startNode");
					lastNode=minHeap.prevNode;
					lastNode.loopNode=startNode;		
					minHeap=null;
				}else{
					if(minHeap.prevNode==startNode){
						if(minHeap.loopNode==lastNode){
							startNode.nextNode=lastNode;
							lastNode.prevNode=startNode;
							minHeap=null;
						}else{
							startNode.loopNode=minHeap.loopNode;
							minHeap.loopNode.prevNode=startNode;
							minHeap=null;
						}
						
					}else{
						minHeap.prevNode.loopNode=minHeap.loopNode;
						minHeap.loopNode.prevNode=minHeap.prevNode;
						//System.out.println("Min child null: "+minHeap.data);
						minHeap=null;
					}					
				}
			}
			findMin();
			//printHeap("Before Meld:\n======");
			meldFheap();
		}else{
			HeapNode prev=minHeap.prevNode;
			HeapNode next=minHeap.loopNode;
			HeapNode childLoopStart=minHeap.child;
			HeapNode childLoopEnd=minHeap.child.prevNode;
			if(minHeap==startNode){
				if(childLoopStart==childLoopEnd){
					if(minHeap==lastNode){
						childLoopStart.prevNode=childLoopStart;
						childLoopEnd.loopNode=childLoopEnd;
						startNode=childLoopStart;
						lastNode=childLoopStart;
						minHeap=null;
					}else{
						lastNode.loopNode=childLoopStart;
						childLoopStart.prevNode=lastNode;
						childLoopStart.loopNode=startNode.loopNode;
						startNode.loopNode.prevNode=childLoopStart;
						startNode=childLoopStart;
						minHeap=null;
					}
					
				}else{
					if(minHeap==lastNode){
						startNode=childLoopStart;
						lastNode=childLoopEnd;
						minHeap=null;
					}else{
						childLoopStart.prevNode=lastNode;
						lastNode.loopNode=childLoopStart;
						childLoopEnd.loopNode=startNode.loopNode;
						startNode.loopNode.prevNode=childLoopEnd;
						startNode=childLoopStart;
						minHeap=null;
					}
					
				}
			}else{
				if(minHeap==lastNode){
					lastNode.prevNode.loopNode=childLoopStart;
					childLoopStart.prevNode=lastNode.prevNode;
					childLoopEnd.loopNode=startNode;
					lastNode=childLoopEnd;
					startNode.prevNode=childLoopEnd;
					minHeap=null;
				}else{
					if(minHeap.prevNode==startNode){
						if(minHeap.loopNode==lastNode){
							startNode.loopNode=childLoopStart;
							childLoopStart.prevNode=startNode;
							childLoopEnd.loopNode=lastNode;
							lastNode.prevNode=childLoopEnd;
							minHeap=null;									
						}else{
							startNode.loopNode=childLoopStart;
							childLoopStart.prevNode=startNode;
							childLoopEnd.loopNode=next;
							next.prevNode=childLoopEnd;
							minHeap=null;
						}
					}else{
						minHeap.prevNode.loopNode=childLoopStart;
						childLoopStart.prevNode=minHeap.prevNode;
						childLoopEnd.loopNode=next;
						next.prevNode=childLoopEnd;
						minHeap=null;
					}
					
				}
				
			}
			findMin();
			//printHeap("Before Meld:\n======");
			meldFheap();
		}
		return returnMin;
	}
	
	//Method perform the meld operation after Removal of minimum node from F-Heap
	public boolean meldFheap(){
		HashMap<Integer,HeapNode> degreeTable=new HashMap<Integer, HeapNode>();
		HeapNode temp=startNode;
		minHeap=startNode;
		if((startNode==null||lastNode==null)||(startNode==lastNode)){
			printHeap("Empty Heap / only one node");
		}else{
			// degree based search in the F-Heap for pair-wise compare
			boolean firstEntry=false;
			while(temp!=startNode||firstEntry==false){
				firstEntry=true;
				if(degreeTable.containsKey(temp.degree)){
					int tempDeg=temp.degree;
					//System.out.println("Found matching degree: "+temp.degree+"current Node: "+temp.data+" Node data:"+((HeapNode)degreeTable.get(temp.degree)).data);
					HeapNode matchNode=(HeapNode)degreeTable.get(temp.degree);
					removeHeapNodes(matchNode);
					//printHeap("First Remove: \n======");
					//System.out.println("StartNode after 1st removing:"+matchNode.data+" --"+startNode.data);
					
					removeHeapNodes(temp);
					//System.out.println("StartNode after 2nd removing:"+temp.data+" --"+startNode.data);
					//printHeap("SecontRemove: \n=====");
					HeapNode mergeHeap=mergeHeaps(matchNode, temp);		
					//System.out.println("MergeHeap Result degree: "); mergeHeap.printData();
					if(startNode==lastNode && lastNode==temp){
						//System.out.println("Start=Last:next Node after merge: ");temp.printData();
						HeapNode temp2=startNode;
						startNode=mergeHeap;
						mergeHeap.loopNode=temp2;
						mergeHeap.prevNode=temp2.prevNode;
						lastNode=mergeHeap;
						lastNode.loopNode=startNode;
						lastNode.prevNode=mergeHeap;
						temp=mergeHeap;
						firstEntry=false;
						//findMin();
						
					}else{
						//HeapNode temp2=startNode;
						mergeHeap.loopNode=startNode;
						startNode.prevNode=mergeHeap;
						lastNode.loopNode=mergeHeap;
						mergeHeap.prevNode=lastNode;
						startNode=mergeHeap;
						temp=startNode;
						firstEntry=false;
						//System.out.println("next Node after merge: "); temp.loopNode.printData();
						//printHeap("Error");
						//System.out.println("Meld second case: "+lastNode.loopNode.data);
					}
					
					degreeTable=new HashMap<Integer, HeapNode>();
					//findMin();
					//degreeTable.put(mergeHeap.degree, mergeHeap);
					//printHeap("After Meld:\n======");
				}else{
					//System.out.println("Not Found Added in degree Tree:");
					degreeTable.put(temp.degree, temp);
					temp=temp.loopNode;
					findMin();
					//printHeap("After Meld:\n======");
				}
			}
		}
		findMin();
		return true;
	}
	
	//method to merge the nodes based on same degree and add it back to the F-Heap
	public HeapNode mergeHeaps(HeapNode first, HeapNode second){
		//System.out.println("Merge Heap: "+first.data+"--"+second.data);
		HeapNode temp=new HeapNode();
		if(first.data<second.data||first.data==second.data){
			first.updateChilds(second);
			temp=first;
			//System.out.println("First: Meld temp update:"+temp.child.data);
		}else{
			second.updateChilds(first);
			temp=second;
			//System.out.println("Second: Meld temp update:"+temp.child.data);
		}
		return temp;
	}
	
	//Method to find minimum value of current F-Heap after remove and meld operation
	//it compare all root nodes for the minimum value and select the new min
	public boolean findMin(){
		HeapNode temp=startNode;
		//startNode used as reference for current minimum
		minHeap=startNode;
		//printHeap("FindMin starting: ");
		if(startNode==null || lastNode==null){
			//System.out.println("Cannont find min: Empty Heap");
			return false;
		}else{
			temp=temp.loopNode;
			if(temp.data<minHeap.data){
				minHeap=temp;
			}
			while(temp.loopNode!=startNode){
				if(temp.data<minHeap.data){
					minHeap=temp;
					//break;
				}else{
					temp=temp.loopNode;
				}
			}
		}
		//System.out.println("Find Min:"); minHeap.printData();
		return true;
	}
	
	//method remove the give nodes from F-Heap for the meld operation
	public void removeHeapNodes(HeapNode rm){
		HeapNode temp=startNode;
		
			if(rm==startNode){
				if(rm.loopNode==lastNode){
					startNode=lastNode;
					lastNode.prevNode=startNode;
					lastNode.loopNode=startNode;
					//System.out.println("RM=startNode=lastNode "+rm.prevNode.data+"-->"+rm.loopNode.data+" -- "+rm.data);
					//break;
				}else{
					HeapNode temp2=startNode;
					startNode=startNode.loopNode;
					startNode.prevNode=lastNode;
					lastNode.loopNode=startNode;
					//System.out.println("RM=startNode "+rm.prevNode.data+"-->"+rm.loopNode.data+" -- "+rm.data);
					//break;
				}
				
			}else{
				if(rm==lastNode){
					lastNode=lastNode.prevNode;
					lastNode.loopNode=startNode;
					startNode.prevNode=lastNode;
					//System.out.println("RM=lastNode "+rm.prevNode.data+"-->"+rm.loopNode.data+" -- "+rm.data);
					//break;
				}else{
					//if(rm==temp){
						if(rm.prevNode==startNode){
							if(rm.loopNode==lastNode){
								lastNode.prevNode=startNode;
								startNode.loopNode=lastNode;
							//System.out.println("Start-->rm-->last nodes sectoin "+rm.prevNode.data+"-->"+rm.loopNode.data+" -- "+rm.data);
							}else{
								startNode.loopNode=rm.loopNode;
								rm.loopNode.prevNode=startNode;
							//System.out.println("Start-->rm nodes sectoin "+rm.prevNode.data+"-->"+rm.loopNode.data+" -- "+rm.data);
							}
							//break;
						}else{

							rm.prevNode.loopNode=rm.loopNode;
							rm.loopNode.prevNode=rm.prevNode;
							//System.out.println("Removing middle min: "+rm.prevNode.data+"-->"+rm.loopNode.data+" -- "+rm.data);
							//break;
						}
				}
			}
		
	}
	
	//Method used basically for debugging the code getting the status of code at any point.
	public void printHeap(String msg){
		//System.out.println(msg);
		if(startNode==null||lastNode==null){ 
			System.out.println("Empty Heap");
		}else{
			HeapNode temp=startNode;
			//System.out.print("--"+temp.data+"("+temp.from+","+temp.to+","+temp.degree+")");
			temp=temp.loopNode;
			while(temp!=startNode){
				//System.out.print("--"+temp.data+"("+temp.from+","+temp.to+","+temp.degree+")");
				temp=temp.loopNode;
			}
			System.out.println("\nMin Node: "+minHeap.data+" degree: "+minHeap.degree);
			System.out.println("StartNode: "+startNode.data+" LastNode : "+lastNode.data);
		}
		
		
	}
}

