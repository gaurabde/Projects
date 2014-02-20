package tree;

class Node{
	Node right=null,left=null;
	int data;
	
}
class Data{
	Node data;
	Data next;
	Data(Node n){
		data=n;
	}
}
class QueN{
	Data first=null,last=null;
	void enQN(Node n){
		if(first==null){
			first=new Data(n);
			last=first;
			System.out.println("Enqueue First data: "+n.data);
		}else{
			last.next=new Data(n);
			last=last.next;
			System.out.println("Enqueue data: "+n.data);
		}
	}
	Node dQN(){
		Node temp=null;
		if(first==null){
			System.out.println("Queue Empty");
		}else{
			temp=first.data;
			first=first.next;
			System.out.println("Dqueue data: "+temp.data);
			return temp;
		}
		return temp;
	}
}
public class Tree {
	Node root=new Node();
	Node left=new Node();
	Node right=new Node();
	Tree(int[] data){
		
		//Node tempR=new Node();
		int count=0;
		QueN q=new QueN();
		for(int i=0;i<data.length;i++){
			if(i==0){
				root.data=data[i];
				left.data=data[++count];
				right.data=data[++count];
				root.left=left;
				root.right=right;
				System.out.println("Root Element :"+root.data+"  "+count);

				q.enQN(left);
				q.enQN(right);
				
			}else{
				if(count+1>=data.length)break;
				Node rr=q.dQN();
				Node l=new Node();
				Node r=new Node();
				l.data=data[++count];
				if(count+1>data.length){
					r.data=99;
				}else r.data=data[++count];
				rr.left=l;
				if(count+1>data.length){
					rr.right=null;
				}else rr.right=r;
				q.enQN(l);
				q.enQN(r);
				System.out.println(count);
			}
			
		}
	}
	void display(Node root2){
		System.out.println("Root: "+root2.data);
		System.out.println("Root L: "+root2.left.data);
		System.out.println("Root R: "+root2.right.data);
		System.out.println("------------");
		Node temp=root;
		if(root2.left.left!=null&&root2.left.right!=null){
			display(root2.left);
		}
		if(root2.right.left!=null&&root2.right.right!=null){
			display(root2.right);
		}
		
		
	}
	void displayInOrder(Node root){
		if(root.left!=null) displayInOrder(root.left);
		System.out.print(root.data+"--");
		if(root.right!=null) displayInOrder(root.right);
		//while()
	}
	void preOrder(Node root){
		if(root.left!=null ||root.right!=null){
			System.out.print(root.data+"--");
			if(root.left!=null)preOrder(root.left);
			if(root.right!=null)preOrder(root.right);

		}else{
			System.out.print(root.data+"--");
		}
	}

	void postOrder(Node root){
		if(root.left!=null ||root.right!=null){
			if(root.left!=null)postOrder(root.left);
			if(root.right!=null)postOrder(root.right);
			System.out.print(root.data+"--");

		}else{
			System.out.print(root.data+"--");
		}
	}
	int depth(Node root){
		if(root==null) return 0;
		
		return 1+Math.max(depth(root.left),depth(root.right));
	}
	public static void main(String[] args){
		int[] d=new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
		Tree t=new Tree(d);
		t.display(t.root);
		System.out.println("InOrder: ");
		t.displayInOrder(t.root);
		System.out.println("\nPreOrder: ");
		t.preOrder(t.root);
		System.out.println("\nPostOrder: ");
		t.postOrder(t.root);
		System.out.println("\nDepth : "+t.depth(t.root));
	}
	
}
