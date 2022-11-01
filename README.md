# Polynomial-local-minimizer
Optimization of mathematical function is a core component of both operations research and machine learning.
“Optimization” means finding the global minimum or maximum of a function. However, since polynomials can
have any number of variables, and those variables can be multiplied together in any combination, hence in the real world, 
we are often happy to accept a local minimum as a solution when the objective
function is too difficult to globally optimize.  

In this project, I will implement the steepest descent algorithm to find local minima of polynomials of the
form SimplePolynomialSum. l will display the performance of the algorithm for the following metrics:
        • Final objective function value
        • Closeness to local minimum (`2 norm of the gradient at the final solution)
        • Number of iterations needed for the algorithm to run
        • Computation time needed for the algorithm to run

The main class is where my program runs, however the main class inherits from  Polynomial and SteepestDescent class :
                                                                                          1. Polynomial class stores all the polynomial data
                                                                                          2. SteepestDescent class runs the steepest descent algorithm
