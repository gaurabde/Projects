
/*
 * @Author: Gaurab Dey
 * @Version: 2.2
 * @Date: 06/22/2014
 * @Class: BinomialHeap
 * @Description: BinomialHeap uses get the minimum value from the set of Input and Delete operations
 * @Input Support: support both random Input/Delete as well as structured file input from user 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/*
 * HeapNode: Basic Node structure for B-Heap with details:
 * 				Data, Parent Node,Degree of Node
 */
class HeapNode{
	HeapNode parentNode=null;
	boolean root=false;
	int data=-99, to=-99,degree=0;
	
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
	
	HeapNode(int val){
		this.data = val;
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
		System.out.print(this.data);
	}
}

/*
 * FHeapTreeMethods: Main method for create, initiate and manage the B-Heap
 * 
 */
public class BinomialHeap{
	public HeapNode minHeap=new HeapNode();
	public HeapNode startNode=new HeapNode();
	public HeapNode lastNode=new HeapNode();
	public int noEdges;
	public int noVerts;
	
	//Default constructor
	public BinomialHeap() {
		minHeap=null;
		startNode=null;
	}
	
	//Method to start the B-Heap with the given file location containing structured vertices and edge data
	public boolean startHeap(String filePath) throws FileNotFoundException{
		
		String dataset = System.getProperty("user.dir");
		//System.out.println(dataset);
		dataset = dataset + "/src/inputData.txt";
		HeapNode minNode = new HeapNode();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		try {
			String data = br.readLine();
			while(data!=null){
				String[] input = data.split(" ");
				////System.out.println(input[0].toString().equalsIgnoreCase("I")+": ");
				if(input[0].toString().equalsIgnoreCase("i")){
					insertBHeap(Integer.parseInt(input[1]));
					//displayHeap("Insert Opetation");
				}else{
					if(input[0].toString().equalsIgnoreCase("d")){
						 minNode= getMin();
						 //displayHeap("Delete Opetation");
					}
				}
				////System.out.println(input);
				data = br.readLine();
				
			}
			displayHeap("\n\n====================\nFinal Binomial Heap\n====================\n");;
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return true;
	}
	
	
	public boolean startHeap(ArrayList<String> data){
		HeapNode minNode = new HeapNode();
		for(int i=0;i<data.size();i++){
			String[] input = data.get(i).split(" ");
			////System.out.println(input[0].toString().equalsIgnoreCase("I")+": ");
			if(input[0].toString().equalsIgnoreCase("i")){
				insertBHeap(Integer.parseInt(input[1]));
			}else{
				if(input[0].toString().equalsIgnoreCase("d")){
					 minNode= getMin();
				}
			}
		}
		displayHeap("\n\n==================\nFinal Binomial Heap\n====================\n");;
		return true;
	}
	
	
	//Method inserting the random and visiting node edge weights in the B-Heap for getting the MST
	public void insertBHeap(int data){
		HeapNode temp=new HeapNode(data);
		//System.out.println("Creating Node: "+temp.data);
		
		if(startNode==null){
			startNode=temp;
			startNode.loopNode=temp;
			startNode.prevNode=temp;
			minHeap=temp;
			lastNode=temp;
			//System.out.println("StartNode :"+temp.data);
		}else{
			if(startNode.loopNode==lastNode){
				if(minHeap.data>data){
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
				if(minHeap.data>data){
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
		
		//printHeap("After Insert");
		meldFheap();
		findMin();
		//return heapData;
	}
	
	//Method to get the Minimum value from the B-Heap
	public HeapNode getMin(){
		HeapNode returnMin= minHeap;
		//displayHeap("Before getMin : "+returnMin.data );
		if(minHeap==null){
			//System.out.println("Matrix problem min is NULL");
			return null;
		}
		//condition to check various stages of min before remove form B-Heap
		if(minHeap.degree==0){
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
					  //  System.out.println("Min:"+minHeap.loopNode.data);
						if(minHeap.loopNode==lastNode){
							//System.out.println("LastNode --> "+lastNode.loopNode.data);
							startNode.loopNode=lastNode;
							lastNode.prevNode=startNode;
							minHeap=null;
							//printHeap("s->m->l");
						}else{
							startNode.loopNode=minHeap.loopNode;
							minHeap.loopNode.prevNode=startNode;
							minHeap=null;
						}
						
					}else{
						minHeap.prevNode.loopNode=minHeap.loopNode;
						minHeap.loopNode.prevNode=minHeap.prevNode;
						System.out.println("Min child null: "+minHeap.data);
						minHeap=null;
					}					
				}
			}
			
			//printHeap("Before Meld:\n======");
			meldFheap();
			findMin();
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
			
			//printHeap("Before Meld:\n======");
			meldFheap();
			findMin();
		}
		return returnMin;
	}
	
	//Method perform the meld operation after Removal of minimum node from B-Heap
	public boolean meldFheap(){
		HashMap<Integer,HeapNode> degreeTable=new HashMap<Integer, HeapNode>();
		HeapNode temp=startNode;
		minHeap=startNode;
		if((startNode==null||lastNode==null)||(startNode==lastNode)){
			System.out.println("");//"Empty Heap / only one node");
		}else{
			// degree based search in the B-Heap for pair-wise compare
			boolean firstEntry=false;
			while(temp!=startNode||firstEntry==false){
				firstEntry=true;
				if(degreeTable.containsKey(temp.degree)){
					int tempDeg=temp.degree;
					//System.out.println("Found matching degree: "+temp.degree+"current Node: "+temp.data+" Node data:"+((HeapNode)degreeTable.get(temp.degree)).data);
					HeapNode matchNode=(HeapNode)degreeTable.get(temp.degree);
					removeHeapNodes(matchNode);
					//printHeap("First Remove:");
					//System.out.println("StartNode after 1st removing:"+matchNode.data+" --"+startNode.data);
					
					removeHeapNodes(temp);
					//System.out.println("StartNode after 2nd removing:"+temp.data+" --"+startNode.data);
					//printHeap("SecondRemove");
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
					////System.out.println("Not Found Added in degree Tree:");
					degreeTable.put(temp.degree, temp);
					temp=temp.loopNode;
					//findMin();
					//printHeap("After Meld:\n======");
				}
			}
		}
		findMin();
		return true;
	}
	
	//method to merge the nodes based on same degree and add it back to the B-Heap
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
	
	//Method to find minimum value of current B-Heap after remove and meld operation
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
	
	//method remove the give nodes from B-Heap for the meld operation
	public void removeHeapNodes(HeapNode rm){
		HeapNode temp=startNode;
		
			if(rm==startNode){
				if(rm.loopNode==lastNode){
					startNode=lastNode;
					lastNode.prevNode=startNode;
					lastNode.loopNode=startNode;
					//System.out.println("RM=startNode=lastNode "+rm.prevNode.data+"-->"+rm.loopNode.data+" -- "+rm.data);
				}else{
					HeapNode temp2=startNode;
					startNode=startNode.loopNode;
					startNode.prevNode=lastNode;
					lastNode.loopNode=startNode;
					//System.out.println("RM=startNode "+rm.prevNode.data+"-->"+rm.loopNode.data+" -- "+rm.data);
				}
				
			}else{
				if(rm==lastNode){
					lastNode=lastNode.prevNode;
					lastNode.loopNode=startNode;
					startNode.prevNode=lastNode;
					//System.out.println("RM=lastNode "+rm.prevNode.data+"-->"+rm.loopNode.data+" -- "+rm.data);
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
						}else{

							rm.prevNode.loopNode=rm.loopNode;
							rm.loopNode.prevNode=rm.prevNode;
							//System.out.println("Removing middle min: "+rm.prevNode.data+"-->"+rm.loopNode.data+" -- "+rm.data);
						}
				}
			}
		
	}
	
	//Method used basically for debugging the code getting the status of code at any point.
	public void printHeap(String msg){
		System.out.println("\n\n-------"+msg+"-------");
		if(startNode==null||lastNode==null){ 
			System.out.println("Empty Heap");
			
		}else{
			HeapNode temp=startNode;
			System.out.print("--"+temp.data+"("+temp.degree+")");
			temp=temp.loopNode;
			while(temp!=startNode){
				System.out.print("--"+temp.data+"("+temp.degree+")");
				temp=temp.loopNode;
			}
			System.out.println("\nMin Node: "+minHeap.data+" degree: "+minHeap.degree);
			System.out.println("StartNode: "+startNode.data+" LastNode : "+lastNode.data);
		}
		
		
	}
	
	void displayHeap(String msg){
		System.out.println(msg);
		boolean check = true;
		HeapNode tempNode = new HeapNode();
		ArrayList<HeapNode> temp1 = new ArrayList<HeapNode>();
		ArrayList<HeapNode> temp2 = new ArrayList<HeapNode>();
		int level=1;
		//startLoop
		tempNode = startNode;
		temp1.add(tempNode);
		if(startNode.loopNode!=startNode){
			tempNode = startNode.loopNode;
			while(tempNode!=startNode){
				temp1.add(tempNode);
				tempNode = tempNode.loopNode;
			}
		}
		System.out.print("\nLevel "+level+": [");
		while(check){
			int index = 0;
			while(temp1.size()>0){
				tempNode = temp1.get(index);
				tempNode.printData();
				temp1.remove(tempNode);
				if(tempNode.child!=null){
					HeapNode tempChildNode = tempNode.child;
					temp2.add(tempNode.child);
					tempChildNode = tempChildNode.loopNode;
					while(tempChildNode!=tempNode.child){
						temp2.add(tempChildNode);	
						tempChildNode = tempChildNode.loopNode;
					}
				}
				if(temp1.size()==0){
					System.out.print("]\n");
				}else{
					System.out.print(", ");
				}
			}
			if(temp2.size()>0){
				level++;
				System.out.print("Level "+level+": [");
			}
			while(temp2.size()>0){
				tempNode = temp2.get(index);
				tempNode.printData();
				temp2.remove(tempNode);
				if(tempNode.child!=null){
					HeapNode tempChildNode = tempNode.child;
					temp1.add(tempChildNode);
					tempChildNode =  tempChildNode.loopNode;
					while(tempChildNode!=tempNode.child){
						temp1.add(tempChildNode);
						tempChildNode = tempChildNode.loopNode;
					}
				}
				if(temp2.size()==0){
					System.out.print("]\n");
				}else{
					System.out.print(", ");
				}
			}
			if(temp1.size()==0 && temp1.size()==0){
				check = false;
			}else{
				level++;
				System.out.print("Level "+level + ": [");
			}
		}
	}
}
