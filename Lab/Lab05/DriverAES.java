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
    static String inputKey = "";
  static String inputText = "";

  public static void main(String[] args){
    AESCipher Cipher = new AESCipher();
    Scanner sc = new Scanner(System.in);

    System.out.println("Enter the input in the form of a string for Key");
    if (sc.hasNext()){
      inputKey = sc.nextLine();
    }
    else {
      System.out.println("Key not found");
    }

    System.out.println("Enter the input in the form of a string for Text");
    if (sc.hasNext()){
      inputText = sc.nextLine();
    }
    else{
      System.out.println("Text not found");
    }

    //Checks if both the inputs are of 32 characters exact
    if(inputKey.length() == 32){
      if(inputText.length() == 32){

        Cipher.aes(inputKey, inputText);

      }
    }
    else{
      System.out.println("Enter a valid key");
    }
  }
}
