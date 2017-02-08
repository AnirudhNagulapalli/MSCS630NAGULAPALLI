/**
 * file: Lab02_1.java
 * author: Anirudh Nagulapalli
 * course: MSCS630
 * Lab: Lab02
 * due date: Feb 08, 2017
 * version: 1.8
 *
 * This file contains logic to implement
 * the Euclid Algorithm
 *
 */


import java.util.Scanner;

public class Lab02_1{
  static int input[] = new int[50];

  public static void main(String[] args){
    int i=0, j;
    System.out.println("Enter values with spaces and Press control+d once done");    //Instruction to User

    Scanner sc = new Scanner(System.in);
    while(sc.hasNext()){
      input[i] = sc.nextInt();    //Takes Input
      i++;
    }

    for(j=0; j<i; j+=2){
      euclidAlg(input[j], input[j+1]);    //Takes Successive two numbers
    }
  }

  /**
     * euclidAlg
     *
     * This function implements the Euclid Algorithm
     *
     * Parameters:
     *   a: dividend
     *   b: divisor
     *
     * Return value: gcd of 'a' and 'b'
     */

  public static void euclidAlg(long a, long b){    //function
    long r, q;
    q = a/b;    //Quotient
    r = a%b;    //Remainder
    if(r==0){    //Base Condition
      System.out.println(b);    //Output
    }
    else{
      a = b;    //New value of Dividend
      b = r;    //New value of Divisor
      euclidAlg(a, b);    //Recursive function
    }
  }
}
