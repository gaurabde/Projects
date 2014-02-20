import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ParserDataConvertor {
	public static void main(String args[]) throws IOException{
		String filePath="/home/gaurabde/Documents/Cloud/workSpace/RDSPuchCode/ParserOuputData/productDetails_EBay";
		BufferedReader input = new BufferedReader(new FileReader(filePath));
		System.out.println("loading");
		int i=0;
		String stringInput = input.readLine();
		while(stringInput!=null){
			String [] prod=stringInput.split("::::");
			int len=0;
			while(len<prod.length){
				System.out.print(prod[len]+"--");
				len++;
			}
			System.out.println();
			stringInput = input.readLine();
		}
		
		input.close();
	}
}
