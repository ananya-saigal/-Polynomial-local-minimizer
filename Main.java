import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Pro3_saigala3 {
    //Initializing a public BufferedReader object; the only BufferedReader object in the entire program
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    
    //creating the main method
    public static void main(String[] args) throws IOException{
    	String output=" ";	
    	
    	//Creating Polynomial and SteepestDescent objects
    	Polynomial polynomial= new Polynomial();
    	SteepestDescent SD= new SteepestDescent();
    	
    	//Loop runs for all inputs except Q (Q makes the loop end)
    	do{
            displayMenu();
            output= reader.readLine().toUpperCase();
            
			if (output.equals("E")){
            	Polynomial new_polynomial= polynomial;
            	System.out.println();
            	
            	//getPolynomialDetails returns a boolean; 
            	//if (not false)= true; then the process is cancelled, this happens if user inputs 0 
            	if (!getPolynomialDetails(new_polynomial)) {
            		System.out.println("\nProcess canceled. No changes made to polynomial function.\n");
            	}
            	
            	//if true, user has inputed a value other than 0, hence polynomial is initialized
            	else {
            		 new_polynomial.init();
            		 for (int i = 0; i < new_polynomial.getN(); ++i) {
                         System.out.println("Enter coefficients for variable x" + (i + 1) + ": ");
                         for (int j = 0; j < new_polynomial.getDegree() + 1; ++j) {
                        	 double coefficient= getDouble("   Coefficient " + (j + 1) + ": ", -1.7976931348623157E308, Double.MAX_VALUE);
                        	 new_polynomial.setCoef(i, j, coefficient);
                         }  
            		 }
            		 System.out.println("\nPolynomial complete!\n");
            	}
            }

			//Checks to see if polynomial is inputed and then prints the polynomial
            else if (output.equals("F")) {
                if (polynomial.getN()==0) {
                    System.out.println("\nERROR: Polynomial function has not been entered!\n");
                }
                else {
                    polynomial.print();
                }
            }
			
			//Sets algorithm parameters if polynomial exists 
            else if (output.equals("S")) {
                if (polynomial.getN()==0) {
                    System.out.println("\nERROR: Polynomial function has not been entered!\n");
                }
                else {
                	if (!getAlgorithmParams(SD,polynomial.getN())) {
                		System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
                	}
                	else {
                		System.out.println("\nAlgorithm parameters set!\n");
                	}
                }
            }
			//Prints steepest descent parameters
			else if (output.equals("P")) {
            	SD.print();
            }
			
			
			//runs steepest descent 
            else if (output.equals("R")) {
            	if (polynomial.getN()==0) {
                    System.out.println("\nERROR: Polynomial function has not been entered!\n");
                }
            	else {
            		System.out.println();
                    SD.run(polynomial);
            	}
            }
            
			//Display algorithm performance
            else if (output.equals("D")) {
                if (SD.getnIter()==0) {
                    System.out.println("\nERROR: No results exist!\n");
                }
                else {
                    SD.printResults(false);
                    SD.printResults(true);
                    System.out.println();;
                }
            }
			
			//Quit
            else {
                if (!(output.equals("Q"))){
                    System.out.println("\nERROR: Invalid menu choice!\n");
                }
            }
			
        //Once the user inputs Q the program ends
        }while (!(output.equals("Q")));
    System.out.println("\nThe end.");
    }

    //Display Menu
    public static void displayMenu() {
        System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
        System.out.println("E - Enter polynomial function");
        System.out.println("F - View polynomial function");
        System.out.println("S - Set steepest descent parameters");
        System.out.println("P - View steepest descent parameters");
        System.out.println("R - Run steepest descent algorithm");
        System.out.println("D - Display algorithm performance");
        System.out.println("Q - Quit");
        System.out.print("\nEnter choice: ");
    }



 //Function ensures a valid integer input is provided by the user 
 //To be valid the input is in between the specified max and min value 
public static int getInteger(String prompt, int LB, int UB) {
    int input=0;
    int valid=0;
    //Creating error messages
    String error_message;
    if (UB==Integer.MAX_VALUE){
        error_message="ERROR: Input must be an integer in ["+LB+", infinity]!\n";
        }
    else if (LB==Integer. MIN_VALUE){
    	error_message="ERROR: Input must be an integer in [-infinity, "+UB+"]!\n";;
    }
    else{
    	error_message="ERROR: Input must be an integer in ["+LB+", "+UB+"]!\n";
    }

    //Catching and printing error message if input is not integer or specified bound
    while (valid==0){
        System.out.print(prompt);
        
        try {
            input= Integer.parseInt(reader.readLine());
           
        }
        catch(NumberFormatException e) {
            valid = 0;
            input=-1;
        	
            
        }
        catch(IOException e) {
            valid = 0;
            input=-1;
        	
            
        }
        
        if (input>=LB && input<=UB) {
            valid=1;
        }
        else {
        	valid=0;
        	System.out.println(error_message);
            
        }
    }    
    //returns the valid input
    return input;
}


//Function ensures a valid double input is provided by the user 
//To be valid the input is in between the specified max and min value 
public static double getDouble(String prompt, double LB, double UB) {
    double input=0.0;
    int valid=0;
    //Creating error messages
    String error_message;
    if (UB==Double.MAX_VALUE){
        error_message="ERROR: Input must be an integer in ["+LB+", infinity]!\n";
        }
    if (LB==-1.7976931348623157E308){//
    	error_message="ERROR: Input must be an integer in [-infinity, "+UB+"]!\n";;
    }
    if ((LB==-1.7976931348623157E308) && (UB==Double.MAX_VALUE )){
    	error_message="ERROR: Input must be an integer in [-infinity, infinity]!\n";
    }
    else{
    	error_message="ERROR: Input must be an integer in ["+LB+", "+UB+"]!\n";
    }

    //Catching and printing error message if input is not integer or specified bound
    while (valid==0){
        System.out.print(prompt);
        
        try {
            input= Double.parseDouble(reader.readLine());
           
        }
        catch(NumberFormatException e) {
            valid = 0;
            input=-1.0;
        	
            
        }
        catch(IOException e) {
            valid = 0;
            input=-1.0;
        	
            
        }
        
        if (input>=LB && input<=UB) {
            valid=1;
        }
        else {
        	valid=0;
        	System.out.println(error_message);
            
        }
    }    
    //returns the valid input
    return input;
}


//If number of variables or degree is 0; method returns false
//else degree and number of variables are set in the polynomial class 
public static boolean getPolynomialDetails(Polynomial P){
	int num_var=getInteger("Enter number of variables (0 to cancel): ", 0, Integer.MAX_VALUE);
	if (num_var==0){
		return false;
	}
	else {
		P.setN(num_var);
		int degrees=getInteger("Enter polynomial degree (0 to cancel): ", 0, Integer.MAX_VALUE);
		
		if (degrees==0) {
			return false ;
		}
		P.setDegree(degrees);
		return true	;
		
	}
		
	}
	
//Utilizes SteepestDescent's getParamsUser method to see if any parameter is 0 or 0.0; getAlgorithmParams method returns false
//else true is returned 
public static boolean getAlgorithmParams(SteepestDescent SD, int n) {
	SD.getParamsUser(n);
	if (SD.getEps()==0.0) {
		return false;
	}
	else {
		if (SD.getnIter()==0) {
			return false;
		}
		else {
			if (SD.getStepSize()==0.0){
			return false;
			}
		}
	}
	return true;
}


}

