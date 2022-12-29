import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
	//Initializing a public BufferedReader object; the only BufferedReader object in the entire program
	public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static boolean was_run;
	//creating the main method
	public static void main(String[] args) throws IOException{
		SteepestDescent SD= new SteepestDescent();
		//Global Polynomial object
		Polynomial poly= new Polynomial();
		SDGSS SDG= new SDGSS();
		SDArmijo SDA= new SDArmijo();
		SDFixed SDF= new SDFixed();
		was_run=false;
		String output=" ";
		//Creating an Arraylist of polynomial objects called "polynomials"
		ArrayList<Polynomial> polynomial_array = new ArrayList<Polynomial>();
		do {
			displayMenu();
			output= reader.readLine().toUpperCase();
	
			if (output.equals("L")) {
				//calling the load method
				loadPolynomialFile(polynomial_array);
			}
			//remains same
			else if (output.equals("F")) {
				//check if the arraylist is empty
				if (polynomial_array.isEmpty()) {
					System.out.println("\nERROR: No polynomial functions are loaded!\n");
				}
				else {
					printPolynomials(polynomial_array);
				}
			}
			//Done
			else if (output.equals("C")) {
	            polynomial_array.clear();
	            SDF.setHasResults(false);
				SDA.setHasResults(false);
				SDG.setHasResults(false);
				was_run=false;
	            System.out.println("\nAll polynomials cleared.\n");
	        }
	
			//Check
			else if (output.equals("S")) {
				// Set steepest descent parameters
				//using the getAllParams method
				//if we are setting parameters we want was_run to be false
				was_run=false;
				getAllParams(SDF, SDA, SDG);
			}
	
			//check
			else if (output.equals("P")) {
				printAllParams(SDF, SDA, SDG);
			}
			//check
			else if (output.equals("R")) {
				//calling the method to run the steepest descent algorithm
				if (polynomial_array.isEmpty()) {
					System.out.println("\nERROR: No polynomial functions are loaded!\n");
				}
				else {
					was_run=true;
					SD.init(polynomial_array);
					System.out.println("\nRunning SD with a fixed line search:");
					SDF.init(polynomial_array);
					for (int i=0; i<polynomial_array.size(); i++) {
						SDF.run(i,polynomial_array.get(i));
					}
					System.out.println("\nRunning SD with an Armijo line search:");
					SDA.init(polynomial_array);
					for (int j = 0; j < polynomial_array.size(); j++) {
						SDA.run(j, polynomial_array.get(j));
					}
					System.out.println("\nRunning SD with a golden section line search:");
					SDG.init(polynomial_array);
					for (int k = 0; k < polynomial_array.size(); k++) {
						SDG.run(k, polynomial_array.get(k));
					}
					System.out.println("\nAll polynomials done.\n");
				}
			}
			//check
			else if (output.equals("D")) {
				//Display algorithm performance for all 3 files
				//Uses the printall method in SteepestDescent
				printAllResults(SDF, SDA, SDG, polynomial_array);
				System.out.println(" ");
			}
			
			else if (output.equals("X")) {
				if (!SDA.hasResults() || !SDG.hasResults()) {
					System.out.println("\nERROR: Results do not exist for all line searches!\n");
				}
				else {
					compare(SDF, SDA, SDG);
				}
			}
	
	
			//Quit
			else {
				if (!(output.equals("Q"))){
					System.out.println("\nERROR: Invalid menu choice!\n");
				}
			}

		}while (!output.equals("Q"));
		
		System.out.println("\nArrivederci.");
		}
	
	


	//Display Menu
	public static void displayMenu() {
		System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
		System.out.println("L - Load polynomials from file");
		System.out.println("F - View polynomial functions");
		System.out.println("C - Clear polynomial functions");
		System.out.println("S - Set steepest descent parameters");
		System.out.println("P - View steepest descent parameters");
		System.out.println("R - Run steepest descent algorithm");
		System.out.println("D - Display algorithm performance");
		System.out.println("X - Compare average algorithm performance");
		System.out.println("Q - Quit");
		System.out.print("\nEnter choice: ");

	}
	//Loads the polynomial function details from a user-specified file.
		public static boolean loadPolynomialFile(ArrayList<Polynomial> P) throws IOException{
			System.out.print("\nEnter file name (0 to cancel): ");
			//Get the file name
			String file_name = reader.readLine();
			//If the user entered 0, return false
			if (file_name.equals("0")) {
				System.out.println("\nFile loading process canceled.\n");
				return false;
			}
			//Checking if the file exists
			File file = new File(file_name);
			if (!file.exists()) {
				System.out.println("\nERROR: File not found!\n");
				return false;
			}
			//If the file exists, read the file
			BufferedReader file_reader = new BufferedReader(new FileReader(file));
			//create a string 'line' variable that will later read each lines of the file
			String line;
			boolean flag= true;
			
			//Initializing count used in the while loop
			int count=1;
			//number of polynomials
			int number_polynomials=P.size();
			//This loop will loop over the entire file 
			do {
				line = file_reader.readLine();
				//checking if the line is null
				if (line!=null) {
					//following code only runs when line is not null
					Polynomial new_polynomial = new Polynomial();
					
					//line_list is an arraylist, which will store arrays of each line(the lines between 2 stars) 
					ArrayList<double[]> line_list= new ArrayList<double[]>();
					
					//the coefficients read on each line of the file are stored on the line_array 
					double[] line_array;
					String[] coefficients;
					//If line is not "*"; '*' indicates the end of one polynomial and the beginning of another
					while (line.charAt(0)!='*') {
						
						if (flag==true) {
							//Split the line into an array coefficients
							//line.split creates a string
							coefficients = line.split(",");
							
							//new_polynomial.getDegree ==0 means that the line is the first line of polynomial;
							//hence we initialize the new_polynomial values
							if (new_polynomial.getDegree() == 0) {
								//degree is # of coefficients -1 (ex.degree 3 means 4 coefficents)
								new_polynomial.setDegree((coefficients.length)- 1);
								
							}
							
							//coefficients array is the array that has been created by using split function the current file line
							//get_degree value was initialized from the first polynomial variable
							//In our case if every line is same length, coefficient array length matches get_degree+1
							if ((coefficients.length == new_polynomial.getDegree() + 1)&&(flag=true)) {
								//creating an arrays to store the coefficients in each line; the length of the array is the
								// same as the coefficients array
								line_array = new double[coefficients.length];
								for (int i = 0; i < coefficients.length; i++) {
									Double coefficient= (double) Double.parseDouble(coefficients[i]);
									//adding the coefficients converted to double in to the line array
									line_array[i] = coefficient;
									}
								//once all the coefficients are added to array the array is added to line_list
								line_list.add(line_array);
								flag=true;
							}
							else{
								System.out.println("\nERROR: Inconsistent dimensions in polynomial " + (count) + "!");
								flag=false;
							}
						}		
						//read the next line
						line = file_reader.readLine();
						//The next line could be null
						if (line==null) {
							break;
						}
					}
					//while loop ended hence null line was reached or star was reached
					//put many parameters to avoid polynomial with inconsistent length being added
					 if ((flag)&& (line_list.size()>0)){
						 //initializing the correct size of new_polynomial.setCoef
						 new_polynomial.setN((line_list.size()));
						 new_polynomial.init();
						 for (int i = 0; i < (line_list.size()); ++i) {
							 for (int j = 0; j < line_list.get(i).length; ++j) {
								 new_polynomial.setCoef(i, j, line_list.get(i)[j]);
							}
					    }
						P.add(new_polynomial);
					 }
					 //count keeps track of number of polynomials; its used in the error message for inconsistent polynomials
					 count=count+1;
					 flag=true;
					}//end of if statement on top that checks in the do while loop if flag=true
				}while (line != null);
				//if we load more than one file it should only print out the number of polynomials in the new file
				System.out.println("\n" + (P.size()-number_polynomials) + " polynomials loaded!\n");
				file_reader.close();
				return true;
			}
			
	
	//ask
	//Get algorithm parameters from the user for each algorithm variation.
	public static void getAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG ){
			System.out.println("");
			if (!SDF.getParamsUser()) {
				System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
				//if SDF is not set then was_run is true
				was_run=true;
			}
			if (!SDA.getParamsUser()) {
				System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			}
			if (!SDG.getParamsUser()) {
				System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n");
			}
		}


	//check
	public static void printAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG){
		System.out.print("\nSD with a fixed line search:");
		SDF.print();
		System.out.print("\nSD with an Armijo line search:");
		SDA.print();
		System.out.print("\nSD with a golden section line search:");
		SDG.print();
		System.out.println();
	}

	// function prints polynomial details-->remains same(option F)
	public static void printPolynomials(ArrayList<Polynomial> P){
		System.out.println();
		System.out.println("---------------------------------------------------------");
		System.out.println("Poly No.  Degree   # vars   Function");
		System.out.println("---------------------------------------------------------");
		for (int i = 0; i < P.size(); ++i) {
			System.out.format("%8d%8d%9d   ", i + 1, P.get(i).getDegree(), P.get(i).getN());
			P.get(i).print();
		} 
		System.out.println();
		}

	//Function creates a table comparing the results of the three
	//fix
	public static void compare(SDFixed SDF, SDArmijo SDA, SDGSS SDG){
			Statistics stats = new Statistics();
			System.out.println();
			System.out.println("---------------------------------------------------");
			System.out.println("          norm(grad)       # iter    Comp time (ms)");
			System.out.println("---------------------------------------------------");
			System.out.format("%-7s%13.3f%13.3f%18.3f\n", "Fixed", stats.average_double(SDF.getBestGradNorm()),
					stats.average_int(SDF.getNIter()), stats.average_long(SDF.getCompTime()));
			System.out.format("%-7s%13.3f%13.3f%18.3f\n", "Armijo", stats.average_double(SDA.getBestGradNorm()),
					stats.average_int(SDA.getNIter()), stats.average_long(SDA.getCompTime()));
			System.out.format("%-7s%13.3f%13.3f%18.3f\n", "GSS", stats.average_double(SDG.getBestGradNorm()),
					stats.average_int(SDG.getNIter()), stats.average_long(SDG.getCompTime()));
			System.out.println("---------------------------------------------------");
			//Compare and find the best algorithm
			String norm_grad_winner;
			String iter_winner;
			String comp_time_winner;
			//If and else if used to compare and find norm_grad_winner
			if (stats.average_double(SDF.getBestGradNorm()) < stats.average_double(SDA.getBestGradNorm() ) && stats.average_double(SDF.getBestGradNorm()) < stats.average_double(SDG.getBestGradNorm())) {
				norm_grad_winner = "Fixed";
			} else if (stats.average_double(SDA.getBestGradNorm()) < stats.average_double(SDF.getBestGradNorm()) && stats.average_double(SDA.getBestGradNorm()) < stats.average_double(SDG.getBestGradNorm())) {
				norm_grad_winner = "Armijo";
			} else {
				norm_grad_winner = "GSS";
			}
			//If and else if used to compare and find iter_winner
			if (stats.average_int(SDF.getNIter()) < stats.average_int(SDA.getNIter()) && stats.average_int(SDF.getNIter()) < stats.average_int(SDG.getNIter())) {
				iter_winner = "Fixed";
			} else if (stats.average_int(SDA.getNIter()) < stats.average_int(SDF.getNIter()) && stats.average_int(SDA.getNIter()) < stats.average_int(SDG.getNIter())) {
				iter_winner = "Armijo";
			} else {
				iter_winner = "GSS";
			}
			//If and else if used to compare and find comp_time_winner
			if (stats.average_long(SDF.getCompTime()) < stats.average_long(SDA.getCompTime()) && stats.average_long(SDF.getCompTime()) < stats.average_long(SDG.getCompTime())) {
				comp_time_winner = "Fixed";
			} else if (stats.average_long(SDA.getCompTime()) < stats.average_long(SDF.getCompTime()) && stats.average_long(SDA.getCompTime()) < stats.average_long(SDG.getCompTime())) {
				comp_time_winner = "Armijo";
			} else {
				comp_time_winner = "GSS";
			}
			//Print the best algorithm
			//Printing the winners for each category
			System.out.format("%-7s%13s%13s%18s\n", "Winner",norm_grad_winner , iter_winner, comp_time_winner);
			System.out.println("---------------------------------------------------");
			//Overall winner is the one that is the winner in all three categories
			if (norm_grad_winner.equals(iter_winner) && norm_grad_winner.equals(comp_time_winner)) {
				System.out.println("Overall winner: " + norm_grad_winner);
			} else {
				System.out.println("Overall winner: Unclear");
			}
			System.out.println();

		}

	//Detailed results and statistics summaries for all algorithm variations(used in option D)
	//Uses the printall and printstats functions from SteepestDescent inherited by all the classes
	//Prints the tables
	public static void printAllResults(SDFixed SDF, SDArmijo SDA, SDGSS SDG, ArrayList<Polynomial> P){
		 if (SDA.hasResults() && was_run && P.size() > 0) {	
		//if (SDF.hasResults() && SDA.hasResults() && SDG.hasResults() && P.size() > 0) {
				System.out.println("\nDetailed results for SD with a fixed line search:");
				SDF.printAll();
				System.out.println("\nStatistical summary for SD with a fixed line search:");
				SDF.printStats();
				System.out.println("\nDetailed results for SD with an Armijo line search:");
				SDA.printAll();
				System.out.println("\nStatistical summary for SD with an Armijo line search:");
				SDA.printStats();
				System.out.println("\nDetailed results for SD with a golden section line search:");
				SDG.printAll();
				System.out.println("\nStatistical summary for SD with a golden section line search:");
				SDG.printStats();

				}
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
	public static double getDouble(String prompt, Double LB, Double UB) {
		double input=0.00;
		int valid=0;
		//Creating error messages
		String error_message;
		if (UB==Double.MAX_VALUE){
			error_message=String.format("ERROR: Input must be a real number in [%.2f, infinity]!\n",LB);
		}
		else if (LB==Double.MIN_VALUE){
			error_message=String.format("ERROR: Input must be a real number in [-infinity, %.2f]!\n",UB);
		}
		else if ((LB==Double.MIN_VALUE) && (UB==Double.MAX_VALUE)){
			error_message=String.format("ERROR: Input must be a real number in [-infinity, infinity]!\n");
		}
		else{
			error_message=String.format("ERROR: Input must be a real number in [%.2f, %.2f]!\n",LB,UB);
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

}


