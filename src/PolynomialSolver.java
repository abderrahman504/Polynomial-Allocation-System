import linkedLists.SingleLinkedList;
import java.util.Scanner;

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


public class PolynomialSolver implements IPolynomialSolver
{
    static int[][] A, B, C, R;
    static Scanner scn;


    static class PolyTerm
    {
        int exponent;
        int coefficient; 

        PolyTerm(int coefficient, int exponent)
        {
            this.exponent = exponent;
            this.coefficient = coefficient;
        }
    }


    public static void main(String[] args)
    {
        scn = new Scanner(System.in);
        while (scn.hasNextLine())
        {
            String command = scn.nextLine();
            String output = process_command(command);
            if (output != null) System.out.println(output);
        }
        scn.close();
        

    }


    static String process_command(String command)
    {//Will change this method.
        char polyName1, polyName2;
        String output = "";
        switch(command)
        {
            case "set":
            polyName1 = scn.nextLine().charAt(0);
            String rawInput = scn.nextLine();
            SingleLinkedList polynomial;
            if (rawInput.equals("[]"))
            {
                int[][] x = selectPoly(polyName1);
                x = null;
                output = null;
                break;
            }
            polynomial = take_polynomial(rawInput);
            new PolynomialSolver().setPolynomial(polyName1, list_to_array(polynomial));
            output = null;
            break;

            case "print":
            polyName1 = scn.nextLine().charAt(0);
            output = is_poly_valid(polyName1) ? new PolynomialSolver().print(polyName1): "Error";
            break;
            
            case "add":
            polyName1 = (scn.nextLine()).charAt(0);
            polyName2 = (scn.nextLine()).charAt(0);
            if (is_poly_valid(polyName1) && is_poly_valid(polyName2))
            {
                R = new PolynomialSolver().add(polyName1, polyName2);
                output = new PolynomialSolver().print('R');
                    
            }
            else output = "Error";
            break;
            
            case "sub":
            polyName1 = (scn.nextLine()).charAt(0);
            polyName2 = (scn.nextLine()).charAt(0);
            if (is_poly_valid(polyName1) && is_poly_valid(polyName2))
            {
                R = new PolynomialSolver().subtract(polyName1, polyName2);
                output = new PolynomialSolver().print('R');
                    
            }
            else output = "Error";
            break;
            
            case "mult":
            polyName1 = (scn.nextLine()).charAt(0);
            polyName2 = (scn.nextLine()).charAt(0);
            if (is_poly_valid(polyName1) && is_poly_valid(polyName2))
            {
                R = new PolynomialSolver().multiply(polyName1, polyName2);
                output = new PolynomialSolver().print('R');
                    
            }
            else output = "Error";
            break;
            
            case "clear":
            polyName1 = (scn.nextLine()).charAt(0);
            new PolynomialSolver().clearPolynomial(polyName1);
            output = "[]";
            break;
            
            case "eval":
            polyName1 = (scn.nextLine()).charAt(0);
            float value = scn.nextFloat();
            if (is_poly_valid(polyName1))
            {
            float result = new PolynomialSolver().evaluatePolynomial(polyName1, value);
            output = (result - (int) result) != 0 ? String.valueOf(result) : String.valueOf((int) result);
            }
            else output = "Error";
            break;

        }

        return output;
        
    }

    

    public void setPolynomial(char poly, int[][] terms)
    {
        switch(poly) 
        {
            case 'A':
                A = terms;
                break;
            case 'B':
                B = terms;
                break;
            case 'C':
                C = terms;
                break;
        }
    }
  

    public String print(char poly)
    {
        String x ="";
        int [][] terms = selectPoly(poly);
        if (terms[0].length == 0) return "";

        SingleLinkedList pol = array_to_list(terms);
        clean_poly(pol);
        PolyTerm currentTerm = (PolyTerm) pol.get(0);
        String coeff = currentTerm.coefficient == 1 ? "" : String.valueOf(currentTerm.coefficient);
        x += coeff;
        if (currentTerm.exponent > 1)
        {
            x += "x^" + currentTerm.exponent;
        }
        else if (currentTerm.exponent == 1)
        {
            x += "x";
        }
        for (int i = 1; i < pol.size(); i++)
        {
            
            currentTerm = (PolyTerm) pol.get(i);
            String sign = currentTerm.coefficient > 0 ? "+" : "";
            coeff = currentTerm.coefficient == 1 ? "" : String.valueOf(currentTerm.coefficient);
            x += sign + coeff;
            if (currentTerm.exponent > 1)
            {
                x += "x^" + currentTerm.exponent;
            }
            else if (currentTerm.exponent == 1)
            {
                x += "x";
            }
        }
        return x;
    }


    public void clearPolynomial(char poly)
    {
        switch(poly) {
            case 'A':
            A = null;
            break;
            case 'B':
            B = null;
            break;
            case 'C':
            C = null;
            break;
            }
    }
  

    public float evaluatePolynomial(char poly, float value)
    {
        int [][]result = selectPoly(poly) ;
         
        int x = 0;
        for (int i = 0; i<result[0].length;i++) {
            if(result[1][i] == 0) {
                x += result[0][i];
            }
            else 
            {
                x += result[0][i]* Math.pow(value, result[1][i]);
            }
        } 
        
        return x;
    }


