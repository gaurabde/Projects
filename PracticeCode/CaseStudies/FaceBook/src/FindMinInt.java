import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;


public class FindMinInt {
	public static void main(String[] args) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader("/home/gaurab/Documents/Eclipse/workspace/FaceBook/FindMin.txt")); 
	    
		boolean caseStatus=true;
		int caseNo=0;
		String stringInput = input.readLine();
		while(caseStatus){
			caseNo=Integer.parseInt(stringInput);  
			if(caseNo>=1&&caseNo<=20){
				caseStatus=false;
			}else {
				System.out.println("No of cases not in range (1-20)"+caseNo);
				System.exit(1);
			}
		}
		int n,k,a,b,c,r,out;
		
		for(int i=0;i<caseNo;i++){
			stringInput = input.readLine();
			String[] stringSplit=stringInput.split(" ");
			//HashMap
			n=Integer.parseInt(stringSplit[0]);
			k=Integer.parseInt(stringSplit[1]);
			
			System.out.println(n+" "+k);
			stringInput = input.readLine();
			stringSplit=stringInput.split(" ");
			a=Integer.parseInt(stringSplit[0]);
			b=Integer.parseInt(stringSplit[1]);
			c=Integer.parseInt(stringSplit[2]);
			r=Integer.parseInt(stringSplit[3]);
			System.out.println(a+" "+b+" "+c+" "+r);
			
			for(int j=0;j<=n+10;j++){
				System.out.print(a+"--"+j+" , ");
				a=(b*a+c)%r;
				
			}
			System.out.println("\nCase #"+(i+1)+" : "+a);
		}
		

	}
}
