

class Node{
	Node next=null;
	Node prev=null;
	int data;
	public Node(int d){
		data=d;
	}
	void append(int d){
		Node newNode=new Node(d);
		Node n=this;
		
		while(n.next!=null){
			n=n.next;
		}
		n.next=newNode;
		System.out.println(newNode+" : "+d);
	}
	Node deleteNode(Node head,int data){
		Node n=head;
		System.out.println("variable passed: "+head+" "+data);
		if(n.data==data){
			return n.next; //New Head
		}
		while(n.next!=null){
			if(n.next.data==data){
				n.next=n.next.next;
				return head;//Node deleted taken care by GC
			}
			n=n.next;
		}
		System.out.println("Node dont exists in the LinkList");
		return head;
	}
}
public class LinkList {
	public static void main(String[] args){
		Node n=new Node(0);
		System.out.println("Head: "+n+" : "+0);
		for(int i=1;i<10;++i){
			n.append(i);
		}
		
		
		
		n.deleteNode(n, 5);
		System.out.println("After Deletetion\n");
		while(n!=null){
			System.out.println(n+" : "+n.data);
			n=n.next;
		}
	}
}