    public int[][] add(char poly1, char poly2)
    {
        int[][] arr1 = selectPoly(poly1);
        int[][] arr2 = selectPoly(poly2);
        SingleLinkedList pol1, pol2, result = new SingleLinkedList();
        pol1 = array_to_list(arr1);
        pol2 = array_to_list(arr2);
        
        while (!pol1.isEmpty())
        {
            PolyTerm newTerm = (PolyTerm) pol1.get(0);
            pol1.remove(0);
            for (int j=0; j < pol2.size(); j++)
            {
                PolyTerm t2;
                t2 = (PolyTerm) pol2.get(j);
                if (newTerm.exponent == t2.exponent)
                {
                    pol2.remove(j);
                    j--;
                    newTerm.coefficient += t2.coefficient;
                    break;
                }
            }
            result.add(newTerm);
        }
        while (!pol2.isEmpty())
        {
            result.add(pol2.get(0));
            pol2.remove(0);
        }
        result = sort_polynomial(result);
        return list_to_array(result);
        
    }
    

    public int[][] subtract(char poly1, char poly2)
    {
        int[][] arr1 = selectPoly(poly1);
        int[][] arr2 = selectPoly(poly2);
        SingleLinkedList pol1, pol2, result = new SingleLinkedList();
        pol1 = array_to_list(arr1);
        pol2 = array_to_list(arr2);
        
        while (!pol1.isEmpty())
        {
            PolyTerm newTerm = (PolyTerm) pol1.get(0);
            pol1.remove(0);
            for (int j=0; j < pol2.size(); j++)
            {
                PolyTerm t2;
                t2 = (PolyTerm) pol2.get(j);
                if (newTerm.exponent == t2.exponent)
                {
                    pol2.remove(j);
                    j--;
                    newTerm.coefficient -= t2.coefficient;
                    break;
                }
            }
            result.add(newTerm);
        }
        while (!pol2.isEmpty())
        {
            result.add(pol2.get(0));
            pol2.remove(0);
        }
        result = sort_polynomial(result);
        return list_to_array(result);
    }
    

    public int[][] multiply(char poly1, char poly2)
    {
        int[][] arr1 = selectPoly(poly1);
        int[][] arr2 = selectPoly(poly2);    
        SingleLinkedList pol1, pol2, result = new SingleLinkedList();
        pol1 = array_to_list(arr1);
        pol2 = array_to_list(arr2);

        for (int i=0; i< pol1.size(); i++)
        {
            PolyTerm t1 = (PolyTerm) pol1.get(i);
            for (int j=0; j < pol2.size(); j++)
            {
                PolyTerm t2 = (PolyTerm) pol2.get(j);
                PolyTerm newTerm = new PolyTerm(t1.coefficient * t2.coefficient, t1.exponent + t2.exponent);
                boolean expAlreadyExists = false;
                for (int k = 0; k < result.size(); k++)
                {
                    PolyTerm tr = (PolyTerm) result.get(k);
                    if (tr.exponent == newTerm.exponent)
                    {
                        expAlreadyExists = true;
                        tr.coefficient += newTerm.coefficient;
                        break;
                    }
                }
                if (!expAlreadyExists) result.add(newTerm);
            }
        }

        result = sort_polynomial(result);
        return list_to_array(result);
    }


    static SingleLinkedList take_polynomial(String rawInput)
    {
        SingleLinkedList polynomial = new SingleLinkedList();
        if (rawInput == "[]")
        {
            return polynomial;
        }
        String[] processedInput = (rawInput.replaceAll("\\[|\\]", "")).split(",");
        for (int i = 0; i < processedInput.length; i++)
        {
            PolyTerm term = new PolyTerm(Integer.parseInt(processedInput[i]),processedInput.length-i-1);
            polynomial.add(term);
        }
        clean_poly(polynomial);
        return polynomial;
    }
    
    static SingleLinkedList sort_polynomial(SingleLinkedList poly)
    {
        SingleLinkedList sorted = new SingleLinkedList();
        
        while (!poly.isEmpty())
        {
            PolyTerm highestExp = (PolyTerm) poly.get(0);
            int highestIndex = 0;
            for (int i=1; i<poly.size(); i++)
            {
                PolyTerm currentTerm = (PolyTerm) poly.get(i); 
                if (currentTerm.coefficient == 0) 
                {
                    poly.remove(i);
                    i--;
                    continue;
                }
                highestExp = currentTerm.exponent > highestExp.exponent ? currentTerm : highestExp;
                highestIndex = currentTerm.exponent > highestExp.exponent ? i : highestIndex;
            }
            sorted.add(highestExp);
            poly.remove(highestIndex);
        }
        
        return sorted;
    }

    static void clean_poly(SingleLinkedList poly)
    {
        for (int i = 0; i<poly.size(); i++)
        {
            PolyTerm term = (PolyTerm) poly.get(i);
            if (term.coefficient == 0) 
            {
                poly.remove(i);
                i--;
            }
        }
    }

    static SingleLinkedList array_to_list(int[][] terms)
    {
        SingleLinkedList result = new SingleLinkedList();
        if (terms == null) return result;
        for (int i=0; i< terms[0].length; i++)
        {
            PolyTerm term = new PolyTerm(terms[0][i], terms[1][i]);
            result.add(term);
        }

        return result;
    }

    static int[][] list_to_array(SingleLinkedList polynomial)
    {
        if (polynomial.size()== 0) return null;
        int[][] result = new int[2][polynomial.size()];
        for (int i=0; i<polynomial.size(); i++)
        {
            PolyTerm term = (PolyTerm) polynomial.get(i); 
            result[0][i] = term.coefficient;
            result[1][i] = term.exponent;
        }
        return result;
    }


    static int[][] selectPoly (char polyChar){
        
        switch(polyChar) 
        {
        case 'A':
        return A;

        case 'B':
        return B;

        case 'C':
        return C;

        case 'R':
        return R;
        }
        return null;
    }
    static boolean is_poly_valid(char poly)
    {
        switch (poly)
        {
            case 'A':
            return (A != null);
            case 'B':
            return (B != null);
            case 'C':
            return (C != null);
            case 'R':
            return (C != null);
        }
        return false;
    }
}


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


