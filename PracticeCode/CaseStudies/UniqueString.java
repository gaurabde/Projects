import java.util.Arrays;

public class UniqueString {
	
	public static String replaceBy(String data,String rep){
		char[] repData=data.toCharArray();
		int space=0;
		for(int i=0;i<data.length();i++){
			if(data.charAt(i)==' '){
				++space;
			}
		}
		char[] newData=new char[data.length()+space*3];
		space=0;
		for(int i=0;i<data.length();i++){
			if(repData[i]==' '){
				newData[space]=rep.charAt(0);
				newData[++space]=rep.charAt(1);
				newData[++space]=rep.charAt(2);
				++space;
			}else{
				newData[space]=repData[i];
				++space;
			}
		}
		
		return (new String(newData));
	}
	
	public static String reverseOrder(String word){
		String rev=null;
		for(int i=word.length()-1;i>=0;i--){
			rev=rev+word.charAt(i);
		}
		
		return rev;
	}
	
	public static void removeDuplicates(char[] str) {
		
		if (str == null) return;
		int len = str.length;
		if (len < 2) return;
		int tail = 1;
		for (int i = 1; i < len; ++i) {		
			int j;	
			for (j = 0; j < tail; ++j) {		
				if (str[i] == str[j]){
					System.out.println("Matched: "+str[i]);
					break;
				}
			}	
			if (j == tail) {		
				str[tail] = str[i];
				System.out.println("Change tail: "+str[tail]);
				++tail;
			}
			
		}
			
		str[tail] = 0;
		System.out.println(str);
	}

	
	public static boolean uniqueStr(String word){
		
		String uni=word.toLowerCase();
		char[] arr=uni.toCharArray();
		Arrays.sort(arr);
		System.out.println(arr);
		for(int i=0;i<arr.length;i++){
			if(i>0 && i<arr.length && arr[i]==arr[i-1]){
				System.out.println("Not unique");
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args){
		UniqueString u=new UniqueString();
		//System.out.println("String Unique status: "+u.uniqueStr("aAH;"));
		//System.out.println("Reverse String: "+u.reverseOrder("fagagjAJJJAKSKasdA"));
		//u.removeDuplicates("adsarrraaffs".toCharArray());
		//System.out.println("sfa  faff afa".replace(" ", "%20"));
		System.out.println(u.replaceBy("adsada  gkkkg eirueir 3231231", "%20"));
	}
}