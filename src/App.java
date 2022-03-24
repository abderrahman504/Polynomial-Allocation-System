import linkedLists.*;

/*
Tasks the app will perform:
● Read in a polynomial and store it in variable A, B, or C.
● Output a polynomial using a form that clearly displays it.
● Add two polynomials and store the result in R.
● Subtract two polynomials and store the result in R.
● Multiply two polynomials and store the result in R.
● Evaluate a polynomial at some point, a, where a is a floating point constant. In other
words, substitute by the given value in your polynomial. Display the result as a floating
point.
● Clear a polynomial. Note that: a polynomial whose value is cleared or initially unset
cannot be involved in an operation.
It is required to implement the following interface by the core of your application. The core
of the application should throw a runtime exception when it encounter any invalid input
or operation.
*/
interface IPolynomialSolver {
    /**
    * Set polynomial terms (coefficients & exponents)
    * @param poly: name of the polynomial
    * @param terms: array of [coefficients][exponents]
    */
    void setPolynomial(char poly, int[][] terms);
    /**
    * Print the polynomial in ordered human readable representation
    * @param poly: name of the polynomial
    * @return: polynomial in the form like 27x^2+x-1
    */
    String print(char poly);
    /**
    * Clear the polynomial
    * @param poly: name of the polynomial
    */
    void clearPolynomial(char poly);
    /**
    * Evaluate the polynomial
    * @param poly: name of the polynomial
    * @param value: the polynomial constant value
    * @return the value of the polynomial
    */
    float evaluatePolynomial(char poly, float value);
    /**
    * Add two polynomials
    * @param poly1: first polynomial
    * @param poly2: second polynomial
    * @return the result polynomial
    */
    int[][] add(char poly1, char poly2);
    /**
    * Subtract two polynomials
    * @param poly1: first polynomial
    * @param poly2: second polynomial
    * @return the result polynomial
    */
    int[][] subtract(char poly1, char poly2);
    /**
    * Multiply two polynomials
    * @param poly1: first polynomial
    * @param poly2: second polynomial
    * @return: the result polynomial
    */
    int[][] multiply(char poly1, char poly2);

}


public class App {
    public static void main(String[] args)
    {
        
    }
}
