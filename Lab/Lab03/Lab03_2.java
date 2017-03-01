/**
 * file: Lab03_2.java
 * author: Anirudh Nagulapalli
 * course: MSCS630
 * Lab: Lab03
 * due date: Mar 01, 2017
 * version: 1.8
 *
 * This file containes a logic to perform
 * Encryption using a method similar to
 * Hill Cryptosystem
 *
 */


import java.util.Scanner;

public class Lab03_2{
  static String[][] encrypt = new String[4][4];
  static String[][] result = new String[4][4];
  public static void main(String[] args){
    Character c;
    String s = "";
    int len, i, j, k, a=0, q, r;
    System.out.println("Enter Substitution Character.");
    Scanner sc = new Scanner(System.in);
    c = sc.next().charAt(0);                    //Substitution Character
    //System.out.println("Character is " +c);
    System.out.println("Enter Plain text");
    sc = new Scanner(System.in);
    s = sc.nextLine();                          //String
    //System.out.println("String is " +s);
    len = s.length();
    //System.out.println(len);

    // q = len/16;                                        // Tried
    // while(true){                                       // in
    //   String P = s.substring(a,a+16);                  // an
    //   getHexMatP(c, P);                                // other
    //   //System.out.println(getHexMatP(c, P));          // way
    //   result = getHexMatP(c, P);                       // but
    //   for(i=0; i<4; i++){                              // resulted
    //     for(j=0; j<4; j++){                            // in
    //       System.out.print(result[i][j] + " ");        // IndexOutOfBound
    //     }                                              // Error
    //     System.out.println();
    //   }                                                //Small correction to be made
    //   //s = s.substring(16);
    //   System.out.println();
    //   a = a+16;
    //   if(a<len){
    //     continue;
    //   }
    // }
    // if(len%16!=0){
    //   String P = s.substring(q*16);
    //   while(P.length()<16){
    //     P = P + c;
    //   }
    //   getHexMatP(c, P);
    //   //System.out.println(getHexMatP(c, s));
    //   result = getHexMatP(c, P);
    //   for(i=0; i<4; i++){
    //     for(j=0; j<4; j++){
    //       System.out.print(result[i][j] + " ");
    //     }
    //     System.out.println();
    //   }
    // }

    q = len/16;
    r = len%16;
    if(r==0){                                   //For Strings which doesn't need padding
      for(i=0; i<q; i++){
        String P = s.substring(a,a+16);         //Substrings of length 16 each
        getHexMatP(c, P);
        //System.out.println(getHexMatP(c, P));
        result = getHexMatP(c, P);
        for(k=0; k<4; k++){
          for(j=0; j<4; j++){
            System.out.print(result[k][j] + " ");
          }
          System.out.println();
        }
        a+=16;                                  //Iterate for next subsstring
      }
    }
    else{                                       //For Strings which need padding
      for(i=0; i<q; i++){
        String P = s.substring(a,a+16);         //Takes complete filled strings till available
        getHexMatP(c, P);
        //System.out.println(getHexMatP(c, P));
        result = getHexMatP(c, P);
        for(k=0; k<4; k++){
          for(j=0; j<4; j++){
            System.out.print(result[k][j] + " ");
          }
          System.out.println();
        }
        System.out.println();
        //s = s.substring(16);
        a+=16;
      }

      String P = s.substring(q*16);             //Takes last substring
      while(P.length()<16){
        P = P + c;                              //Pads with Substitution Character
      }
      getHexMatP(c, P);
      //System.out.println(getHexMatP(c, s));
      result = getHexMatP(c, P);
      for(i=0; i<4; i++){
        for(j=0; j<4; j++){
          System.out.print(result[i][j] + " ");
        }
        System.out.println();
      }
    }
  }

  /**
     * getHexMatP
     *
     * This function encrypts Plain text into Cypher text and stores them in a matrix
     *
     * Parameters:
     *   s: Susbstituition Character
     *   p: Plain text
     *
     * Return value: Cypher text in 4X4 matrices
     */

  public static String[][] getHexMatP(char s, String p){
    //Charater[] a = new Charater a[p.len];
    int i, j=0, k=0;
    int[] p1 = new int[p.length()];
    String[] p2 = new String[p.length()];
    for(i=0; i<p.length() ;i++){
      p1[i] = (int) p.charAt(i);                          //Change Characters to Integers
      p2[i] = Integer.toHexString(p1[i]).toUpperCase();   //Change Integers to Hexadecimal String values
    }

    i=0;
    while(j<4){
      while(k<4){
        encrypt[k][j] = p2[i];                            //Put values column-wise
        i++;
        k++;
      }
      k=0;
      j++;
    }

    return encrypt;
  }
}
