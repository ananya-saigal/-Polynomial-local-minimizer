//SteepestDescent Class is set

public class SteepestDescent {
	private double eps ; // tolerance
	private int maxIter ; // maximum number of iterations
	private double stepSize ; // step size alpha
	private double [] x0; // starting point
	private double [] bestPoint ; // best point found
	private double bestObjVal ; // best obj fn value found
	private double bestGradNorm ; // best gradient norm found
	private long compTime ; // computation time needed
	private int nIter ; // no. of iterations needed
	private boolean resultsExist ; // whether or not results exist
	
	// constructors
	public SteepestDescent () {
		this.x0=new double[1];
		this.x0[0]=1.0;
		this.bestPoint= new double[1];
		this.eps= 0.001;
        this.maxIter = 100;
        this.stepSize= 0.05;
        this.resultsExist = false;
		
	}
	
	public SteepestDescent ( double eps , int maxIter , double stepSize , double [] x0 ) {
		this.eps=eps;
		this.maxIter=maxIter;
		this.stepSize = stepSize;
		this.x0=x0;
	}
	
	// getters and setters
	public double getEps() {
		return eps;
	}
	public void setEps(double eps) {
		this.eps = eps;
	}
	public int getMaxIter() {
		return maxIter;
	}
	public void setMaxIter(int maxIter) {
		this.maxIter = maxIter;
	}
	public double getStepSize() {
		return stepSize;
	}
	public void setStepSize(double stepSize) {
		this.stepSize = stepSize;
	}
	public double[] getX0() {
		return x0;
	}
	public void setX0( int j , double a) {
		this.x0[j] = a;
	}
	public double[] getBestPoint() {
		return bestPoint;
	}
	public void setBestPoint(double[] bestPoint) {
		this.bestPoint = bestPoint;
	}
	public double getBestObjVal() {
		return bestObjVal;
	}
	public void setBestObjVal(double bestObjVal) {
		this.bestObjVal = bestObjVal;
	}
	public double getBestGradNorm() {
		return bestGradNorm;
	}
	public void setBestGradNorm(double bestGradNorm) {
		this.bestGradNorm = bestGradNorm;
	}
	public long getCompTime() {
		return compTime;
	}
	public void setCompTime(long a) {
		this.compTime = a;
	}
	public int getnIter() {
		return nIter;
	}
	public void setnIter(int nIter) {
		this.nIter = nIter;
	}
	public boolean isResultsExist() {
		return resultsExist;
	}
	public void setResultsExist(boolean resultsExist) {
		this.resultsExist = resultsExist;
	}
	
	//Initializing x0 and bestPoint array given the array has to be length n
	public void init ( int n ) {
		this.x0 = new double[n];
        this.bestPoint = new double[n];
	}
	
	//Runs Steepest descent algorithm given polynomial P
    public void run ( Polynomial P ) {
        //If polynomial P and x0 are not same length then x0 is made to be of length n with only values of 1.0
        if (x0.length != P.getN()) {
            System.out.println("WARNING: Dimensions of polynomial and x0 do not match! Using x0 = 1-vector of appropriate dimension.\n");
            this.init(P.getN());
            for (int i = 0; i < P.getN(); i++) { 
                setX0( i , 1.0);
            }
        }
            
        //Initially the niter is 0(initially the amount of iteration is 0)
        this.setnIter(0);
        
        //copies x0 array into best point array, x0 array is the array of starting points 
        //initially best point array contains only the starting points 
        System.arraycopy(x0, 0, this.bestPoint, 0, x0.length);

        //Finds the start time of running the steepest descent algorithm
        //We want to later calculate the time it will take the method to complete this algorithm and print the results
        long start=System.currentTimeMillis();
        
        //printResults(false) prints out only the header
        this.printResults(false);
        
        //runs until maxIter is reached 
        for (int i = 0; i <= this.getMaxIter() ; ++i) {
            this.bestObjVal= P.f(this.bestPoint);
            this.bestGradNorm= P.gradientNorm(this.bestPoint);
            double[] moving_direction= direction (P , this.getBestPoint());
            double[] new_point = new double[this.bestPoint.length];
            System.arraycopy(this.bestPoint, 0, new_point, 0, this.bestPoint.length);

            //finds new point
            for (int j = 0; j < new_point.length; ++j) {
                new_point[j]= new_point[j]+(moving_direction[j]*this.stepSize);
            }
            this.printResults(true);
            //the new_point array is copied onto bestPoint array
            System.arraycopy(new_point, 0, this.bestPoint, 0,this.bestPoint.length);
            
            //when bestGradNorm becomes lower than epsilon-->the loop terminates;
            if (this.bestGradNorm <= this.getEps()) {
                break;
            }
            ++this.nIter;
            this.compTime = System.currentTimeMillis()-start;

        }
        setResultsExist(true);
        System.out.println();
    }
	
	
	//returns the stepSize
	public double lineSearch () {
		return this.getStepSize();
	}
	
