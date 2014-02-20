import java.util.ArrayList;
import java.util.HashMap;


public class TestClass {
	public static void main(String[] args){
		System.out.println("Hi");
		String[] w={"dsdsdsd","dsdsdsdsd"};
		String[] m={"aggaevdf","fahrtra"};
		
		int[]r=new int[26];
		System.out.println(check("abcdefghijklmkkopqrstuvwx"));
		
		
		
	}
	
	public static Boolean check(String c){
		int[]mm=new int[26];
		int v=0;
		for(int i=0;i<c.length();i++){
			v=-97+c.charAt(i);
			System.out.println((int)c.charAt(i));
			if(mm[v]==1){
				return false;
			}else{
				mm[v]=1;
			}
		}
		return true;
	}
}
