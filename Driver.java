import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.File;

public class Driver {
	public static void main(String [] args) throws FileNotFoundException {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		
		double [] c1 = {-1,-2,-3,-4};
		int [] i1 = {1,2,3,4};
		Polynomial p1 = new Polynomial(c1, i1);
		double [] c2 = {1,2, 3, 4};
		Polynomial p2 = new Polynomial(c2, i1);
		
		Polynomial s = p1.add(p2);
		s.saveToFile("/Users/alexortega/eclipse-workspace/one/src/output");
		p1.saveToFile("/Users/alexortega/eclipse-workspace/one/src/input");
		
		System.out.println("p2(0.1) = " + p2.evaluate(0.1));
		if(p2.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
						
		
		
		
		File file = new File("/Users/alexortega/eclipse-workspace/one/src/input");
		Polynomial p3 = new Polynomial(file);
		Polynomial p4 = p1.multiply(p2);
		p3.saveToFile("/Users/alexortega/eclipse-workspace/one/src/output2");
		p4.saveToFile("/Users/alexortega/eclipse-workspace/one/src/output3");
		
	}
}
