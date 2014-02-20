
class Node2{
	Node2 next=null,prev=null;
	int data;
	Object data2;
	Node2(int d){
		data=d;
	}
	
}

class QueueWitStack{
	Stack s1=new Stack();
	Stack s2=new Stack();
	int buff=5;
	
	void enQ(int d){
		if(s1.count==5){
			System.out.println("Buffer Full");
			s1.displayS();
		}else{
			s1.push(d);
		}
	}
	void deQ(){
		if(s2.last==null){
			System.out.println("No Node to Dequeue checking Stack 1");
			int temp=0;
			while(s1.last!=null){
				s2.push(s1.pop());
			}
			System.out.println("Dequeu: "+s2.pop());
			
		}else{
			System.out.println("Dequeu: "+s2.pop());
		}
	}
	void displayQS(){
		System.out.println("Stack1: ");
		s1.displayS();
		System.out.println("Stack2: ");
		s2.displayS();
		
	}
}
class Queue{
	Node2 first=null,last=null;
	
	void enQueue(int d){
		if(first==null){
			first=new Node2(d);
			last=first;
		}else{
			last.next=new Node2(d);
			last=last.next;
		}
	}
	void dQueue(){
		if(first.next==null){
			System.out.println("Last Node in Queue deQueued: "+first+" : "+first.data);
		}else{
			first=first.next;
		}
	}
	void displayQ(){
		Node2 n=first;
		System.out.println("Starting Node: "+n+" : "+n.data);
		while(n.next!=null){
			n=n.next;
			System.out.print("--"+n.data);
		}
		System.out.println();
	}
}

class Stack{
	Node2 last=null;
	int count=0;
	
	void push(int d){
		if(last==null){
			last=new Node2(d);
			count++;
		}else{
			Node2 n=new Node2(d);
			n.next=last;
			System.out.println("Added New:"+n.data+" last:"+last.data);
			last=n;
			count++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	int pop(){
		
		if(last.next==null){
			System.out.println("Last element poped out of Stack: "+last.data);
			int temp=last.data;
			last=null;
			count=0;
			return temp;
		}else{
			System.out.println("poped: "+last.data);
			int temp=last.data;
			last=last.next;
			count--;
			return temp;
		}
	}
	void displayS(){
		Node2 n=last;
		if(n==null){
			System.out.println("No Node in the Stack");
		}else{
			System.out.println("First Node of Stack: "+n.data);
			while(n!=null){
				System.out.print("--"+n.data);
				n=n.next;
			}
			System.out.println();
		}
	}
}

public class QueueStack {
	public static void main(String[] args){
		//s.push(0);
		//s.displayS();
		//for(int i=1;i<10;++i){
			//s.push(i);
		//}
		//s.push(2);
		//s.displayS();
		//s.pop();
		//s.displayS();
		QueueWitStack qs=new QueueWitStack();
		qs.enQ(3);
		qs.displayQS();
		for(int i=0;i<5;i++){
			qs.enQ(i);
		}
		qs.deQ();
		qs.displayQS();
		System.out.println((23/10)%10);
	}
}
