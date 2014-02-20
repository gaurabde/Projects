
import java.io.IOException;
import java.util.Scanner;

public class PrimeGen {
	public static void main(String[] args) throws IOException{
		try{
		//BufferedReader input = new BufferedReader(new FileReader("/home/gaurab/Documents/Eclipse/testFiles/prime.txt")); 
		
		Scanner input=new Scanner(System.in);
		int caseNo = Integer.parseInt(input.nextLine()),n,m;
		
		//number of testCase checker 
		if(!(caseNo>=1&&caseNo<=10)){
			System.out.println("No. of cases not in range (1-10)"+caseNo);
			System.exit(1);
		}
		
		String stringInput;
		for(int i=0;i<caseNo;i++){
			stringInput = input.nextLine();
			String[] stringSplit=stringInput.split(" ");
			m=Integer.parseInt(stringSplit[0]);
			n=Integer.parseInt(stringSplit[1]);
			
			//System.out.println(n+" "+m);
			
			if((m>=1)&&(n>=m)&&(n-m<=100000)&&(n<=1000000000)){
				while(m<=n){
					if(primeChecker(m)==true){
						System.out.println(m);
						m++;
					}else m++;					
				}
				System.out.println();
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
	
	static boolean primeChecker(int i){
		
		try{
		int check=2;
		if(i<1) 
			return false;
		
		while(check<=i/2+1){
			if(i%check==0)
				return false;
			else
				check++;
		}
		return true;
		}
		catch(Exception e){
			System.out.println("Prime method exeption");
			return false;
		}
	}
	
	

}


