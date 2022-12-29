
//class runs the fixed line search variation
//similar to line search results in taking too many iterations if the step size is too small
//method steps over the local minimum if the step size is too big, and thus never improving
public class SDFixed extends SteepestDescent {
    // In this class its a fixed step size
	//Default alpha value
    private double alpha = 0.01;
    // constructors
     public SDFixed (){
     }
     public SDFixed ( double alpha ){
        this.alpha = alpha;
     }
    // getters and setters
    public double getAlpha (){
        return this.alpha;
    }
    public void setAlpha ( double a ){
        this.alpha = a;
    }
    
    // fixed step size
    //lineSearch method overrides the lineSearch method in SteepestDescent
    @Override
    public double lineSearch( Polynomial P , double [] x ){
        return this.getAlpha();
    }
        
    // get algorithm parameters from user
    //Uses the getParamsUser method in SteepestDescent
    public boolean getParamsUser () {
        System.out.println("Set parameters for SD with a fixed line search:");
    	double step_size=Pro5_saigala3.getDouble("Enter fixed step size (0 to cancel): ", 0.0, Double.MAX_VALUE);
        if (step_size==0.00) {
        	return false;
        }
        else {
        	if (super.getParamsUser()) {
        		this.alpha=step_size;
        		return true;	
        	}
        	else {
        		return false;
        	}
        }
    	
    }
    
    // print parameters
   //Uses the print method in SteepestDescent
    public void print () {
         super.print();
        System.out.println("Fixed step size (alpha): " + this.getAlpha());

    }

    
    

}
