import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;




public class BeautifulString {
	public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new FileReader("C:\\Users\\Gaurab\\Desktop\\facebook\\beautiful_stringstxt (1).txt")); 
	    //System.out.print("Describe the product: ");
		boolean caseStatus=true;
		int caseNo=0;
		String stringInput = input.readLine();
		while(caseStatus){
			caseNo=Integer.parseInt(stringInput);  
			if(caseNo>=2&&caseNo<=50){
				caseStatus=false;
			}else {
				System.out.println("No of cases not in range (2-50)"+caseNo);
			}
		}
    	
    	int stringBeauty=0;
	    for(int i=0;i<caseNo;i++){
	    	stringInput = input.readLine().toLowerCase();
	    	System.out.println(stringInput);
	    	if(stringInput.length()>=2 && stringInput.length()<=500){
	    		for(int j=0;j<stringInput.length();j++){
		        	stringBeauty=stringBeauty+((int)stringInput.charAt(j)-97);
		    	}
	    	}else System.out.println("Out of range String enter again");	    		
	    	System.out.println("Case #"+(i+1)+" : "+" "+stringBeauty);
	    	
	    }
	    
	    
    }
}
