import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



class BinaryNode{
	
	public BinaryNode parentNode = null, childNode = null, nextNode = null, prevNode = null;
	public int degree=0, data=0;
	
	public BinaryNode(){
		
	}
	public BinaryNode(int d){
		this.data = d;
		nextNode = null;
		prevNode = null;
		childNode = null;
	}
	void addChildNode(BinaryNode data){
		if(childNode == null){
			childNode = data;
			childNode.nextNode = data;
			childNode.prevNode = data;
			this.degree++;
		}else{
			childNode.prevNode.nextNode = data;
			data.prevNode = childNode.prevNode;
			data.nextNode = childNode;
			childNode.prevNode = data;
			this.degree++;
		}
	}
	
}

class BinaryHeapDB {
	
	BinaryNode startNode = null, lastNode = null, minNode = null;
	
	BinaryHeapDB(){
		//startNode = new BinaryNode();
		//lastNode = new BinaryNode();
		//minNode = new BinaryNode();
	}
	
	void readNodes(String filePath) throws FileNotFoundException{
		String dataset = System.getProperty("user.dir");
		dataset = dataset + "/src/inputData.txt";
		
		BufferedReader br = new BufferedReader(new FileReader(dataset));
		try {
			String data = br.readLine();
			while(data!=null){
				String[] input = data.split(" ");
				if(input[0].toString().equalsIgnoreCase("i")){
					if(input.length>1){
						insertNode(Integer.parseInt(input[1]));
					}else{
						System.out.println("Wrong input file: value after \"I\" missing");
					}
				}else{
					if(input[0].toString().equalsIgnoreCase("d")){
						removeMin();
					}
				}
				//System.out.println(input);
				data = br.readLine();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void insertNode(int data){
		if(startNode==null){
			startNode = new BinaryNode(data);
			lastNode = startNode;
			startNode.nextNode = lastNode;
			startNode.prevNode = lastNode;
			minNode = startNode;
		}else{
			if(startNode.nextNode == lastNode){
				BinaryNode temp = new BinaryNode(data);
				temp.prevNode = lastNode;
				temp.nextNode = startNode;
				lastNode.nextNode = temp;
				startNode.prevNode = temp;
				lastNode = temp;
			}
			
		}
	}
	
	void removeMin(){
		
	}

}


public class BinaryHeap{
	public static void main(String args[]){
				
	}

}
