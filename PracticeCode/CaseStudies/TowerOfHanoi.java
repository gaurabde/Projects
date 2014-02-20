

public class TowerOfHanoi {
	public static void main(String[] args){
		System.out.println("Test");
		TowerOfHanoi tw=new TowerOfHanoi();
		tw.solveTower(5, "A", "C", "B");
	}
	
	public static void solveTower(int num, String from,String to, String using) {

		if (num == 1) {
		    // Base Case: Move 1 disk...
		    System.out.println("Move disk from " + from +" to " + to + ".");
		}
		else {
		    // Recursive Case: 
		    //   Move num-1 disks from the from peg to
		    //   the usingPeg using the toPeg.
		    solveTower(num-1, from, using, to);   // RC1
		
		    //   Move 1 disk from the fromPeg to the toPeg using
		    //   the usingPeg.
		    solveTower(1, from, to, using);            // RC2
		    
		    //   Move num-1 disks from the usingPeg to 
		    //   the toPeg using the fromPeg.
		    solveTower(num-1, using, to, from);   // RC3
		}
	}
}
