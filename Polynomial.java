//Polynomial Class is set
public class Polynomial {
	private int n =0; // no. of variables
    private int degree =0; // degree of polynomial
	private double [][] coefs =new double[0][0]; // coefficients
	
	// constructors
	public Polynomial () {
		
	}
	public Polynomial (int n , int degree , double [][] coefs) { 
		this.n=n;
		this.degree=degree;
		this.coefs = coefs;
	}
	// getters	
	public int getN () {
		return n;
	}

	public int getDegree() {
		return degree;
	}
	public double [][] getCoefs () {
		return coefs;
	}
	
	
	// setters
	public void setN ( int n ) {
		this.n = n;
		
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	
	public void setCoef ( int j , int d , double a ) {
		 this.coefs[j][d] = a;
	}
	
	// init sets arrays to correct size
	//there are n rows and (degree+1) columns in Coefs array 
	public void init () { 
		this.coefs = new double[this.n][this.degree + 1];
	}
	
	// calculate function value at point x
	public double f ( double [] x ) {
		double value=0.0;
		double add = 0.0;
		
		//indexing through the coefs array
		for (int i = 0; i < this.getN(); ++i) {
            for (int j = 0; j <(this.getDegree()+1); ++j) {
            	//Multiplying each elements in the coefs array, with the x-value at the same row index in x array
        		//each x-value is brought to an exponent  
                add= this.coefs[i][j] * Math.pow(x[i], this.getDegree() - j);
                value+=add;
            }
        }
        return value;
    }
	
	// index through x array and calculate gradient for each element
	//gradient uses the concept of derivatives 
	//store gradient value into an array and return the array
	public double [] gradient ( double [] x ) {
		double[]storing_array=new double[x.length];
		int degree;
		double variable;
		double value;
		double add;	   
		for (int i = 0; i < this.getN(); ++i) {
			add=0.0;
			variable= x[i];	     
			degree=this.getDegree();
			for (int j = 0; j < this.getDegree(); ++j) {
            	if (degree>1) {
            		value=((this.getCoefs()[i][j])*(variable)*(degree));        	
            	}
            	else {
            		value=this.getCoefs()[i][j];
            	}
				
            	add= add+ value;
            	degree-=1;		 
            }
            storing_array[i]=add;
		}
		return storing_array;
	}
	
	
	
	// calculate norm of gradient at point x
	public double gradientNorm ( double [] x ) { 
		double total=0.0;
		double[] calculated_gradient = this.gradient(x);	
		//adding together the square of each value in the x array
		for (int i = 0; i < calculated_gradient.length; ++i) {
			total+=Math.pow(calculated_gradient[i],2);
		}
		//returning the square root of the total
		return Math.pow(total,0.5);
	}
	
	// indicate whether polynomial is set
	//it's a set if n is not 0
	public boolean isSet () { 
		return this.getN()!=0;
	}
	
	// print out the polynomial
	public void print () { 
		System.out.println();
        System.out.print("f(x) = ");
        for (int i = 0; i < n; ++i) {
            System.out.print("( ");
            for (int j = 0; j <degree; ++j) {
                System.out.format("%.2fx%d^%d + ", coefs[i][j], i + 1, degree - j);
            }
            System.out.format("%.2f )", this.coefs[i][degree]);
            if (i < n - 1) {
                System.out.print(" + ");
            }
        }
        System.out.println('\n');
        
	}
	}
	

