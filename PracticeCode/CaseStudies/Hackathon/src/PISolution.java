import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class PISolution {
	public static void main(String[] args){
		Scanner scan =new Scanner(System.in);
		int count=scan.nextInt();
		double pi=1;
		while(count>0){
			int n=scan.nextInt();n--;
			while(n>0){
				//System.out.print((Math.round((Math.pow(-1, n)/(2*n+1))*Math.pow(10,15))/Math.pow(10, 15))+"--"+n+"; ");
				pi+=(Math.round((Math.pow(-1, n)/(2*n+1))*Math.pow(10,15))/Math.pow(10, 15));
				--n;
				//(Math.pow(-1, n)/(2*n+1));n--;
			}
			//(Math.round(pi)*Math.pow(10,15))/(Math.pow(10,15))
			count--;System.out.println("\n"+(Math.round(pi*Math.pow(10,15)))/(Math.pow(10,15))+"--"+n);
		}
	}
}

/*import java.io.*;import java.util.*;import java.math.*;public class Solution{public static void main(String[] args){Scanner scan =new Scanner(System.in);int n,count=scan.nextInt();double pi=1.0;while(count>0){n=scan.nextInt();n--;while(n>0){pi+=(Math.pow(-1, n)/(2*n+1));n--;}count--;System.out.println((Math.round(pi*Math.pow(10,15)))/(Math.pow(10,15)));pi=1;}}}
*/
