
/*
 * @Author: Gaurab Dey
 * @Version: 1.3
 * @Date: 06/24/2014
 * @Class: LeftistTree
 * @Description: Leftist Tree uses get the minimum value from the set of Input and Delete operations
 * @Input Support: support both random Input/Delete as well as structured file input from user 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


class LeftistNode{
	LeftistNode leftChild = null, rightChild = null, parentNode = null;
	int sValue,data;
	public LeftistNode(){
		
	}
	public LeftistNode(int d){
		data = d;
		sValue = 0;
	}
	
	public void addRightChild(LeftistNode data){
		if (leftChild==null){
			leftChild = data;
			leftChild.parentNode = this;
			balanceSvalue();
		}else{
			if(rightChild==null){
				rightChild = data;
				rightChild.parentNode = this;
				balanceSvalue();
			}
		}
	}
	
	public void addParent(LeftistNode p){
		parentNode = p;
	}
	
    void balanceSvalue(){
		if (leftChild!=null && rightChild!=null){
			if(leftChild.sValue<rightChild.sValue){
				LeftistNode temp = rightChild;
				rightChild = leftChild;
				leftChild = temp;
				sValue = rightChild.sValue + 1;
				if(parentNode!=null){
					parentNode.balanceSvalue();
				}else{
					System.out.println("-----ParentNode balanced");
				}
			}else{
				if(leftChild.sValue == rightChild.sValue){
					sValue = leftChild.sValue+1;
				}else{
					sValue = rightChild.sValue + 1;
				}
			}
		}else{
			sValue = 0;
		}
		//System.out.println("Node -  Node:"+this.data+" svalue:"+this.sValue);
		displayData();
	}
    
    public void printTree(int level){
    	if(leftChild!=null){
    		leftChild.printTree(level++);
    	}else{
        	if(rightChild!=null){
        		rightChild.printTree(level++);	
        	}else{
        		System.out.println("Node - P: "+parentNode.data+" L: "+level+" data: "+data+" ");
        	}
    	}
    	
    	
    }
    
    public void displayData(){
    	System.out.print("Node -  Node: "+this.data);
    	if(leftChild!=null){
    		System.out.print(" leftchild: "+this.leftChild.data);
    		System.out.print(" LP: "+this.leftChild.parentNode.data);
    		
    		if(rightChild!=null){
    			System.out.print(" rightChild: "+this.rightChild.data);
    			System.out.print(" RP: "+this.leftChild.parentNode.data);
        		
    		}
    	}
    	if(parentNode!=null){
    		System.out.print(" P:"+this.parentNode.data+ " P-sValue: "+this.parentNode.sValue+"\n");
    	    //this.parentNode.displayData();
    	}else
    		System.out.println(" Root Node: "+data+"\n");
    }
}

class LeftistTreeDB{
	private LeftistNode rootNode =null, lastNode = null;
	
	public void readDataFromFile(String filePath)throws FileNotFoundException{
		
		String dataset = System.getProperty("user.dir");
		dataset = dataset + "/src/inputData.txt";
		
		BufferedReader br = new BufferedReader(new FileReader(dataset));
		try {
			String data = br.readLine();
			while(data!=null){
				String[] input = data.split(" ");
				//System.out.println(input[0].toString().equalsIgnoreCase("I")+": ");
				if(input[0].toString().equalsIgnoreCase("i")){
					addElement(Integer.parseInt(input[1]));
				}else{
					if(input[0].toString().equalsIgnoreCase("d")){
						removeMin();
					}
				}
				//System.out.println(input);
				data = br.readLine();
				
			}
			displayHeap("\n\n=========\nFinal Binomial Heap\n=======\n");;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addElement(int data){
		System.out.println("-----------Adding: "+data);
		if(rootNode!=null){
			if(rootNode.data>data){
				LeftistNode  temp = new LeftistNode(data);	
				temp.addRightChild(rootNode);
				rootNode = temp;
				System.out.println("Root swap");
				//rootNode.balanceSvalue();
			}else{
				if(rootNode.leftChild == null | rootNode.rightChild == null){
					System.out.println("Root child null");
					rootNode.addRightChild(new LeftistNode(data));
				}else{
					LeftistNode newNode =new LeftistNode(data);
					//rootNode.rightChild = temp;			
					addTree(newNode);	
					displayHeap("\n\n -- After Adding");
				}
			}
		}else{
			rootNode = new LeftistNode(data);
			System.out.println("Root Node added: "+data);
		}
	}
	
	public LeftistNode addTree(LeftistNode newNode){
		LeftistNode temp = rootNode;
		boolean check=true;
		while(check){
			System.out.println("temp: "+temp.data);
			if(temp.data<=newNode.data){
				if(temp.rightChild!=null){
					temp = temp.rightChild;
					System.out.println("RightChild movement");
					//temp.displayData();
				}else{
					temp.addRightChild(newNode);
					//temp.displayData();
					check=false;
				}
			}else{
				newNode.parentNode = temp.parentNode;
				temp.parentNode.rightChild = newNode;
				newNode.addRightChild(temp);
				System.out.println("ParentNode exchange: current: "+ temp.data+" P:"+temp.parentNode.data+"");
				//temp.displayData();
				check = false;
			}
		}
		return null;
	}
	
	public void removeMin(){
		System.out.println("Delete Called: "+rootNode.data);
		if(rootNode.leftChild!=null && rootNode.rightChild!=null){
			System.out.println("Delete: both right left present");
			if(rootNode.leftChild.data<rootNode.rightChild.data){
				LeftistNode temp = rootNode.rightChild;
				rootNode = rootNode.leftChild;
				rootNode.parentNode = null;
				temp.parentNode = null;
				addTree(temp);
			}else{
				LeftistNode temp = rootNode.leftChild;
				rootNode = rootNode.rightChild;
				rootNode.parentNode =null;
				temp.parentNode = null;
				addTree(temp);
			}
		}else{
			if(rootNode.leftChild!=null){
				rootNode = rootNode.leftChild;
				rootNode.parentNode=null;
			}
		}
		displayHeap("\n\nAfter Delete");
	}
	
	public void displayHeap(String msg){
		System.out.println(msg);
		boolean check = true;
		LeftistNode tempNode = new LeftistNode();
		ArrayList<LeftistNode> temp1 = new ArrayList<LeftistNode>();
		ArrayList<LeftistNode> temp2 = new ArrayList<LeftistNode>();
		int level=0;
		temp1.add(rootNode);
		while(check){
			int index = 0;
			System.out.println("Level: "+level);
			while(temp1.size()>0){
				tempNode = temp1.get(index);
				tempNode.displayData();
				temp1.remove(tempNode);
				if(tempNode.leftChild!=null){
					temp2.add(tempNode.leftChild);
					if(tempNode.rightChild!=null){
						temp2.add(tempNode.rightChild);
					}
				}
			}
			level++;
			System.out.println("Level: "+level);
			while(temp2.size()>0){
				tempNode = temp2.get(index);
				tempNode.displayData();
				temp2.remove(tempNode);
				if(tempNode.leftChild!=null){
					temp1.add(tempNode.leftChild);
					if(tempNode.rightChild!=null){
						temp1.add(tempNode.rightChild);
					}
				}
			}
			if(temp1.size()==0 && temp1.size()==0){
				check = false;
			}
		}
	}
	

}

public class LeftistTree {
	public static void main(String args[]) throws FileNotFoundException{
		LeftistTreeDB lDB =  new LeftistTreeDB();
		lDB.readDataFromFile(" ");
	}
}
