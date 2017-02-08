/**
 * file: Lab01.java
 * author: Anirudh Nagulapalli
 * course: MSCS630
 * Lab: Lab02
 * due date: Feb 07, 2017
 * version: 1.8
 *
 *
 *
 *
 */


import java.util.Scanner;

public class Lab02_1{
  static int h, i, j;
  static int input[][] = new int[50][3];
  static Long inputLong[] = new Long[50];

  public static void main(String[] args){

    System.out.println("Enter number of lines of the input");
    Scanner sc = new Scanner(System.in);
    int lines = sc.nextInt();


    for(j=1; j<=lines; j++){
      System.out.println("Enter your input for line 1");    //Asks for input for specific line
      Scanner s = new Scanner(System.in);
      int i = 1;
      //input[i] = s.nextLine();
      //i++;
      while(s.hasNext()){
        if(i<lines){
          //System.out.println(i);
          //System.out.println(lines);
          System.out.println("Enter your input for line " +(i+1));
          input[j][i] = s.nextInt();
          i++;
        }
        else if(i==lines){
          //System.out.println(i);
          //System.out.println(lines);
          //System.out.println("Enter your last input for line " +(i+1));
          input[j][i] = s.nextInt();
          i++;
          break;
        }
      }
      //else{
        //break;
      //}

    }

    System.out.println("The entered Input is");
    for(i=1; i<=lines; i++){
      System.out.println(input[i]);
    }

    //for(i=1; i<=lines; i++){
      //inputLong[i] = (long)input[i];
    //}

    for(i=1; i<=lines; i++){
      System.out.println(inputLong[i]);
      System.out.println(i);
      System.out.println(lines);
    }
    System.out.println(input.getClass());
    System.out.println(inputLong.getClass());
    //str2int(input, lines, array1, array2);
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

  /*public static int[] str2int(String[] input, int lines, char[] array1, char[] array2){    //function
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
  }*/
}
