import java.util.Scanner;

public class Lab01{
  public static void main(String args[]){
    int i, j;

    char array1[] = new char[27];
    for(i=0; i<26; i++){
      array1[i] = (char)(97+i);
    }

    char array2[] = new char[27];
    for(i=0; i<26; i++){
      array2[i] = (char)(65+i);
    }

    array1[26] = array2[26] = (char)32;

    //System.out.println(array1);
    //System.out.println(array2);
    System.out.println("Enter your input");
    Scanner sc = new Scanner(System.in);
    String input = sc.nextLine();

    for(i=0; i<input.length(); i++){
      for(j=0; j<27; j++){
        if(input.charAt(i) == array1[j] || input.charAt(i) == array2[j]){
          System.out.print(j + " ");
        }
      }
    }
    System.out.println();
  }
}
