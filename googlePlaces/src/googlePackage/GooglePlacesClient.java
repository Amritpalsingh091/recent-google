 package googlePackage;

import java.util.Scanner;
 public class GooglePlacesClient {

	 public static void main(String[] args) throws Exception {
		 System.out.println(" the address should be in this format   \"San jose, Californie, États-Unis |Toronto, ON, Canada \" ");
				 
				 
				
		 System.out.println("Enter origin");
			Scanner keyboard = new Scanner(System.in);
			String origin = keyboard.nextLine();
			System.out.println("Enter destination ");
			Scanner keyboard1 = new Scanner(System.in);
			String destination  = keyboard1.nextLine();
			System.out.println("Enter mode ");
			Scanner keyboard2 = new Scanner(System.in);
			String mode = keyboard2.nextLine();
			//new Methods1().performSearch("San jose, Californie, États-Unis |Toronto, ON, Canada","Vancouver, BC, Canada|Seattle, Washington,États-Unis","driving");
	 		new Methods1().performSearch(origin,destination,mode);
	 }}
 
 
 
 
 
 
 
