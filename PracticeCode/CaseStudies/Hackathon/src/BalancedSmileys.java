import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class BalancedSmileys {
	public static void main(String[] args) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader("C:\\Users\\Gaurab\\Desktop\\facebook\\balanced_smileystxt.txt")); 
	    
		boolean caseStatus=true;
		int caseNo=0;
		String stringInput = input.readLine();
		while(caseStatus){
			caseNo=Integer.parseInt(stringInput);  
			if(caseNo>=1&&caseNo<=50){
				caseStatus=false;
			}else {
				System.out.println("No of cases not in range (1-50)"+caseNo);
				System.exit(1);
			}
		}
    	
		int smiley=0,open=0,close=0;
		for(int i=0;i<caseNo;i++){
	    	stringInput = input.readLine();
	    	//System.out.println(stringInput);
	    	if(stringInput.length()>=2 && stringInput.length()<=500){
	    		int stack=0;
	    		open=0;
	    		close=0;
	    		smiley=0;
	    		for(int j=0;j<stringInput.length();j++){
		        	if(stringInput.charAt(j)=='('){
		        		open++;
		        		stack++;
		        	}else{
		        		if(stringInput.charAt(j)==')'){
		        			close++;
		        			if(stack>0){
		        				stack--;
		        			}
		        			//System.out.println("Close:"+j+" open: "+stack);
		        		}else{
		        			if(stringInput.charAt(j)==':'&&j+1<stringInput.length()){
		        				if(stringInput.charAt(j)==':'&&(stringInput.charAt(j+1)==')')){
		        					smiley++;
			        				if(stack>0){
			        					close++;
			        					stack--;
			        				}
			        				++j;
			        			//	System.out.println("Smily"+j);
		        				}
		        				
		        			}
		        			if(stringInput.charAt(j)==':'&&(j+1<stringInput.length())){
		        				if(stringInput.charAt(j)==':'&&stringInput.charAt(j+1)=='('){
		        					smiley++;
			        				++j;
		        				}
		        				
		        			}
		        		}
		        	}
		    	}
	    		
	    		if(open==close &&stack==0) System.out.println("Case #"+(i+1)+": YES");
	    		else System.out.println("Case #"+(i+1)+": NO");
	    	}else System.out.println("Out of range String enter again");	    		
	    	//System.out.println("Case #"+(i+1)+" : "+" "+stringBeauty);
	    	
	    }


	}
}
