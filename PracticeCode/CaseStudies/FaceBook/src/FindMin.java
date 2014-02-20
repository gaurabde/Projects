import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;


public class FindMin {
	public static void main(String[] args) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader("/home/gaurab/Documents/Eclipse/workspace/FaceBook/find_the_mintxt.txt")); 
	    
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
		BigInteger n,k,a,b,c,r,out;
		
		for(int i=0;i<caseNo;i++){
			stringInput = input.readLine();
			String[] stringSplit=stringInput.spl "  ");
			//HashMap
			n=new BigInteger(stringSplit[0]);
			k=new BigInteger(stringSplit[1]);
			
			System.out.println(n+" "+k);
			stringInput = input.readLine();
			stringSplit=stringInput.split(" ");
			a=new BigInteger(stringSplit[0]);
			b=new BigInteger(stringSplit[1]);
			c=new BigInteger(stringSplit[2]);
			r=new BigInteger(stringSplit[3]);
			System.out.println(a+" "+b+" "+c+" "+r);
			BigInteger[] data=new BigInteger[n.intValue()];
			
			BigInteger[] copyKSize=new BigInteger[k.intValue()];
			data[0]=a;
			copyKSize[0]=a;
			//System.out.print(data[0]+"--"+0+" , ");
			for(int j=1;j<k.intValue();j++){
				a=(((a.multiply(b)).add(c)).mod(r));
				data[j]=a;
				copyKSize[j]=a;
				//System.out.print(a+"--"+j+" , ");
			}
			boolean checker=true;
			int min=1;
			HashMap<BigInteger,String>newMap=new HashMap<BigInteger,String>();
			System.out.println();
			for(int m=k.intValue(),j=0;m<n.intValue();m++,j++){
				//HashMap<BigInteger,String> nextTest=new HashMap<BigInteger,String>(Arrays.data,"");
				newMap=new HashMap(copyArray(data,k.intValue(),m-k.intValue()));
				min=1;
				while(checker){
					if(newMap.containsKey(BigInteger.valueOf(min))==false){
						data[m]=BigInteger.valueOf(min);
						checker=false;
						//System.out.print(m+"=="+min+"\n");
					}else {
						min++;
						//System.out.println("minLoop"+min);
					}
				}checker=true;
			}
			System.out.println("Case #"+(i+1)+" : "+data[n.intValue()-1]);
		}
		

	}
	static HashMap<BigInteger,String> copyArray(BigInteger[] m,int k,int index){
		HashMap<BigInteger,String>copy=new HashMap<BigInteger,String>();
		//System.out.println("index: "+index);
		for(int i=index;i<index+k;i++){
			copy.put(m[i], m[i].toString());
		}
		return copy;
	}
	
}
