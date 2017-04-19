/**
 * file: AESCipher.java
 * author: Anirudh Nagulapalli
 * course: MSCS630
 * Lab: Lab04
 * due date: Apr 05, 2017
 * version: 1.8
 *
 * This file contains a logic to
 * produce 11 round keys for a given
 * 16 hexadecimal string
 */


import java.util.Scanner;

public class AESCipher{

  static String KeyHex;
  static String K[][] = new String[4][4];
  static String W[][] = new String[4][44];
  static String Wnew[][] = new String[4][4];
  static String roundKey = "";
  static int Wnumber;

  // ////////////////////////////////////////////////////////////////////////////////////
	// Precomputed tables.
	// ////////////////////////////////////////////////////////////////////////////////////

	// Precomputed Rijndael S-BOX
  // From snipplr.com/view/67929
	private static final String sbox[][] = {
  //  0     1     2     3     4     5     6     7     8     9     A     B     C     D     E     F
    {"63", "7C", "77", "7B", "F2", "6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76"}, /** 0 */
    {"CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "D4", "A2", "AF", "9C", "A4", "72", "C0"}, /** 1 */
    {"B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15"}, /** 2 */
    {"04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75"}, /** 3 */
    {"09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84"}, /** 4 */
    {"53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF"}, /** 5 */
    {"D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8"}, /** 6 */
    {"51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2"}, /** 7 */
    {"CD", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73"}, /** 8 */
    {"60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB"}, /** 9 */
    {"E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79"}, /** A */
    {"E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08"}, /** B */
    {"BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A"}, /** C */
    {"70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E"}, /** D */
    {"E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF"}, /** E */
    {"8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16"}  /** F */
  };


  // Precomputed Rijndael R-Con
  // From Handout
  private static final String Rcon[] = {"00", "01", "02", "04", "08", "10", "20", "40", "80", "1B", "36"};


  public static String aesRoundKeys(String KeyHex){
    int col, row, count, i, j;
    char[] input;
    String temp, RconVal;

    //Setting values of K
    input = KeyHex.toCharArray();
    count = 0;
    for(col=0; col<4; col++){
      for(row=0; row<4; row++){
        K[row][col] = Character.toString(input[count]) + Character.toString(input[count+1]);
        count+=2;
      }
    }

    //Copying values of K in to W
    for(col=0; col<4; col++){
      for(row=0; row<4; row++){
        W[row][col] = K[row][col];
      }
    }

    //To set remaining values of W as zeros
    for(col=4; col<44; col++){
      for(row=0; row<4; row++){
        W[row][col] = "0";
      }
    }

    for(Wnumber=4; Wnumber<44; Wnumber++){
      //If not a multiple of 4
      if(Wnumber%4 != 0){
        for(row=0; row<4; row++){
          W[row][Wnumber] = XOR(W[row][Wnumber-4], W[row][Wnumber-1]);
        }
      }

      //If a multiple of 4
      else{
        //Wnew
        for(i=0; i<4; i++){
          Wnew[0][i] = W[i][Wnumber-1];
        }
        // for(i=0; i<4; i++){
        //   System.out.println(Wnew[0][i]);
        // }

        //Shifting
        temp = Wnew[0][0];
        for(i=0; i<3; i++){
          Wnew[0][i] = Wnew[0][i+1];
        }
        Wnew[0][3] = temp;
        // for(i=0; i<4; i++){
        //   System.out.println(Wnew[0][i]);
        // }

        //Sbox
        for(i=0; i<4; i++){
          Wnew[0][i] = aesSBox(Wnew[0][i]);
        }

        //Get Rcon
        RconVal = aesRcon(Wnumber/4);

        //XOR operation
        Wnew[0][0] = XOR(RconVal, Wnew[0][0]);

        //Final Step
        for(i=0; i<4; i++){
          W[i][Wnumber] = XOR(W[i][Wnumber-4], Wnew[0][i]);
        }
      }
    }

    for(i=0; i<44; i++){
      for(j=0; j<4; j++){
        roundKey +=W[j][i];
        if((i+1)%4==0 && j==3){
          System.out.println(roundKey);
          roundKey = "";
        }
      }
    }
    return "";
  }

  /**
   * This fuction produces the Hexadecimal value of the XOR operation
   */
  public static String XOR(String first, String second){
    int firstNum = Integer.parseInt(first, 16);
    int secondNum = Integer.parseInt(second, 16);
    int Value = firstNum ^ secondNum;
    String HexValue = Integer.toHexString(Value);
    if(HexValue.length()==1){
      return "0" + HexValue;
    }
    else
      return HexValue;
  }

  /**
   * This fuction produces the final output from the S-BOX table
   */
  public static String aesSBox(String inHex){
    String outHex = sbox[Integer.parseInt(inHex.split("")[0],16)][Integer.parseInt(inHex.split("")[1],16)];
    return outHex;
  }

  /**
   * This fuction produces the final output from the R-Con table
   */
  public static String aesRcon(int round){
    String Value = Rcon[round];
    return Value;
  }

}
