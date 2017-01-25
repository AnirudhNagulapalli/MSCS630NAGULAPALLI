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

public class Lab01{
  public static void main(String[] args){
    int h, i, j;
    String input[] = new String[50];
    char array1[] = new char[27];
    for(i=0; i<26; i++){
      array1[i] = (char)(97+i);    //Array1 stores a-z in 0-25 positions
    }

    char array2[] = new char[27];
    for(i=0; i<26; i++){
      array2[i] = (char)(65+i);    //Array2 stores A-Z in 0-25 positions
    }

    array1[26] = array2[26] = (char)32;    //Space is in 26th position

    System.out.println("Enter number of lines of the input");
    Scanner sc = new Scanner(System.in);
    int lines = sc.nextInt();

    for(i=0; i<lines; i++){
      System.out.println("Enter your input for " +(i+1) +" line");    //Asks for input for specific line
      input[i] = sc.next();
    }

    //System.out.println(input);

    for(h=0; h<lines; h++){
      for(i=0; i<input[h].length(); i++){
        for(j=0; j<27; j++){
          if(input[h].charAt(i) == array1[j] || input[h].charAt(i) == array2[j]){    //Matches input with position number
            System.out.print(j + " ");
          }
        }
      }
      System.out.println();
    }
  }
}
