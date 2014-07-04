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
				data.loopNode=child.loopNode;
				data.loopNode.prevNode=data;
				data.prevNode=child;
				this.degree++;
				noOfChild++;
			}else{
				data.loopNode=child;
				data.prevNode=child;
				child.loopNode=data;
				child.prevNode=data;
				this.degree++;
				noOfChild++;
			}
		}else{
			child=data;
			child.loopNode=data;
			child.prevNode=data;
			child.parentNode=this;
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
		System.out.println(" ( "+this.data+","+this.from+","+this.to+","+this.degree+")");
	}
}

class FHeapTreeMethods{
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
					System.out.println("Path: "+temp.from+"->"+temp.to+" data: "+temp.data+" no. of vertVisted: "+vertVisted+" cost: "+totalCost);
					vertVisted++;
					path=path+"--"+temp.from+"->"+temp.to;
					insertFHeap(temp.to, edgeList[temp.to]);
					printHeap("Heap Print");
					temp=getMin();
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
			System.out.println("Added to the graph edge list: "+key+"\t Weight:"+stringSplit[2]);
		}
		input.close();
		return vertEdge;
	}
	
	public void insertFHeap(int randNode,int[] data){
		int val=0;
		for(int i=1;i<data.length;i++){
			val=data[i];
			System.out.println("=="+val);
			if(!checkVertVisit(i, randNode)){
				if(val!=-99){
					String key=randNode+"-"+i;
					verV.put(key,val);	
					System.out.println("Added to verV: "+randNode+"-"+i);
					HeapNode temp=new HeapNode(val, randNode, i);
					System.out.println("Creating Node"+temp.data);
					if(startNode==null){
						temp.loopNode=temp;
						startNode=temp;
						startNode.loopNode=temp;
						startNode.prevNode=temp;
						minHeap=temp;
						lastNode=temp;
						System.out.println("StartNode :"+temp.data);
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
		
	public HeapNode getMin(){
		HeapNode returnMin= minHeap;
		printHeap("Before getMin");
		if(minHeap.degree==0){
			HeapNode tempMin=minHeap;
			if(minHeap==startNode){
				System.out.println("Min=start Node= startNode");
				startNode=minHeap.loopNode;
				startNode=minHeap.loopNode;
				if(minHeap==lastNode){
					lastNode=startNode;
				}else{
					lastNode.loopNode=startNode;
				}
			}else{
				if(minHeap==lastNode){
					System.out.println("Min=last Node= startNode");
					lastNode=minHeap.prevNode;
					lastNode.loopNode=startNode;					
				}else{
					minHeap.prevNode.loopNode=minHeap.loopNode;
					minHeap.loopNode.prevNode=tempMin.prevNode;
					System.out.println("Min child null: "+minHeap.data);
				}
			}
			printHeap("Before Meld:\n======");
			meldFheap();
			printHeap("After Meld:\n======");
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
					}else{
						lastNode.loopNode=childLoopStart;
						childLoopStart.prevNode=lastNode;
						childLoopStart.loopNode=startNode.loopNode;
						startNode.loopNode.prevNode=childLoopStart;
						startNode=childLoopStart;
					}
					
				}else{
					if(minHeap==lastNode){
						startNode=childLoopStart;
						lastNode=childLoopEnd;
					}else{
						childLoopStart.prevNode=lastNode;
						lastNode.loopNode=childLoopStart;
						childLoopEnd.loopNode=startNode.loopNode;
						startNode.loopNode.prevNode=childLoopEnd;
						startNode=childLoopStart;
					}
					
				}
			}else{
				if(minHeap==lastNode){
					lastNode.prevNode.loopNode=childLoopStart;
					childLoopStart.prevNode=lastNode.prevNode;
					childLoopEnd.loopNode=startNode;
					startNode.prevNode=childLoopEnd;
				}else{
					prev.loopNode=childLoopStart;
					childLoopEnd.prevNode=prev;
					childLoopEnd.loopNode=next;
					next.prevNode=childLoopEnd;
				}
				
			}
			printHeap("Before Meld:\n======");
			meldFheap();
			printHeap("After Meld:\n======");
		}
		return returnMin;
	}
	
	public boolean meldFheap(){
		HashMap<Integer,HeapNode> degreeTable=new HashMap<Integer, HeapNode>();
		HeapNode temp=startNode;
		minHeap=startNode;
		degreeTable.put(temp.degree, temp);	
		System.out.println("Starting Meld: "+temp.data+" degree: "+temp.degree);
		temp=temp.loopNode;
		while(temp.loopNode!=startNode){
			if(degreeTable.containsKey(temp.degree)){
				int tempDeg=temp.degree;
				System.out.println("Found matching degree: "+temp.degree+" Node data:"+((HeapNode)degreeTable.get(temp.degree)).data);
				HeapNode matchNode=(HeapNode)degreeTable.get(temp.degree);
				removeHeapNodes(matchNode);
				System.out.println("StartNode after removing:"+matchNode.data+" --"+startNode.data);
				removeHeapNodes(temp);
				System.out.println("StartNode after removing:"+temp.data+" --"+startNode.data);
				
				HeapNode mergeHeap=mergeHeaps(matchNode, temp);		
				System.out.println("MergeHeap Result degree: "); mergeHeap.printData();
				if(startNode==lastNode && lastNode==temp){
					System.out.println("Start=Last:next Node after merge: ");temp.printData();
					HeapNode temp2=startNode;
					startNode=mergeHeap;
					mergeHeap.loopNode=temp2;
					lastNode=mergeHeap;
					lastNode.loopNode=startNode;
					lastNode.prevNode=mergeHeap;
					temp=mergeHeap.loopNode;
					
				}else{
					HeapNode temp2=startNode;
					startNode=mergeHeap;
					mergeHeap.loopNode=temp2;
					temp2.prevNode=mergeHeap;
					lastNode.loopNode=startNode;
					startNode.prevNode=lastNode;
					temp=mergeHeap.loopNode;
					System.out.println("next Node after merge: "); temp.printData();
				}
				
				degreeTable.remove(tempDeg);
				findMin();
				degreeTable.put(mergeHeap.degree, mergeHeap);
			}else{
				System.out.println("Not Found:");
				degreeTable.put(temp.degree, temp);
				temp=temp.loopNode;
				findMin();
			}
		}
		return true;
	}
	
	public HeapNode mergeHeaps(HeapNode first, HeapNode second){
		System.out.println("Merge Heap: "+first.data+"--"+second.data);
		HeapNode temp=new HeapNode();
		if(first.data<second.data||first.data==second.data){
			first.updateChilds(second);
			temp=first;
			System.out.println("First: Meld temp update:"+temp.child.data);
		}else{
			second.updateChilds(first);
			temp=second;
			System.out.println("Second: Meld temp update:"+temp.child.data);
		}
		return temp;
	}
	
	public boolean findMin(){
		HeapNode temp=startNode;
		minHeap=startNode;
		temp=temp.loopNode;
		if(temp.data<minHeap.data){
			minHeap=temp;
		}
		while(temp.loopNode!=startNode){
			if(temp.data<minHeap.data){
				minHeap=temp;
				temp=temp.loopNode;
			}else{
				temp=temp.loopNode;
			}
		}
		return true;
	}
	
	public void removeHeapNodes(HeapNode rm){
		HeapNode temp=startNode;
		System.out.println("In remove node: ");rm.printData();
		System.out.println(" StartNode: ");startNode.printData();
		System.out.println("LastNode: ");lastNode.printData();
		while(temp.loopNode!=startNode){
			if(rm==startNode){
				if(rm.loopNode==lastNode){
					startNode=lastNode;
					lastNode.prevNode=startNode;
					lastNode.loopNode=startNode;
					break;
				}else{
					HeapNode temp2=startNode;
					startNode=startNode.loopNode;
					startNode.prevNode=lastNode;
					break;
				}
				
			}else{
				if(rm==lastNode){
					lastNode=lastNode.prevNode;
					lastNode.loopNode=startNode;
					break;
				}else{
					if(rm==temp){
						HeapNode temp2=temp;
						temp.prevNode.loopNode=temp.loopNode;
						temp.loopNode.prevNode=temp2.prevNode;
						break;
					}else{
						temp=temp.loopNode;
						System.out.println("RM last loop: "+temp.data);
					}
				}
			}
		}
		System.out.println("After Removal: \n======");
		System.out.println("remove node: "+rm.data +" StartNode: ");startNode.printData();
		System.out.println("LastNode: ");lastNode.printData();
	}
	
	public void printHeap(String msg){
		System.out.println(msg);
		HeapNode temp=startNode;
		System.out.print("--"+temp.data+"("+temp.from+","+temp.to+","+temp.degree+")");
		temp=temp.loopNode;
		while(temp!=startNode){
			System.out.print("--"+temp.data+"("+temp.from+","+temp.to+","+temp.degree+")");
			temp=temp.loopNode;
		}
		System.out.println("\nMin Node: "+minHeap.data+" degree: "+minHeap.degree);
		System.out.println("StartNode: "+startNode.data+" LastNode : "+lastNode.data);
	}
}

public class FHeap {

	
	public static void main(String[] args) throws IOException, InterruptedException{
		System.out.println("test");
		FHeapTreeMethods hm=new FHeapTreeMethods();
		int[] data={11,12,14,2,3,0,34,2,5,10};
		hm.startHeap("/home/gaurabde/workspace/Project1_ADS/textInput/graphData.txt");
		//hm.getMin();
		Thread.sleep(1000);
		System.exit(1);
		
	}
}
