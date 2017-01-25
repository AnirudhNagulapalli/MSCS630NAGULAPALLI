
import java.util.Scanner;

public class Percentages{
  public static void main(String args[]){
    float midterm, finals, projects, homework, Grade;
    System.out.println("Please enter the following as percentages");
    Scanner sc = new Scanner(System.in);
    System.out.println("Midterm Exam: ");
    midterm = sc.nextFloat();
    System.out.println("Final Exam: ");
    finals = sc.nextFloat();
    System.out.println("Projects: ");
    projects = sc.nextFloat();
    System.out.println("Homework: ");
    homework = sc.nextFloat();
    Grade = (midterm + finals + projects + homework) / 4;
    System.out.println("Your final grade is: " +Grade);
  }
}
