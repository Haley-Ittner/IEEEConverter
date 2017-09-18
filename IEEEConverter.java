import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.text.DecimalFormat;

public class IEEEConverter {
   
   public static void main(String[] args) {
      DecimalFormat formatter = new DecimalFormat("0.0");
      ArrayList<String> num = new ArrayList<String>();
      ArrayList<String> frac = new ArrayList<String>();
      ArrayList<String> expo = new ArrayList<String>();
      Scanner scnr = new Scanner(System.in);
      int exponent = 0;
      int way = 0;
      String response = "";
      String answer = "";
      String sign = "";
      String wholeAnswer = "";
      String fractionalAnswer = "";
      String toOrFrom = "";
      
      System.out.print("Are you converting from or to IEEE? ");
      toOrFrom = scnr.nextLine();
      way = toOrFromChecker(toOrFrom);
      
      if (way == 0) {
         System.out.print("Please enter your IEEE number ");
         response = scnr.nextLine();
         num = putter(response);
         sign = signChecker(response.substring(0,1));
         exponent = exponentConverter(num);
         answer = mantissaConverter(num,exponent);
         System.out.println("Mantissa: " + answer + " Length; " + answer.length());
         wholeAnswer = wholeNumberBiConverter(answer);
         System.out.println("Whole number: " + wholeAnswer);
         fractionalAnswer = fractionalNumberBiConverter(answer);
         System.out.println("Fractional number: " + fractionalAnswer);
         System.out.println("You number in IEEE, " + response + " is " + sign + 
                         wholeAnswer + fractionalAnswer.substring(1, fractionalAnswer.length()) 
                         + " in decimal");
      } else {
         System.out.print("Please enter your decimal number ");
         response = scnr.nextLine();
         wholeAnswer = wholeNumberFinder(response);
         decToBinary(wholeAnswer, num);
         fractionalDecCalc(response, frac, num, formatter);
         exponentFinder(num, expo, frac);
         System.out.println("Num is " + num);
         System.out.println("Frac is " + frac);
         System.out.println("Expo is " + expo);
         wholeAnswer = binaryPrinter(num);
         fractionalAnswer = binaryPrinter(frac);
         answer = binaryPrinter(expo);
         sign = negOrPos(response);
         System.out.println("Your number, " + response + ", converted to IEEE is " +
                            sign + answer + wholeAnswer.substring(1, wholeAnswer.length()) + 
                            fractionalAnswer); 
      }                         
   }
   
   public static int toOrFromChecker(String answer) {
      if (answer.substring(0,1).toLowerCase().equals("t")) {
         return 1;
      } else if (answer.substring(0,1).toLowerCase().equals("f")) {
         return 0;
      } else {
         System.out.println("Goodbye!");
         System.exit(0); 
      }
      return 0;     
   }
   
   public static ArrayList putter(String number) {
      ArrayList<String> start = new ArrayList<String>();
      for(int x = 0; x < number.length(); x++) {
         start.add(number.substring(x, x + 1));
      }
      return start;
   }
   
   public static String negOrPos(String num) {
      if (num.charAt(0) != '-') {
         return "0";
      } else {
         return "1";
      }   
   }
   
   public static String signChecker(String firstBit) {
      if (firstBit.equals("1")) {
         return "-";   
      } else {
         return "";
      }   
   }
   
   public static int exponentConverter(ArrayList number) {
      double endingNumber = 0.0;
      double tmp = 0.0;
      int counter = 1;
      String holder = "";
      for(double x = 7; x > -1; x--) {
         holder += number.get(counter);
         System.out.println("Holder of exponent: " + holder);
         tmp = Double.parseDouble(holder);
         endingNumber += tmp * Math.pow(2, x);
         holder = "";
         counter++;
      }
      return (int) endingNumber - 127;  
   }
   
   public static String mantissaConverter(ArrayList number, int exponent) {
      System.out.println("Exponent: " + exponent);
      String endNumber = "1";
      String pad = "";
      for(int x = 9; x < number.size(); x++) {
         endNumber += number.get(x);
      }
      if(exponent > 0) {
         return endNumber.substring(0, exponent + 1) + "." + endNumber.substring(exponent + 1, endNumber.length()); 
      } else {
         for(int x = 0; x < Math.abs(exponent) - 1; x++) {
            System.out.println("Padding a zero!");
            pad += "0";
         }
         return "0." + pad + endNumber; 
      }        
   }
   
