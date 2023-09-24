public class Polynomial {
	
	double [] coefficients;
	
	public Polynomial() {
		this.coefficients = new double [1];
		this.coefficients[0] = 0;
		
	}
	
	public Polynomial(double [] coeff) {
		this.coefficients = new double[coeff.length];
		for (int i = 0; i < coeff.length; i++) {
			this.coefficients[i] = coeff[i];
		}
		
	}
	
	public Polynomial add (Polynomial poly) {
		Polynomial new_poly = new Polynomial();
		if (poly.coefficients.length > this.coefficients.length) {
			new_poly = new Polynomial(poly.coefficients);
		} else {
			new_poly = new Polynomial(this.coefficients);
		}
		
				
		for (int i = 0; i < Math.min(poly.coefficients.length, this.coefficients.length); i++) {
			new_poly.coefficients[i] = this.coefficients[i] + poly.coefficients[i];
		}
		
		
		return new_poly;
	}
	
	public double evaluate (double x) {
		double sum = 0;
		for (int i = 0; i < this.coefficients.length; i++) {
			sum += this.coefficients[i]*(Math.pow(x, i));
			
		}
		return sum;
	}
	
	public boolean hasRoot (double x) {
		if (this.evaluate(x) == 0) {
			return true;
		}
		return false;
	}
	
}