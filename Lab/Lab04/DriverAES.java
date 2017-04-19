/**
 * file: DriverAES.java
 * author: Anirudh Nagulapalli
 * course: MSCS630
 * Lab: Lab04
 * due date: Apr 05, 2017
 * version: 1.8
 *
 * This file contains a logic to
 * find if the input in the form
 * of a string of 32 characters
 */


import java.util.Scanner;

public class DriverAES{
  static String inHex;

  public static void main(String[] args){
    AESCipher Cipher = new AESCipher();
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the input in the form of a string");
    inHex = sc.next();
    if(inHex.length() == 32){   //Checks if the input is of 32 characters exact
      Cipher.aesRoundKeys(inHex);
    }
    else{
      System.out.println("Enter a valid key");
    }
  }
}
