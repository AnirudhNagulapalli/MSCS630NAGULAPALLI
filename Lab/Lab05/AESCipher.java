/**
 * file: AESCipher.java
 * author: Anirudh Nagulapalli
 * course: MSCS630
 * Lab: Lab05
 * due date: May 10, 2017
 * version: 1.8
 *
 * This file contains a logic to
 * produce an encrypted output
 * with give input of a 16-bit
 * key and plain text
 *
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
	public static final String sbox[][] = {
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
  public static final String Rcon[] = {"00", "01", "02", "04", "08", "10", "20", "40", "80", "1B", "36"};

  static String[][] inputData = new String[4][44];

  // Galois Matrix - fixed
  static String[][] GaloisMatrix = {
    {"02", "03", "01", "01"},
    {"01", "02", "03", "01"},
    {"01", "01", "02", "03"},
    {"03", "01", "01", "02"}
  };

  // To generate the round keys for Lab04
  public static String aesRoundKeys(String KeyHex, String InputText){
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

    // for(i=0; i<44; i++){
    //   for(j=0; j<4; j++){
    //     roundKey +=W[j][i];
    //     if((i+1)%4==0 && j==3){
    //       System.out.println(roundKey);
    //       roundKey = "";
    //     }
    //   }
    // }

    generateDataMatrix(InputText);
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

  /* This method XOR's the two 4*4 matrices and outputs the result
   *
   * Parameters
   *  sHex : Input text matrix.
   *  keyHex : Input Key matrix.
   * Return Value
   *  outStateHex : XOR result of key matrix and text matrix
   */
  public static String[][] aesStateXOR(String[][] sHex, String[][] keyHex) {
    String[][] outStateHex = new String[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        outStateHex[i][j] = XOR(sHex[i][j], keyHex[i][j]);
      }
    }
    return outStateHex;
  }

  /* This method substitutes each and every element
   * of a 4*4 matrix using the S-box
   *
   * Parameters
   *  inStateHex : Output from aesStateXOR.
   * Return Value
   *  outStateHex : substituted SBox matrix.
   */
  public static String[][] aesNibbleSub(String[][] inStateHex) {
    String[][] outStateHex = new String[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        outStateHex[i][j] = aesSBox(inStateHex[i][j]);
      }
    }
    return outStateHex;
  }

  /* This method left shifts each element with respect to their row number
   * Parameters
   *  inStateHex : Output from Nibble Substitution.
   * Return Value
   *  outStateHex : Matrix after shifting.
   */
  public static String[][] aesShiftRow(String[][] inStateHex) {
    String[][] outStateHex = new String[4][4];
    int row = 0;
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        outStateHex[i][j] = inStateHex[i][(j + row) % 4];
      }
      row++;
    }
    return outStateHex;
  }

  /*
   * GaloisMatrix is used to multiply the columns of the 4*4 input matrix
   *
   * Parameters inStateHex : Output from ShiftRows
   * Return Value returnMatrix : Matrx after row shift
   */
  public static String[][] aesMixColumn(String[][] inStateHex) {
    String sum = "0";

    String[][] returnMatrix = new String[4][4];
    int row;
    int rowCount = 0;
    int stateCol = 0;
    while (stateCol<=3) {
      for (row = 0; row < 4 && stateCol < 4; row++) {
        sum = "0";
        for (int col = 0, stateRow = 0; col < 4; col++) {
          if (null != GaloisMatrix[row][col]) {
            switch (GaloisMatrix[row][col]) {
              case "01":
                sum = XOR(sum, inStateHex[stateRow][stateCol]);
                break;
              case "02":
                sum = XOR(sum,
                  shiftBit(inStateHex[stateRow][stateCol]));
                break;
              case "03":
                String temp = XOR(
                  inStateHex[stateRow][stateCol],
                  shiftBit(inStateHex[stateRow][stateCol]));
                sum = XOR(sum, temp);
                break;
              default:
                break;
            }
          }
          stateRow++;
        }
        returnMatrix[row][stateCol] = sum;
        if (rowCount == 3) {
          stateCol++;
          rowCount = -1;
        }
        rowCount++;
      }
    }
    return returnMatrix;
  }

  /**
   * This method performs desired operations for matrix multiplication.
   *
   * Parameters
   *  pstrHexValue : Value for matrix multiplication.
   * Return Value
   *  Value : Value that has been bit shifted.
   */
  public static String shiftBit(String pstrHexValue) {
    // Binary value in string format for the input integer
    String binary = Integer.toBinaryString(Integer.parseInt(pstrHexValue, 16));

    // Pad if the length of the string is less than 8
    while (binary.length() < 8) {
      binary = "0" + binary;
    }

    // Shift to left by 1
    int intvalueIn = Integer.parseInt(binary, 2);
    String shiftedBinary = Integer.toBinaryString(intvalueIn << 1);
    shiftedBinary = (shiftedBinary.length() > 8 ? shiftedBinary
      .substring(1) : shiftedBinary);

    // check if upper significatn bit is 1,
    // then XOR by 27
    // else return the original value
    if (binary.substring(0, 1).equals("1")) {
      String constant = Integer.toString(27, 2);
      while (constant.length() < 8) {
        constant = "0" + constant;
      }
      return XOR(
        Integer.toHexString(Integer.parseInt(shiftedBinary, 2)),
        Integer.toHexString(Integer.parseInt(constant, 2)));
    }
    else {
      return Integer.toHexString(Integer.parseInt(shiftedBinary, 2));
    }
  }

  /** This method computes data for one full round of AES encryption
   *
   * Parameters
   *  larRoundData : Intermediate Round Data
   *  larRoundKey : Intermediate Round Keys
	 *  pintRoundCount : Round Number
   * Return Value
   *  larInputToNextStep: Intermediate Round Data
	 *  to be used in the next step.
   */
  public static String[][] computeDataForEachRound(
    String[][] larRoundData, String[][] larRoundKey, int pintRoundCount) {
    String[][] larInputToNextStep;
    larInputToNextStep = null;

    //Step 1: Perform XOR of the data and round key.
    larInputToNextStep = aesStateXOR(larRoundData, larRoundKey);

    //Step 2: Perform Nibble Substitution of the XOR
    if (larInputToNextStep != null) {
      larInputToNextStep = aesNibbleSub(larInputToNextStep);
    }

    //Step 3: Perform Shifting on Rows
    if (larInputToNextStep != null) {
      larInputToNextStep = aesShiftRow(larInputToNextStep);
    }

    //Step 4: Perform Mixing of column if not the last round
    if (pintRoundCount != 10) {
      if (larInputToNextStep != null) {
        larInputToNextStep = aesMixColumn(larInputToNextStep);
      }
    }

	return larInputToNextStep;
  }

  /** This method encrypts the plaintext with the key to produce a ciphertext.
   *
   * Parameters
   *  pstrInputText : Plain text taken from the input file
   *  pstrInputKey : Key taken from the input file
   * Return Value
   *  larRoundData : Ciphertext
   */
  public static void aes(String pstrInputText, String pstrInputKey) {
    // If the Input key and Text is not empty
    // Process the keys and Input to generate cipher text.
    if (!(pstrInputText.trim().equals("") && pstrInputKey.trim().equals(""))) {
      // Generate Matrices for the given Key and Text.
      aesRoundKeys(pstrInputText, pstrInputKey);

      int roundCount = 0;
      String[][] larRoundKey = new String[4][4];
      String[][] larRoundData = new String[4][4];

      //Copy the Input Data matrix into a local Matrix.
      for (int row = 0; row < W.length; row++) {
        System.arraycopy(inputData[row], 0, larRoundData[row], 0, 4);
      }

      // Take 4x4 matrix from 4x44 as round keys
	    for (int increment = 0; increment < W[0].length ; increment += 4) {
         // Copy the 4x4 Round Key matrix from the global 4x44 matrix
         // into a local Matrix.
         for (int row = 0; row < W.length; row++) {
           System.arraycopy(W[row], increment, larRoundKey[row], 0, 4);
         }

		     if(roundCount == 10)
			      larRoundData = aesStateXOR(larRoundData, larRoundKey);
		     else{
           roundCount++;
           larRoundData = computeDataForEachRound(
           larRoundData, larRoundKey, roundCount);
		     }
      }
      if (larRoundData != null) {
         printRoundData(larRoundData);
      }
    }
  }

  /**
   * This method generates the matrix for the input text from the file.
   *
   * Parameters
   *  pstrInputText : Input text received from the file
   */
  public static void generateDataMatrix(String pstrInputText) {
    try {
      int col = 0;
      for (int colCounter = 0; colCounter < (pstrInputText.length() - 1);
        colCounter += 8, col++) {
        int row = 0;
        for (int rowCounter = colCounter; rowCounter < (colCounter + 8);
          rowCounter += 2, row++) {
          inputData[row][col]
            = pstrInputText.substring(rowCounter, (rowCounter + 2));
        }
      }
    } catch (Exception ex) {
      System.out.println("Exception in generateDataMatrix is : " + ex.getMessage());
    }
  }

  /**
   * This method prints the cipher generated by AES algorithm
   *
   * Parameters
   *  larRoundData : Final Cipher text generated by the algorithm.
   */
  public static void printRoundData(String[][] larRoundData) {
    try {
      System.out.println("Output:");
      for (int cols = 0; cols < 4; cols++) {
        for (int row = 0; row < 4; row++) {
          System.out.print(larRoundData[row][cols].toUpperCase());
        }
      }
      System.out.println();
    } catch (Exception ex) {
      System.out.println("Exception in printRoundData is : " + ex.getMessage());
    }
  }
}
