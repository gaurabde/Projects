import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) throws IOException{
		try{
			Scanner input=new Scanner(System.in);
			int caseNo = Integer.parseInt(input.nextLine()),W,H,N;
			Random r = new Random();
			//number of testCase checker 
			if(!(caseNo>=1&&caseNo<=10)){
				System.out.println("No. of cases not in range (1-10)"+caseNo);
				System.exit(1);
			}
			
			String stringInput;
			for(int i=0;i<caseNo;i++){
				//System.out.println("Main");
				stringInput = input.nextLine();
				String[] stringSplit=stringInput.split(" ");
				W=Integer.parseInt(stringSplit[0]);
				H=Integer.parseInt(stringSplit[1]);
				N=Integer.parseInt(stringSplit[2]);
			
				if(N>=1&&N<=100){
					System.out.println(saveBayar(W, H, N));						
					for(int i=0;i<N;i++){
						co=input1.nextLine();
						String[] stringSplit=co.split(" ");
						if(stringSplit.length<3 || stringSplit.length>3){
							System.out.println("Error input"+stringSplit.length);
							System.exit(1);
						}else{
							X=Integer.parseInt(stringSplit[0]);
							Y=Integer.parseInt(stringSplit[1]);
							R=Integer.parseInt(stringSplit[2]);
						}
						
					}
					
					out=r.nextInt(N);
					//input1.close();
				}else{
					System.out.println("input range details incorrect");
					
				}
				
			}
			
		}catch(Exception e){
			System.out.println("Main method exception");
			e.printStackTrace();
			return;
		}
	}
	static int saveBayar(int W,int H,int N){
		try{
			Scanner input1=new Scanner(System.in);
			String co;
			int X,Y,R,out;
			//System.out.println("Save");
			for(int i=0;i<N;i++){
				co=input1.nextLine();
				String[] stringSplit=co.split(" ");
				if(stringSplit.length<3 || stringSplit.length>3){
					System.out.println("Error input"+stringSplit.length);
					System.exit(1);
				}else{
					X=Integer.parseInt(stringSplit[0]);
					Y=Integer.parseInt(stringSplit[1]);
					R=Integer.parseInt(stringSplit[2]);
				}
				
			}
			Random r = new Random();
			out=r.nextInt(N);
			//input1.close();
			return out;
		}catch(Exception e){
			Random r = new Random();
			int out=r.nextInt(N);
			return out;
		}
	}
}
