/**
 * file: Lab03_1.java
 * author: Anirudh Nagulapalli
 * course: MSCS630
 * Lab: Lab03
 * due date: Mar 01, 2017
 * version: 1.8
 *
 * This file contains a logic to
 * find the determinant of matrices
 * and perform modulo operation
 */


import java.util.Scanner;

public class Lab03_1{
  static int inputModDim[] = new int[3];
  public static void main(String[] args){
    int i=0, j, k;
    System.out.println("Enter the value of m, the modulo under which all integer calculations will be performed, followed by a single space and n, the size of the matrix A");
    Scanner sc = new Scanner(System.in);
    //scanner sc = new Scanner(System.in);
    for (i=0; i<=1; i++){
      inputModDim[i] = sc.nextInt();          //inputModDim[0] = modulo value
    }                                         //inputModDim[1] = Order of the sqaure matrix

    System.out.println("Enter a " +inputModDim[1] +"X" +inputModDim[1] +" matrix"); //Takes a square matrix of order inputModDim[1] X inputModDim[1]
    int matrix[][] = new int[inputModDim[1]][inputModDim[1]];
    for(j=0; j<inputModDim[1]; j++){
      for(k=0; k<inputModDim[1]; k++){
        matrix[j][k] = sc.nextInt();
      }
    }
    System.out.println(cofModDet(inputModDim[0], matrix));   //Sends m, matrix
  }

  /**
     * cofModDet
     *
     * This function takes the determinant of matrices
     *
     * Parameters:
     *   m: modulo value
     *   A[][]: matrix
     *
     * Return value: Determinant in modulo m
     */

  public static int cofModDet(int m, int[][] A){
    int n=A.length, detA;
    if(n == 1){                               //if 1X1
      detA = A[0][0] % m;
      if(detA < 0){
        detA = detA + m;
      }
      return detA;
      //System.out.println(detA);
    }
    else if(n == 2){                          //if 2X2
      detA = ((A[0][0]*A[1][1] - A[0][1]*A[1][0]) % m);
      if(detA < 0){
        detA = detA + m;
      }
      return detA;
      //System.out.println(detA);
    }
    else{
      detA = 0;
      for(int Row1=0; Row1<n; Row1++){        //Iterates through first row elements
        int[][] a = new int[n-1][n-1];        //Submatrix of order [n-1 X n-1]
        for(int i=1; i<n; i++){
          int j2=0;
          for(int j=0; j<n; j++){
            if(j == Row1)                     //To neglect same value columns
              continue;
            a[i-1][j2] = A[i][j];             //Only takes the submatrix values
            j2++;
          }
        }
        detA += (Math.pow(-1.0,1.0+j1+1.0)* A[0][Row1] * cofModDet(m, a)) % m;    //Multiply First row elements with their submatrices and add all of them together
      }
      if(detA < 0){
        detA = detA + m;
      }
    }
    return detA;
  }
}
