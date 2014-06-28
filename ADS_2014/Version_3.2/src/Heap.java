import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Heap{
	public static void main(String args[]) throws FileNotFoundException{
		
		//System.out.println(args.length);
		long start=0,lTotalTime=0,bTotalTime=0;
		//System.out.println("argument: "+args.length +"  "+(args[0]=="-r"));
		if (args.length>=1){
			if(args.length==2){
				if(args[0].equalsIgnoreCase("-il")){
					LeftistTree lTree = new LeftistTree();
					start=System.currentTimeMillis();
					lTree.startTree(args[1]);
					lTotalTime=System.currentTimeMillis() - start;
					System.out.println("\n(leftist Tree) TotalTime taken: "+lTotalTime+" msec");
				}else{
					if(args[0].equalsIgnoreCase("-ib")){
						BinomialHeap bHeap = new BinomialHeap();
						start=System.currentTimeMillis();
						bHeap.startHeap(args[1]);
						bTotalTime=System.currentTimeMillis() - start;
						System.out.println("\n(Binomial Heap) TotalTime taken: "+bTotalTime+" msec");
					}else{
						printErrorNotification();
					}
				}
			}else{
				if(args[0].equalsIgnoreCase("-r")){
					ArrayList<String> data = randomDataGen();
					LeftistTree lTree = new LeftistTree();
					start=System.currentTimeMillis();
					lTree.startTree(data);
					lTotalTime=System.currentTimeMillis() - start;
					BinomialHeap bHeap = new BinomialHeap();
					start=System.currentTimeMillis();
					bHeap.startHeap(data);
					bTotalTime=System.currentTimeMillis() - start;

					System.out.println("\n(leftist Tree) TotalTime taken: "+lTotalTime+" msec");
					System.out.println("\n(Binomial Heap) TotalTime taken : "+bTotalTime +" msec");
				
				}else{
					printErrorNotification();
				}
				
			}
			//BinomialHeap bHeap =  new BinomialHeap();
			//bHeap.startHeap(" " );
		}else{
			printErrorNotification();
		}
	}
	
	
	public static ArrayList randomDataGen(){
		ArrayList<String> dataList = new ArrayList<String>();
		Random ran = new Random();
		int temp=0;
		int delCount=0,insertCount=0;
		for(int i=0; i<5000;i++){
			temp = ran.nextInt(5000);
			if(dataList.contains("I "+temp)){
				dataList.add("D");
				//System.out.println("----D "+temp);
				delCount++;
			}else{
				dataList.add("I "+temp);
				//System.out.println("I "+temp);
				insertCount++;
			}
		}
		System.out.println("DelCount: "+delCount);
		System.out.println("InsertCount: "+insertCount);
		return dataList;
	}
	public static void printErrorNotification(){
		System.out.println("Invalid input modes, please enter correct mode\n\n----------------"
				+ "\tRandom Mode: java Heap -r \n"
				+ "\tUser input Mode:"
				+ "\n---------------\n"
				+ "\t\t 1) Min Leftist Tree: java Heap -il <FilePath>\n"
				+ "\t\t 2) Binomaial Heap: java -ib <FilePath>\n");
		System.exit(-1);
	}
}