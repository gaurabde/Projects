import java.io.IOException;
import java.util.Scanner;



public class PushPop {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	private int data[]={1,2,3,4,5,6,7,8,9,10,11,12};
	private int que1[]=new int[5];
	private int que2[]=new int[5];
	private int count=0;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("");
		int i=0;
		
		boolean m=true;
		Scanner in=new Scanner(System.in);
		int userIn=0;
		PushPop d=new PushPop();
		while(m){
			Runtime.getRuntime().exec("clear");
			System.out.println("1. Push");
			System.out.println("2. Pop");
			System.out.println("3. Display Queue");
			System.out.println("4. Exit");
			userIn=in.nextInt();
			System.out.println(userIn);
			switch (userIn){
				case 1: d.push(); break;
				case 2: d.pop(); break;
				
				case 3: 
					System.out.println("Queue details\n");
					for(int r=0;r<5;r++){
							System.out.print(d.que1[r]+"  ");
						}
					break;
				default: System.out.println("Default/Exit case"); System.exit(0);
			}
			//d.push();
			//System.out.println(d.que1[4]);
		}
		for (i=0;i<6;i++){
			
		}
		

	}
	public void push(){
		int i=0;
		for(i=0;i<5&&count<=data.length;i++){
			//System.out.print(que1[i]);
			if(count>=data.length){
				System.out.print("No data in data table / starting again");
				break;
			}
			if(que1[i]==0){
				que1[i]=data[count];
				count++;
				System.out.println("PUSH: "+que1[i]+" Count: "+count+" Data Len:"+data.length);
				break;
			}
			if(i==4){
				System.out.println("Queue Full POP out data");
			}
		}
		
	}
	public void pop(){
		int i=0;
		for(i=0;i<5;i++){
			if(que1[i]==0&&i>0){
				System.out.println("POP: "+que1[i-1]);
				que1[i-1]=0;
				break;
			}
			if(que1[i]==0 &&i==0){
				System.out.println("POP done queue empty: "+que1[i]);
				que1[i]=0;
				break;
			}
			if(i==4 && que1[i]!=0){
				System.out.println("POP: "+que1[i]);
				que1[i]=0;
				break;
			}
		}
	}

}
