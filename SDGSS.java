public class SDGSS extends SteepestDescent{
	 	private final double _PHI_= (1.0 + Math.sqrt(5.0)) / 2.0; 
	    private double maxStep;// Armijo max step size
	    private double minStep;// Armijo beta parameter
	    private double delta;// Armijo delta parameter

	   // constructors
	   public SDGSS () {
	        this.maxStep = 1.0;
	        this.minStep = 0.001;
	        this.delta = 0.001;
	    }

	    public SDGSS ( double maxStep , double minStep , double delta ) {
	    	this.maxStep = maxStep;
	        this.minStep = minStep;
	        this.delta = delta;
	    }
	    
	    // getters and setters
		public double getMaxStep (){
	        return this.maxStep ;
	    }
	    public void setMaxStep ( double maxStep ){
	        this.maxStep = maxStep ;																						
	    }
	    public double getMinStep (){
	        return this.minStep ;
	    }
		public void setMinStep ( double minStep ){
	        this.minStep = minStep ;
	    }
		public double getDelta (){
	        return this.delta ;
	    }
		public void setDelta ( double delta ){
	        this.delta = delta ;
	    }
		//  Inheriting from the getParamsUser in SteepestDescent class
		@Override
		public final boolean getParamsUser() {
			//  Inheriting from the getParamsUser in SteepestDescent class
			//If true, then the user will enter further parameters
			System.out.println("Set parameters for SD with a golden section line search:");
			double step_size=Pro5_saigala3.getDouble("Enter GSS maximum step size (0 to cancel): ", 0.00, 1.7976931348623157E308);
			boolean check=false;
			if (step_size!=0.0) {
				this.setMaxStep(step_size);
				this.minStep=Pro5_saigala3.getDouble("Enter GSS minimum step size (0 to cancel): ", 0.00, this.getMaxStep());
				if (this.minStep!=0.0) {
					this.delta=Pro5_saigala3.getDouble("Enter GSS delta (0 to cancel): ", 0.00, 1.7976931348623157E308);
					if (this.delta!=0.0) {
						check=true;
					}
				}
			}
			if (check) {
				return (super.getParamsUser());
			}
			//If false, we convert all parameters to default values
            else {
				this.maxStep = 1.0 ;
				this.minStep = 0.001;
				this.delta = 0.001 ;
				return false;
			}
		}

		//Condition for the GSS line search
		//when b-a is less than or equal delta, then return the average of a and b ((b + a) / 2.0)
		//if the distance bettween a and c is greater than the distance between b and c, then interval is [a,c]
		//if the distance bettween a and c is less than the distance between b and c, then interval is [b,c]
	    //If f(c) is greater than f(a) or f(c) is greater than f(b), then check if f(a) is greater than f(b)(!)
	    //If f(a) is greater than f(b), then return b( i.e the minimum value)(!)
	    //a is the min step size and b is the max step size
	    //If f(c) is less than f(y) change the interval to b,y,c
		private double GSS ( double a , double b , double c , double [] x , double [] dir , Polynomial P ){
			//  If the distance between a and b is less than or equal to delta, then return the average of a and b
			if (Math.abs(b - a) <= this.delta) {
				return (b + a) / 2.0;
			}
			// To calculate f(a) and f(b) and f(c) we need to input an array of values
			//Simmilar to what we did in the Armijo line search
			double [] x_a = new double [x.length];
			double [] x_b = new double [x.length];
			double [] x_c = new double [x.length];
			for (int i = 0; i < x.length; i++) {
				x_a[i] = x[i] + a * dir[i];
				x_b[i] = x[i] + b * dir[i];
				x_c[i] = x[i] + c * dir[i];
			}
			//calculate f(a) and f(b) and f(c)
			double a_value=P.f(x_a);
			double b_value=P.f(x_b);
			double c_value=P.f(x_c);
			//  If f(c) is greater than f(a) or f(c) is greater than f(b), then check if f(a) is greater than f(b)
			if (c_value > a_value || c_value > b_value) {
				//  If f(a) is greater than f(b), then return b
				if (a_value > b_value) {
					return b;
				}
				else {
					return a;
				}
			}
			
			//  If the distance between a and c is greater than the distance between b and c, then interval is [a,c]
			if (Math.abs(a - c) > Math.abs(b - c)) {
				//as per the document we sample a point y in the larger interval [a, c])
				//y=a+(c-a)/this._PHI_ (this._PHI_ is the golden ratio)
				double y= a+(c-a)/this._PHI_;
				
				//finding f(y)
				double [] x_y = new double [x.length];
				for (int i = 0; i < x.length; i++) {
					x_y[i] = x[i] + y * dir[i];
				}
				double y_value=P.f(x_y);
				    		   
				if (y_value<c_value){
					//interval is [a,c]; y is point between a and c
					return this.GSS(a, c,y, x, dir, P);
				}
				
				else{
					//interval is [y,b]; c is point between y and b
					return this.GSS(y, b,c, x, dir, P);
				}
			}
		
				
			//  If the distance between a and c is less than the distance between b and c, then interval is [c,b]
			else {
				//as per the document we sample a point y in the larger interval [c, b])
				//y=b-(b-c)/this._PHI_
				double y= b-(b-c)/this._PHI_;
				
				//finding f(y)
				double [] x_y = new double [x.length];
				for (int i = 0; i < x.length; i++) {
					x_y[i] = x[i] + y * dir[i];
				}
				double y_value=P.f(x_y);
				
				//same condition as above y is point between a and c
				if (y_value<c_value){
					//interval is [c,b]
					return this.GSS(c,b,y, x, dir, P);
				}
				
				else{
					//interval is [a,y] c is point between y and b
					return this.GSS(a, y,c, x, dir, P);
				}

		}	
	}
			
	

		@Override
		public double lineSearch( Polynomial P , double [] x ) {
	    	double[] direction= super.direction(P,x);
			double step=this.GSS(this.minStep, this.maxStep, (this.minStep + (this.maxStep - this.minStep) / this._PHI_), x, direction, P);
			return step;
	    }

		public void print () {
			super.print();
			System.out.println("GSS maximum step size: " + this.getMaxStep());
			System.out.println("GSS minimum step size: " + this.getMinStep());
			System.out.println("GSS delta: " + this.getDelta());
		
		}


	}

