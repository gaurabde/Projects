package project1.code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import javax.swing.text.AsyncBoxView.ChildState;


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
	public boolean updateChilds(HeapNode data){
		if(child!=null){
			HeapNode temp=child;
			if(temp.loopNode!=child){
				data.parentNode=this;
				child.loopNode=data;
				child.prevNode=data;
				data.loopNode=child;
				data.prevNode=child;
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
	public boolean updateLoopNode(HeapNode data){
		loopNode=data;
		return true;
	}
	public boolean updateDegree(HeapNode current){
		while(current.parentNode!=null){
			current.degree++;
			current=current.parentNode;
		}
		current.degree++;
		return true;
	}
	
	public void printData(){
		//System.out.println(" ( "+this.data+","+this.from+","+this.to+","+this.degree+")");
	}
}

public class FHeapTreeMethods{
	public HeapNode minHeap=new HeapNode();
	public HeapNode startNode=new HeapNode();
	public HeapNode lastNode=new HeapNode();
	public int noEdges;
	public int noVerts;
	HashMap<String, Integer> treeData=new HashMap<String, Integer>();
	HashMap<String, Integer> verV=new HashMap<String, Integer>();
	int[][] edgeList;
	
	public FHeapTreeMethods() {
		minHeap=null;
		startNode=null;
	}
	public boolean startHeap(int[][] matrix, int noV,int noE) throws IOException{
		edgeList=readHeapData(matrix,noV,noE);
		Random rand=new Random();
		//Print HeapData
		for(int i=0;i<noVerts;i++){
			for(int j=0;j<noVerts;j++){
				System.out.print("--"+edgeList[i][j]);
			}
			System.out.println(" ");
		}
		//System.out.println(edgeList[1]);
		insertFHeapFromMatrix(1, edgeList);
		int totalCost=0;
		int vertVisted=1;
		HeapNode temp=getMin();
		String path="";
		while(vertVisted<noVerts){
			if(treeData.containsKey(temp.from+"-"+temp.to)||treeData.containsKey(treeData.get(temp.to+"-"+temp.from))){
				System.out.println("Already Visited: "+temp.from+"-"+temp.to);
				temp=getMin();
			}else{
				if(treeData.containsKey(Integer.toString(temp.from))&&treeData.containsKey(Integer.toString(temp.to))){
					System.out.println("Loop return");
					temp=getMin();
				}else{
					treeData.put(temp.from+"-"+temp.to, temp.data);	
					treeData.put(Integer.toString(temp.from), 0);
					treeData.put(Integer.toString(temp.to), 0);
					totalCost=totalCost+temp.data;
					vertVisted++;
					System.out.println("Path: "+temp.from+"->"+temp.to+" data: "+temp.data+" no. of vertVisted: "+vertVisted+" cost: "+totalCost);
				
					path=path+"--"+temp.from+"->"+temp.to;
					insertFHeapFromMatrix(temp.to, edgeList);
					//printHeap("Heap Print");
					if(vertVisted==noVerts){
						break;
					}else{
						temp=getMin();
					}
					
				}
				
			}
		}
		printHeap("Final Heap");
		String[] p=path.split("--");
		System.out.println(totalCost);
		for(int i=0;i<p.length;i++){
			System.out.println(p[i]);
		}
		System.out.println("");
		return true;
	}
	public boolean startHeap(String filePath) throws IOException{
		edgeList=readHeapData(filePath);
		Random rand=new Random();
		//Print HeapData
		for(int i=1;i<=noVerts;i++){
			for(int j=1;j<=noVerts;j++){
				System.out.print("--"+edgeList[i][j]);
			}
			System.out.println(" ");
		}
		//System.out.println(edgeList[1]);
		insertFHeap(1, edgeList[1]);
		int totalCost=0;
		int vertVisted=1;
		HeapNode temp=getMin();
		String path="";
		while(vertVisted<noVerts-1){
			if(treeData.containsKey(temp.from+"-"+temp.to)||treeData.containsKey(treeData.get(temp.to+"-"+temp.from))){
				System.out.println("Already Visited: "+temp.from+"-"+temp.to);
				temp=getMin();
			}else{
				if(treeData.containsKey(Integer.toString(temp.from))&&treeData.containsKey(Integer.toString(temp.to))){
					System.out.println("Loop return");
					temp=getMin();
				}else{
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
		String[] p=path.split("--");
		System.out.println(totalCost);
		for(int i=0;i<p.length;i++){
			System.out.println(p[i]);
		}
		System.out.println("");
		return true;
	}
	
	public boolean checkVertVisit(int i,int j){
		if(verV.containsKey(i+"-"+j)||verV.containsKey(j+"-"+i)){
			System.out.println("Exist in verV"+i+"-"+j);
			
			return true;
		}else{
			return false;
		}
		
	}
	
	public int[][] readHeapData(String filePath) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(filePath));
		int i=0;
		String stringInput = input.readLine();
		String[] stringSplit=stringInput.split(" ");
		noVerts=Integer.parseInt(stringSplit[0]);
		noEdges=Integer.parseInt(stringSplit[1]);
		int[][] vertEdge=new int[noVerts+1][noVerts+1];
		String key;
		edgeList=vertEdge;
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
			//treeData.put(key, Integer.parseInt(stringSplit[2]));
			//System.out.println("Added to the graph edge list: "+key+"\t Weight:"+stringSplit[2]);
		}
		input.close();
		return vertEdge;
	}
	
	public int[][] readHeapData(int[][] matrix, int v,int e) {
		int i=0,j=0;
		
		noVerts=v;
		noEdges=e;
		int[][] vertEdge=matrix;
		String key;
		edgeList=vertEdge;
		System.out.println("No. of Vertices: "+noVerts+"\n No. Edges: "+noEdges);
		System.out.println("Reading Edges from Random Matrix ");
		
		for(i=0;i<noVerts;i++){
			for(j=0;j<noVerts;j++){
				if(matrix[i][j]!=-99){
					int data=matrix[i][j];
					key=i+"-"+j;
					//treeData.put(key, data);
					//System.out.println("Added to the graph edge list: "+key+"\t Weight:"+data);
				}
			}
		}
		return matrix;
	}
	public void insertFHeap(int randNode,int[] data){
		int val=0;
		for(int i=1;i<data.length;i++){
			val=data[i];
			//System.out.println("=="+val);
			if(!checkVertVisit(i, randNode)){
				if(val!=-99){
					String key=randNode+"-"+i;
					verV.put(key,val);	
					//System.out.println("Added to verV: "+randNode+"-"+i);
					HeapNode temp=new HeapNode(val, randNode, i);
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
				System.out.println("Path already added:"+randNode+"-"+i);
			}
			
		}
		
		//return heapData;
	}
	
	public void insertFHeapFromMatrix(int randNode,int[][] data){
		int val=0,val2=0;
		int ii=0;
		for(ii=0;ii<data.length;ii++){
			val=data[randNode][ii];
			val2=data[ii][randNode];
			//System.out.println("Val/val2: "+val+"/"+val2);		
			
			if(val==-99){
				if(val2!=-99){
 					val=val2;
				}
			}
			//System.out.println("=="+val);
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
				System.out.println("Path already added:"+randNode+"-"+ii);
			}
			
		}
		ii=0;
		findMin();
		//return heapData;
	}
		
	public HeapNode getMin(){
		HeapNode returnMin= minHeap;
		printHeap("Before getMin");
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
	
	public boolean meldFheap(){
		HashMap<Integer,HeapNode> degreeTable=new HashMap<Integer, HeapNode>();
		HeapNode temp=startNode;
		minHeap=startNode;
		if((startNode==null||lastNode==null)||(startNode==lastNode)){
			printHeap("Empty Heap / only one node");
		}else{
			//degreeTable.put(temp.degree, temp);	
			//System.out.println("Starting Meld: "+temp.data+" degree: "+temp.degree);
			//temp=temp.loopNode;
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
						findMin();
						
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
					findMin();
					//degreeTable.put(mergeHeap.degree, mergeHeap);
					printHeap("After Meld:\n======");
				}else{
					//System.out.println("Not Found Added in degree Tree:");
					degreeTable.put(temp.degree, temp);
					temp=temp.loopNode;
					findMin();
					printHeap("After Meld:\n======");
				}
			}
		}
		return true;
	}
	
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
	
	public boolean findMin(){
		HeapNode temp=startNode;
		minHeap=startNode;
		if(startNode==null || lastNode==null){
			System.out.println("Cannont find min: Empty Heap");
			return false;
		}else{
			temp=temp.loopNode;
			if(temp.data<minHeap.data){
				minHeap=temp;
			}
			while(temp.loopNode!=startNode){
				if(temp.data<minHeap.data){
					minHeap=temp;
					break;
				}else{
					temp=temp.loopNode;
				}
			}
		}
		//System.out.println("Find Min:"); minHeap.printData();
		return true;
	}
	
	public void removeHeapNodes(HeapNode rm){
		HeapNode temp=startNode;
		//System.out.println("In remove node: ");rm.printData();
		//System.out.println(" \nStartNode: ");startNode.printData();
		//System.out.println("LastNode: ");lastNode.printData();
		//while(temp.loopNode!=startNode){
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
							//	System.out.println("Start-->rm-->last nodes sectoin "+rm.prevNode.data+"-->"+rm.loopNode.data+" -- "+rm.data);
							}else{
								startNode.loopNode=rm.loopNode;
								rm.loopNode.prevNode=startNode;
							//	System.out.println("Start-->rm nodes sectoin "+rm.prevNode.data+"-->"+rm.loopNode.data+" -- "+rm.data);
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
		//}
		//System.out.println("After Removal: \n======");
		//System.out.println("remove node: "+rm.data +" StartNode: ");startNode.printData();
		//System.out.println("LastNode: ");lastNode.printData();
		//printHeap("===");
	}
	
	public void printHeap(String msg){
		System.out.println(msg);
		if(startNode==null||lastNode==null){
			System.out.println("Empty Heap");
		}else{
			HeapNode temp=startNode;
			//System.out.print("--"+temp.data+"("+temp.from+","+temp.to+","+temp.degree+")");
			temp=temp.loopNode;
			while(temp!=startNode){
				System.out.print("--"+temp.data+"("+temp.from+","+temp.to+","+temp.degree+")");
				temp=temp.loopNode;
			}
			System.out.println("\nMin Node: "+minHeap.data+" degree: "+minHeap.degree);
			System.out.println("StartNode: "+startNode.data+" LastNode : "+lastNode.data);
		}
		
		
	}
}

