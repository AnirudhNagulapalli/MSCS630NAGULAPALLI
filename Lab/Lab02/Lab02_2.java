/**
 * file: Lab02_2.java
 * author: Anirudh Nagulapalli
 * course: MSCS630
 * Lab: Lab02
 * due date: Feb 08, 2017
 * version: 1.8
 *
 * This file contains logic to implement
 * the Extended Euclid Algorithm
 *
 */


import java.util.Scanner;

public class Lab02_2{
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
      euclidAlgExt(input[j], input[j+1]);    //Takes Successive two numbers
    }
  }

  /**
     * euclidAlgExt
     *
     * This function implements the Extended Euclid Algorithm
     *
     * Parameters:
     *   a: dividend
     *   b: divisor
     *
     * Return value: d = gcd of 'a' and 'b', x, y values which satisfy the equation 'd = ax + by'
     */

  public static void euclidAlgExt(long a, long b){    //function
    long r, q;
    int i;
    long U[] = new long[3];    //for storing U[] = [a, 1, 0]
    long V[] = new long[3];    //for storing V[] = [b, 0, 1]
    long W[] = new long[3];    //for using formula W = U - floor(U(1)/V(1))*V
    U[0] = a; U[1] = 1; U[2] = 0;    //Initializing U
    V[0] = b; V[1] = 0; V[2] = 1;    //Initializing V

    while(V[0]>0){
      for(i=0; i<3; i++){
        W[i] = U[i] - (long)Math.floor((U[0]/V[0]))*V[i];;
      }
      for(i=0; i<3; i++){
        U[i] = V[i];
        V[i] = W[i];
      }
    }

    for(i=0; i<3; i++){
      System.out.print(U[i] +" ");    //Output
    }
    System.out.println();
  }
}
