import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintStream;

public class Polynomial {
	
	double [] coefficients;
	int [] exponents;
	
	public Polynomial() {
		coefficients = null; 
		exponents = null;
	}
	
	public Polynomial(double [] coeff, int [] exp) {
		this.coefficients = new double[coeff.length];
		this.exponents = new int[exp.length];
		for (int i = 0; i < coeff.length; i++) {
			this.coefficients[i] = coeff[i];
			this.exponents[i] = exp[i];
		}
	}
	
	public Polynomial (File inputFile) throws FileNotFoundException {
		Scanner scanner = new Scanner(inputFile);
		String eq = scanner.nextLine();
		
		eq = eq.replace("-", "+-");
		
		String [] coe_exp = eq.split("\\+", -1);
		int num = 0;
		for (int i = 0; i < coe_exp.length; ++i) {
			if (coe_exp[i].length() != 0) {
				num++;
			}
		}
		
		double [] res_coe = new  double[num];
		int [] res_exp = new int[num];
		int j = 0;
		for (int i = 0; i < coe_exp.length; ++i) {
			if (coe_exp[i].length() == 1) {
				res_exp[j] = 0;
				res_coe[j] = Double.parseDouble(coe_exp[i]);
				j++;
			} else if(coe_exp[i].length() != 0) {
				String [] var = coe_exp[i].split("x");
				res_coe[j] = Double.parseDouble(var[0]);
				res_exp[j] = (int)Double.parseDouble(var[1]);
				j++;
			}
			
		}
		if (num == 0) {
			this.coefficients = null;
			this.exponents = null;
		} else {
			this.coefficients = res_coe;
			this.exponents = res_exp;
		}
		scanner.close();
	}
	
	public void saveToFile(String inputFile) throws FileNotFoundException {
		File file = new File(inputFile); 
		PrintStream ps = new PrintStream(file);
		
		if (this.coefficients != null && this.exponents != null) {
			
			for (int i = 0; i < this.coefficients.length; i++) {
				ps.print(this.coefficients[i]);
				if (this.exponents[i] != 0) {
					ps.print("x");
					ps.print(this.exponents[i]);
				}
				if (i != this.coefficients.length - 1) {
					if (this.coefficients[i] < 0) {
					} else {
						ps.print("+");
					}
					
				}
				
			}
		}
		
		ps.close();
		
	}
	
	public Polynomial reduced (Polynomial poly) {
		int num = 0;
		for (int i = 0; i < poly.coefficients.length; i++) {
			if(poly.coefficients[i] == 0) {
				num = num + 1;
			}
		}
		
		if(num == poly.coefficients.length) {
			Polynomial res = new Polynomial();
			return res;
		} else if (num != 0) {
			double [] res_cf = new  double[poly.coefficients.length - num];
			int [] res_exp = new int[poly.coefficients.length - num];
			int j = 0;
			for (int i = 0; i < poly.coefficients.length; i++) {
				if(poly.coefficients[i] != 0) {
					res_cf[j] = poly.coefficients[i];
					res_exp[j] = poly.exponents[i];
					j++;
				}
			}
			Polynomial res = new Polynomial(res_cf, res_exp);
			return res;
		}
		
		return poly;
		
	}
	
	public double evaluate (double x) {
		
		double sum = 0;
		if(this.coefficients == null) {
			return 0;
		}
			
		for (int i = 0; i < this.coefficients.length; i++) {
			sum += this.coefficients[i]*(Math.pow(x, this.exponents[i]));
		}
		
		return sum;
	}
	
	public boolean hasRoot (double x) {
		if (this.evaluate(x) == 0) {
			return true;
		}
		return false;
	}
	
	public Polynomial repeated (Polynomial poly) {
		for (int i = 0; i < poly.coefficients.length; i++) {
			for (int j = i + 1; j < poly.coefficients.length; ++j) {
				if (poly.exponents[i] == poly.exponents[j]) {
					poly.coefficients[i] += poly.coefficients[j];
					poly.coefficients[j] = 0;
				}
			}
		}
		
		return poly;
		
	}
	
	public Polynomial add (Polynomial poly) {
		if (poly.coefficients == null && this.coefficients != null) {
			Polynomial new_poly = new Polynomial(this.coefficients, this.exponents);
			return new_poly;
		}
		
		if (poly.coefficients != null && this.coefficients == null) {
			Polynomial new_poly = new Polynomial(poly.coefficients, poly.exponents);
			return new_poly;
		}
		
		if (poly.coefficients == null && this.coefficients == null) {
			Polynomial new_poly = new Polynomial();
			return new_poly;
		}
		double [] coe_size = new double[poly.coefficients.length + this.coefficients.length];
		int [] exp_size = new int[poly.exponents.length + this.exponents.length];
		
		Polynomial new_poly = new Polynomial(coe_size, exp_size);
		
		for (int i = 0; i < poly.coefficients.length; ++i) {
			new_poly.coefficients[i] =poly.coefficients[i];
			new_poly.exponents[i] =poly.exponents[i];
			
		}
		for (int i = 0; i < this.coefficients.length; ++i) {
			new_poly.coefficients[poly.coefficients.length + i] =poly.coefficients[i];
			new_poly.exponents[poly.coefficients.length + i] =poly.exponents[i];
			
		}
		
		
		new_poly = repeated(new_poly);
		
		new_poly = reduced(new_poly);
		
		
		Polynomial res_poly = new Polynomial();
		res_poly = new_poly;
		
		
		
		return res_poly;
	}
	
	
	
	public Polynomial multiply (Polynomial poly) {
		if (poly.coefficients == null || this.coefficients == null) {
			Polynomial new_poly = new Polynomial();
			return new_poly;
		}
		
		Polynomial new_poly = new Polynomial();
		double [] coe_size = new double[poly.coefficients.length * this.coefficients.length];
		int [] exp_size = new int[poly.exponents.length * this.exponents.length];
		new_poly = new Polynomial(coe_size, exp_size);
		int z =0;
		for (int i = 0; i < poly.coefficients.length; i++) {
			for (int j = 0; j < this.coefficients.length; j++) {
				new_poly.coefficients[z] = this.coefficients[i] * poly.coefficients[j];
				new_poly.exponents[z] = this.exponents[i] + poly.exponents[j];
				z++;
				
			}
			
		}
		
		
		Polynomial res_poly = new Polynomial();
		
		new_poly = repeated(new_poly);
		res_poly = reduced(new_poly);
		
		
		return res_poly;
	}
	
	
	
}