   public static String wholeNumberBiConverter(String number) {
      String newNumber = "";
      int checker = 0;
      double endingNumber = 0.0;
      double tmp = 0.0;
      int counter = 0;
      String holder = "";
      while (number.charAt(checker) != '.') {
         newNumber += number.charAt(checker);
         checker++;
      }
      System.out.println("New number: " + newNumber);
      for(double x = newNumber.length() - 1; x > -1; x--) {
         holder += newNumber.substring(counter, counter + 1);
         tmp = Double.parseDouble(holder);
         endingNumber += tmp * Math.pow(2, x);
         holder = "";
         counter++;
      }
      return (int) endingNumber + "";
   }
   
   public static String fractionalNumberBiConverter(String number) {
      String newNumber = "";
      int checker = number.length() - 1;
      float endingNumber = 0F;
      float tmp = 0F;
      int counter = 0;
      String holder = "";
      while (number.charAt(checker) != '.') {
         newNumber += number.charAt(checker);
         checker--;
      }
      System.out.println("New number length: " + newNumber.length());
      for(double x = newNumber.length(); x > 0; x--) {
         holder += newNumber.substring(counter, counter + 1);
         System.out.println("Holder: " + holder);
         if (holder.equals("1")) {
            endingNumber += (float) 1.0/(Math.pow(2, x));
            System.out.println("Ending number: " + endingNumber);
            holder = "";
         }
         holder = "";
         counter++;   
      }
      String test = endingNumber + "";
      return test;   
   }
   
   public static String wholeNumberFinder(String number) {
      String newNumber = "";
      int checker = 0;
      double endingNumber = 0.0;
      double tmp = 0.0;
      int counter = 0;
      String holder = "";
      while ((checker < number.length()) && (number.charAt(checker) != '.')) {
         newNumber += number.charAt(checker);
         checker++;
      }
      return newNumber;   
   }
   
   public static void decToBinary(String working, ArrayList newNumber) {
      double number = Double.parseDouble(working);
      int remainder = 0;
      if (number % 2 == 0) {
         newNumber.add(0, "0");
      } else {
         newNumber.add(0, "1"); 
      }        
      while (number > 1) {
         remainder = (int) number/2;
         if (remainder % 2 == 0) {
            newNumber.add(0, "0");
         } else {
            newNumber.add(0,"1");
         }
         number = remainder;
      }                            
   }
   
   public static void fractionalDecCalc(String working, ArrayList number, ArrayList stop, DecimalFormat formatter) {
      String newNumber = ".";
      int checker = working.length() - 1;
      int endingNumber = 24 - stop.size();
      int place = 0;
      double tmp = 0F;
      double remainder = 0F;
      String holder = "";
      while ((checker > -1) && (working.charAt(checker) != '.')) {
         newNumber += working.charAt(checker);
         checker--;
      }
      tmp = Double.parseDouble(newNumber);
      System.out.println("New Number start: " + tmp);
      while ((place < endingNumber) && (tmp != 0.0)) {
         remainder = tmp * 2;
         System.out.println("Remainder: " + remainder);
         if (remainder < 1) {
            number.add("0");
            tmp = (double) Double.parseDouble(formatter.format(remainder));
            System.out.println("Tmp(was below 1): " + tmp);
         } else {
            number.add("1");
            tmp = (double) Double.parseDouble(formatter.format(remainder - 1));
            System.out.println("Tmp(was above 1): " + tmp);
            
         }
         remainder = 0.0;
         place++;     
      }         
   }
   
   public static void exponentFinder(ArrayList number, ArrayList answer, ArrayList fraction) {
      if ( number.size() > 1) {
         int exponent = number.size() + 126;
         System.out.println("Exponent: " + exponent);
         int remainder = 0;
         if (exponent % 2 == 0) {
            answer.add(0, "0");
         } else {
            answer.add(0, "1"); 
         }        
         while ((answer.size() < 9) && (exponent > 1)) {
            remainder = (int) exponent/2;
            if (remainder % 2 == 0) {
               answer.add(0, "0");
            } else {
               answer.add(0,"1");
            }
            exponent = remainder;
         }
      } else {
         int exponent = 0;
         int count = 0;
         while((count < fraction.size()) && (fraction.get(count).equals("0"))) {
            exponent++;
            count++;
         }
         exponent += 127;
         System.out.println("Exponent: " + exponent);
         int remainder = 0;
         if (exponent % 2 == 0) {
            answer.add(0, "0");
         } else {
            answer.add(0, "1"); 
         }        
         while ((answer.size() < 9) && (exponent > 1)) {
            remainder = (int) exponent/2;
            if (remainder % 2 == 0) {
               answer.add(0, "0");
            } else {
               answer.add(0,"1");
            }
            exponent = remainder;
         }      
      }               
   }
   
   public static String binaryPrinter(ArrayList number) {
      String binary = "";     
      for(int x = 0; x < number.size(); x++) {
         binary += number.get(x);   
      }
      return binary;   
   }
}