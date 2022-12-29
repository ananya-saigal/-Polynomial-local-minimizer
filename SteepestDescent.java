//SteepestDescent Class is set
import java.util.ArrayList;

public class SteepestDescent {
	private double eps ; // tolerance
	private int maxIter ; // maximum number of iterations
	private double x0; // starting point
	private ArrayList < double [] > bestPoint ; // best point found
	private double [] bestObjVal ; // best obj fn value found
	private double [] bestGradNorm ; // best gradient norm found
	private long [] compTime ; // computation time needed
	private int [] nIter ; // no. of iterations needed
	private boolean resultsExist ; // whether or not results exist
	

	// constructors
	public SteepestDescent () {
		this.bestPoint = new ArrayList < double [] > ();
		this.x0=1.0;
		this.eps= 0.001;
        this.maxIter = 100;
        this.resultsExist = false;
		
	}
	
	public SteepestDescent ( double eps , int maxIter , double x0 ) {
		this.eps=eps;
		this.maxIter=maxIter;
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


	public double getX0() {
		return x0;
	}

	public void setX0(double x0) {
		this.x0 = x0;
	}

	public double [] getBestPoint ( int i ) {
		return bestPoint.get(i);
	}

	public void setBestPoint( int i , double [] a ) {
		this.bestPoint.add(i, a);
	}

	public double[] getBestObjVal() {
		return bestObjVal;
	}

	public void setBestObjVal(int i , double a) {

		this.bestObjVal[i] = a;
	}

	public double[] getBestGradNorm() {

		return bestGradNorm;
	}

	public void setBestGradNorm(int i , double a) {

		this.bestGradNorm[i] = a;
	}

	public long[] getCompTime() {
		return compTime;
	}

	public void setCompTime(int i , long a) {
		this.compTime[i] = a;
	}

	public int[] getNIter() {
		return nIter;
	}

	public void setNIter( int i , int a) {
		this.nIter[i] = a;
	}

	public boolean hasResults () {
		return resultsExist;
	}

	public void setHasResults ( boolean a ) {
		this.resultsExist = a;
	}

	
	// init member arrays to correct size
	public void init ( ArrayList < Polynomial > P ) {
		int length = P.size();
		this.bestObjVal = new double [length];
		 this.compTime= new long [ length ];
		this.nIter = new int [ length ];
		this.bestGradNorm = new double [ length ];
		for (int i = 0; i < length; ++i) {
			this.setBestPoint(i, new double [ P.get(i).getN() ]);
		}
	}

	//Runs Steepest descent algorithm given polynomial P at ith position
    public void run (int i , Polynomial P ) {
		//creating a new array to store directions
		//double [] moving_direction = new double [ P.getN() ];
		//x0 is the starting point, we need to add x0 to the best_point arraylist
		//setBestPoint takes a double array as an argument
	    double [] storing_point = new double [ P.getN() ];
		//storing_point is the array that stores the best point(x0 in every index)
		for (int j = 0; j < P.getN(); j++) {
	    	storing_point[j]=x0;
	    	System.out.print(x0);
	    	System.out.print(',');
		}
		System.out.println(' ');
		//at ith position for ith polynomial the best point initially starts with x0 points
		this.setBestPoint(i, storing_point);
            
        //Initially the niter is 0(initially the amount of iteration is 0)
        this.setNIter(i,0);
        //creating a count variable 
        int count=0;
		
        //Finds the start time of running the steepest descent algorithm
        //We want to later calculate the time it will take the method to complete this algorithm and print the results
        long start=System.currentTimeMillis();
        
        //hasResults(false) is initially false
        this.setHasResults(false);
        this.bestObjVal[i]= P.f(this.getBestPoint(i));
        System.out.println(this.bestObjVal[i]);
        this.bestGradNorm[i]= P.gradientNorm(this.getBestPoint(i));
        System.out.println(this.bestGradNorm[i]);
        //runs until maxIter is reached 
        for (int k = 0; k <this.getMaxIter() ; ++k) {
			//moving_direction is calculated for the points from ith array in bestPoint
        	double [] moving_direction= direction(P , this.getBestPoint(i));
            double[] new_point = new double[this.getBestPoint(i).length];
			//new_point is calculated for the points from ith array in bestPoint
			// (i.e a copy of this.getBestPoint(i))
            System.arraycopy(this.getBestPoint(i), 0, new_point, 0, this.getBestPoint(i).length);
            //when bestGradNorm[i] becomes lower than epsilon-->the loop terminates;
            if (this.bestGradNorm[i] <= this.getEps()) {
            	System.out.println(this.bestGradNorm[i]);
            	break;
            }
           
            double step_size=lineSearch(P,this.getBestPoint(i));
            		
            //adding this new 
           if (step_size==(-1.0)) {
        	   this.setHasResults(true);
        	   this.setNIter(i, (count)+1);
        	   break;
           }
            //finds new point
            for (int j = 0; j < new_point.length; ++j) {
                new_point[j]= new_point[j]+(moving_direction[j]*step_size);
                System.out.println(new_point[j]);
            }
            this.setHasResults(true);
         
            this.setBestPoint(i, new_point);
            this.bestObjVal[i]= P.f(this.getBestPoint(i));
            this.bestGradNorm[i]= P.gradientNorm(this.getBestPoint(i));
            ++count;
			//Updates the time it takes to run the algorithm
			this.setCompTime(i, System.currentTimeMillis() - start);
			this.setNIter(i, (count));
        }
        System.out.println("Polynomial " + (i+ 1) + " done in " + this.getCompTime()[i] + "ms.");
        
    }
	
	
	//returns the some random stepsize
    //this will be overwritten in all other classes/algotrithms
	public double lineSearch(Polynomial P, double[] x) {
		return 0.05;
	}
	
	//Returns the direction, which is the opposite of the slope
	public double[] direction ( Polynomial P , double [] x ) {
		//slope is an array that stores the slopes of points x
		double[] slope=P.gradient(x);  
		double[] directions= new double[slope.length];
		for (int i = 0; i < slope.length; ++i) {
			directions[i]=(-1)*slope[i];	 
		}
		return directions;
	}
	
	//Gets all the parameters from the user
	public boolean getParamsUser ( ) {
		this.eps = Pro5_saigala3.getDouble("Enter tolerance epsilon (0 to cancel): ", 0.0, Double.MAX_VALUE);
		if (this.eps!=0.0){
			this.maxIter = Pro5_saigala3.getInteger("Enter maximum number of iterations (0 to cancel): ", 0, 10000);
			if (this.maxIter!=0) {
				this.x0=Pro5_saigala3.getDouble("Enter value for starting point (0 to cancel): ", 0.0, Double.MAX_VALUE);
				if (this.x0!=0.0) {
					System.out.println("\nAlgorithm parameters set!\n");
					return true;
				}
			}
		}
		return false;
		}
	
	//Prints out algorithm parameters
	public void print() { 
		System.out.println();
        System.out.println("Tolerance (epsilon): " + this.eps);
        System.out.println("Maximum iterations: " + this.maxIter);
        System.out.println("Starting point (x0): "+ this.x0);
	}
	
	public void printAll () {
		for (int i = 0; i <= (this.bestObjVal.length); i++) {
			if (i>0) {
				this.printSingleResult(i, true);
				
			}
			
			else{
				this.printSingleResult(0, false);
			}
		}
	}

		

	//Prints the results in table format
	public void printSingleResult( int i , boolean rowOnly) {
		//Header printed if false
		if (!rowOnly) {
			 System.out.println("-------------------------------------------------------------------------");
			 System.out.println("Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
			 System.out.println("-------------------------------------------------------------------------");
		}
		else {
			 System.out.format("%8d%13.6f%13.6f%9d%17d   ", i, this.bestObjVal[i-1], this.bestGradNorm[i-1], this.nIter[i-1], this.compTime[i-1], this.getBestPoint(i-1));
			 //First best point printed does not have a comma before it
			 System.out.format("   %4.4f", this.bestPoint.get(i-1)[0]);
			 //all other best points printed have a comma before it
			 for (int k = 1; k < this.getBestPoint(i-1).length; ++k) {
				 System.out.format(", %4.4f", this.bestPoint.get(i-1)[k]);
				 }
			System.out.println();
			 }
	
		}
	//Prints out statistical results in table format
	public void printStats () {
		//Initializing the Statistics object
		Statistics stats = new Statistics();
		//Print out the table
		System.out.println("---------------------------------------------------");
		System.out.println("          norm(grad)       # iter    Comp time (ms)");
		System.out.println("---------------------------------------------------");
		System.out.format("%-7s%13.3f%13.3f%18.3f\n", "Average", stats.average_double(this.getBestGradNorm()),
				stats.average_int(this.getNIter()), stats.average_long(this.getCompTime()));
		System.out.format("%-7s%13.3f%13.3f%18.3f\n", "Std dev", stats.std_double(this.getBestGradNorm()),
				stats.std_int(this.getNIter()), stats.std_long(this.getCompTime()));
		System.out.format("%-7s%13.3f%13d%18d\n", "Min", stats.min_double(this.getBestGradNorm()),
				stats.min_int(this.getNIter()), stats.min_long(this.getCompTime()));
		System.out.format("%-7s%13.3f%13d%18d\n", "Max", stats.max_double(this.getBestGradNorm()),
				stats.max_int(this.getNIter()), stats.max_long(this.getCompTime()));

		
	}

}