	//Returns the direction, which is the opposite of the slope
	public double[] direction ( Polynomial P , double [] x ) {
		double[] slope=P.gradient(x); //slope
		double[] directions= new double[slope.length];
		for (int i = 0; i < slope.length; ++i) {
			directions[i]=(-1)*slope[i];	 
		}
		return directions;
	}
	
	//Gets all the parameters from the user
	public void getParamsUser ( int n ) {
		System.out.println();
		this.eps = Pro3_saigala3.getDouble("Enter tolerance epsilon (0 to cancel): ", 0.0, Double.MAX_VALUE);
		if (this.eps!=0.0){
			this.nIter = Pro3_saigala3.getInteger("Enter maximum number of iterations (0 to cancel): ", 0, 10000);
			if (this.nIter!=0) {
				this.stepSize= Pro3_saigala3.getDouble("Enter step size alpha (0 to cancel): ", 0.0, Double.MAX_VALUE);
				if (this.stepSize!=0.0) {
					System.out.println("Enter values for starting point: ");
					init(n);
			        for (int i = 0; i < n; ++i) {
			        	this.x0[i] = Pro3_saigala3.getDouble("   x" + (i + 1) + ": ", -1.7976931348623157E308, Double.MAX_VALUE);
			        }
				}
			}
		}
	}
	
	//Prints out algorithm parameters
	public void print() { 
		System.out.println();
        System.out.println("Tolerance (epsilon): " + this.eps);
        System.out.println("Maximum iterations: " + this.maxIter);
        System.out.println("Step size (alpha): " + this.stepSize);
        System.out.print("Starting point (x0): ( ");
        System.out.format("%.2f", this.x0[0]);
        for (int i = 1; i < x0.length; ++i) {
            System.out.format(", %.2f", this.x0[i]);
        }
        System.out.println(" )\n");	
	}
	
	//Prints the results in table format
	public void printResults ( boolean rowOnly ) {
		//Prints out the header if rewOnly is false
		if (!rowOnly) {
			 System.out.println("--------------------------------------------------------------");
			 System.out.println("      f(x)   norm(grad)   # iter   Comp time (ms)   Best point");
			 System.out.println("--------------------------------------------------------------");
		} 
		//Prints the results calculated above
		else {
			System.out.format(" %9.6f", this.getBestObjVal());
			System.out.format("%13.6f", this.getBestGradNorm());
			System.out.format("%9d", this.getnIter());
			System.out.format("%17d", this.getCompTime());
			
			int i=1;
			//First best point printed does not have a comma before it
			System.out.format("   %.4f", this.bestPoint[0]);
			//all other best points printed have a comma before it
			while (i<(bestPoint.length)){
				System.out.format(", %.4f", this.bestPoint[i]);
				i++;
			}
			System.out.println();
		
			
		}

	}
		
	}

