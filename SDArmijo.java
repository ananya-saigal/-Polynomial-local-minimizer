 public class SDArmijo extends SteepestDescent {
    private double maxStep ; // Armijo max step size
    private double beta ; // Armijo beta parameter
    private double tau ; // Armijo tau parameter
    private int K ; // Armijo max no. of iterations
    
    // constructors
    public SDArmijo (){
        this.maxStep = 1.0 ;
        this.beta = 1.0e-4 ;
        this.tau = 0.5 ;
        this.K = 10 ;
    }

    public SDArmijo ( double maxStep , double beta , double tau , int K ){
        this.maxStep = maxStep ;
        this.beta = beta ;
        this.tau = tau ;
        this.K = K ;
    }

    // getters and setters
    public double getMaxStep (){
        return this.maxStep ;
    }
    public void setMaxStep ( double maxStep ){
        this.maxStep = maxStep ;
    }
    public double getBeta (){
        return this.beta ;
    }
    public void setBeta ( double beta ){
        this.beta = beta ;
    }
    public double getTau (){
        return this.tau ;
    }
    public void setTau ( double tau ){
        this.tau = tau ;
    }
    public int getK (){
        return this.K ;
    }
    public void setK ( int K ){
        this.K = K ;
    }

    // Armijo line search method
    //Inheriting from the lineSearch in SteepestDescent class
    
    @Override
    public double lineSearch( Polynomial P , double [] x ) {
        //amijo line search
        double[] direction= super.direction(P,x);
        double function = P.f(x);
        double magnitude = Math.pow(P.gradientNorm(x), 2.0);
        double step = this.getMaxStep();
        double[] new_array= new double[x.length];
        int count=0;
        do{
            for (int i = 0; i < new_array.length; i++) {
                new_array[i] = x[i] + step * direction[i];
            }
            if (P.f(new_array)<=function - this.getBeta() * step * magnitude) {
            	break;
            }
            step = this.getTau() * step;
            count=count+1;

        } while (count<this.getK());

        
        if (count==this.K) {
        	step=(-1.0);
        	System.out.println("   Armijo line search did not converge!");
        }
        //use step to update x
        return step;
    }


    //  Inheriting from the getParamsUser in SteepestDescent class
    public final boolean getParamsUser (){
            //  Inheriting from the getParamsUser in SteepestDescent class
            //If true, then the user will enter further parameters
            System.out.println("Set parameters for SD with an Armijo line search:");
            boolean check=false;
            double step_size=Pro5_saigala3.getDouble("Enter Armijo max step size (0 to cancel): ", 0.0, Double.MAX_VALUE);
            if (step_size!=0.0) {
                this.maxStep=step_size;
                this.beta = Pro5_saigala3.getDouble("Enter Armijo beta (0 to cancel): ", 0.0, 1.0);
                if (this.beta != 0.0) {
                    this.tau = Pro5_saigala3.getDouble("Enter Armijo tau (0 to cancel): ", 0.0, 1.0);
                    if (this.tau != 0.0) {
                        this.K = Pro5_saigala3.getInteger("Enter Armijo K (0 to cancel): ", 0, Integer.MAX_VALUE);
                        if (this.K != 0) {
                            this.maxStep = step_size;
                            check = true;
                            }
                        }
                    }
                }
             
            if(check){
                return (super.getParamsUser());
            }
            //If false, we convert all parameters to default values
            else {
                this.maxStep = 1.0 ;
                this.beta = 1.0e-4 ;
                this.tau = 0.5 ;
                this.K = 10 ;
                return false;
            }
        }

    // print parameters
    //Uses the print method in SteepestDescent
    //adds the parameters of the Armijo line search
    public void print () {
        super.print();
        System.out.println("Armijo maximum step size: " + this.maxStep);
        System.out.println("Armijo beta: " + this.beta);
        System.out.println("Armijo tau: " + this.tau);
        System.out.println("Armijo maximum iterations: " + this.K);
    }

 }
 








