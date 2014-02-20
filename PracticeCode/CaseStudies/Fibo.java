
public class Fibo {
	int fibo(int n) {
		
		if (n == 0) {
				
		return 0; // f(0) = 0
			
		} else if (n == 1) {
				
		return 1; // f(1) = 1
			
		} else if (n > 1) {
				
		return fibo(n-1) + fibo(n-2); // f(n) = f(n—1) + f(n-2)
			
		} else {
				
		return -1; // Error condition
			
		}
		}
	int fiboLoop(int n) {
		
		if (n < 0) return -1; // Error condition.
			
		if (n == 0) return 0;
			
		int a = 1, b = 1;
			
		for (int i = 3; i <= n; i++) {
				
		int c = a + b;
				
		a = b;
				
		b = c;
			
		}           
			
		return b;
		}

	public static void main(String[] args){
		Fibo f=new Fibo();
		System.out.println(f.fibo(100));
		System.out.println(f.fiboLoop(400));
	}

}
