/**
 * file: Lab01.java
 * author: Anirudh Nagulapalli
 * course: MSCS630
 * Lab: Lab01
 * due date: Jan 25, 2017
 * version: 1.8
 *
 * This file contains the logic to convert plain
 * text to cyphertext where text is converted
 * to numbers
 */


import java.util.Scanner;

public class Lab01_Final{
  static int h, i, j;
  static String input[] = new String[50];
  static char array1[] = new char[27];
  static char array2[] = new char[27];
  public static void main(String[] args){

    System.out.println("Enter number of lines of the input");
    Scanner sc = new Scanner(System.in);
    int lines = sc.nextInt();

    for(i=0; i<lines; i++){
      System.out.println("Enter your input for " +(i+1) +" line");    //Asks for input for specific line
      Scanner s = new Scanner(System.in);
      input[i] = s.nextLine();
    }

    for(i=0; i<26; i++){
      array1[i] = (char)(97+i);    //Array1 stores a-z in 0-25 positions
    }


    for(i=0; i<26; i++){
      array2[i] = (char)(65+i);    //Array2 stores A-Z in 0-25 positions
    }

    array1[26] = array2[26] = (char)32;    //Space is in 26th position
    System.out.println(array1);
    System.out.println(array2);
    str2int(input, lines, array1, array2);
  }

  //System.out.println(input);

  /**
     * str2int
     *
     * This function computes the string to integer array conversion
     *
     * Parameters:
     *   input: the number on which to compute the factorial
     *   lines: the number of lines given by the user as input
     *
     * Return value: the output in the form of respective numbers
     */

  public static int[] str2int(String[] input, int lines, char[] array1, char[] array2){    //function
    int[] output = new int[50];
    for(int h=0; h<lines; h++){
      for(int i=0; i<input[h].length(); i++){
        for(int j=0; j<27; j++){
          if(input[h].charAt(i) == array1[j] || input[h].charAt(i) == array2[j]){    //Matches input with position number
            output[i] = j;
            System.out.print(j + " ");
          }
        }
      }
      System.out.println();
    }
    return output;
  }
}
