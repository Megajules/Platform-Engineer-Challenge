/**
 * 
 */
package com.julie.main;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.*;
import com.julie.dbConnect.Query;

//import com.julie.dbConnect.*;

/**
 * @author Julie.Meese
 *
 */
public class DisplayConsole {
	
	//private String choice;
	private String database = "";
	private String collection = "";
	private Boolean display = true;
	
	/**
	 * 
	 */
	public DisplayConsole() {
		// TODO Auto-generated constructor stub
	}
	
	public DisplayConsole(String database, String collection) {
		this.database = database;
		this.collection = collection;
	}
	
	public void displayMenu() {
		
		System.out.println("\n\n\n\n");
		System.out.println("\t\t\t\t==================================================================");
	    System.out.println("\t\t\t\t|   PLATFORM ENGINEER CHALLENGE                                  |");
	    System.out.println("\t\t\t\t==================================================================");
	    System.out.println("\t\t\t\t| Options:                                                       |");
	    System.out.println("\t\t\t\t|        1. Option 1  - Choose Database                          |");
	    System.out.println("\t\t\t\t|        2. Option 2  - Choose Collection                        |");
	    System.out.println("\t\t\t\t|        3. Option 3  - Analyse Data                             |");
	    System.out.println("\t\t\t\t|        4. Option 4  - Query - Get Min Frequency                |");
	    System.out.println("\t\t\t\t|        5. Option 5  - Query - Get Max Frequency                |");
	    System.out.println("\t\t\t\t|        6. Option 6  - Query - Get [x] Frequency                |");
	    System.out.println("\t\t\t\t|        7. Option 7  - Query - Get words of x Length            |");
	    System.out.println("\t\t\t\t|        8. Option 8  - Query - Get words beginning with [char]  |");
	    System.out.println("\t\t\t\t|        9. Exit                                                 |");
	    System.out.println("\t\t\t\t==================================================================");
	    System.out.println("\n\n\n\n");
	    
	} 
	
	public void menuOptions() {
	        String choice = "";
	        Scanner scanner = new Scanner(System.in);
	        
	    	
	  do {
		 
		  try{
	    		displayMenu();
	    		System.out.println("\t\t\t\t Select option - Enter a number between 1 and 9");
	    		System.out.println("\t\t\t\t");
	    	} catch(Exception e) {
	    		e.printStackTrace(); //DEBUG
	    	}
		choice = scanner.nextLine();
		System.out.println("\t\t\t\t Select option - Enter a number between 1 and 9");
		System.out.println("\t\t\t\t");
		switch(choice) {
		case "1" :
			System.out.println("\t\t\t\tEnter the name of the database to use:  ");
			database = scanner.nextLine();
			System.out.println("\t\t\t\tYou chose: " + database + ". Okay? y/n");
				String s1 = scanner.nextLine();
				if(s1 != "y"){
					database = "";
				}
			break;
			
		case "2" :
			System.out.println("\t\t\t\tEnter the name of the collection to use:  ");
			collection = scanner.nextLine();
			System.out.println("You chose: " + collection + ". Okay? y/n");
				String s2 = scanner.nextLine();
				if(s2 != "y"){
					collection = "";
				}
			break;
			
		case "3" :
			System.out.println("\t\t\t\tBegin Analysis?:  y/n");
			String s3 = scanner.nextLine();
			if(s3.equals("y")){
				Challenge challenge = new Challenge();
				try{
					challenge.init();
					System.out.println("Analysis complete. Press any key to return to menu");
				} catch (IOException e) {
					System.out.println("Sorry, something went wrong. Please try again.");
					System.out.println("Press any key to return to menu for retry.");
				}
			scanner.nextLine();
			break;
			}
			
			break;
			
		case "4" :
			System.out.println("\t\t\t\tThis query gets the minimum frequency for words in a database and collection of your choice");
			System.out.println("\t\t\t\tBefore you run this query, please ensure you have selected your database and collection");
			System.out.println("\t\t\t\tThe current database is: " + database + " and the current collection is " + collection + ".");
			if(database == "" || collection == "") {
				System.out.println("\t\t\t\tPlease enter a valid database name and a valid collection name at Options 1 & 2");
			break;
			} else {
				System.out.println("\t\t\t\tGet Minimum Frequency? y/n");
				String s4 = scanner.nextLine();
				System.out.print("\t\t\t\t");
					if(s4.equals("y")){
						Query query = new Query();
						Integer minFrequency = query.getMinFrequency();
						System.out.println("\t\t\t\tThe Minimum Frequency is:  " + minFrequency);
						System.out.println("Enter any key to view word Documents with this frequency.");
						scanner.nextLine();
						System.out.println("\t\t\t\tWords with Minimum Frequency: ");
						query.getDocumentsWithMinFrequency();
						System.out.println("Enter any alphanumeric"
								+ " key to return to menu");
						scanner.nextLine();
						break;
					} else {
						System.out.println("Query failed, Scanner value = " + s4);
					}
					break;
				}
		case "5" :
			System.out.println("\t\t\t\tThis query gets the maximum frequency for words in a database and collection of your choice");
			System.out.println("\t\t\t\tBefore you run this query, please ensure you have selected your database and collection");
			System.out.println("The current database is: " + database + " and the current collection is " + collection + ".");
			if(database == "" || collection == "") {
				System.out.println("\t\t\t\tPlease enter a valid database name and a valid collection name at Options 1 & 2");
			} else {
				System.out.println("\t\t\t\tRun Query? y/n");
					
					String s5 = scanner.nextLine();
					if(s5.equals("y")){
						Query query = new Query();
						Integer maxFrequency = query.getMaxFrequency();
						System.out.println("\t\t\t\tThe Maximum Frequency is:  " + maxFrequency);
						System.out.println("Enter any key to view word Documents with this frequency.");
						scanner.nextLine();
						System.out.println("\t\t\t\tWords with Maximum Frequency: ");
						query.getDocumentsWithMaxFrequency();
						System.out.println("Enter any alphanumeric"
								+ " key to return to menu");
						scanner.nextLine();
						break;
					} else {
						System.out.println("Query failed, Scanner value = " + s5);
					}
				break;
			}
			
		case "6" :
			System.out.println("\t\t\t\tThis query gets the frequency of your choice");
			System.out.println("\t\t\t\tBefore you run this query, please ensure you have analysed your data [menu item 3]");
			
			if(database == "" || collection == "") {
				System.out.println("\t\t\t\tPlease enter a valid database name and a valid collection name at Options 1 & 2");
			} else {
				System.out.println("\t\t\t\tRun Query? y/n");
					
					String s6 = scanner.nextLine();
					if(s6.equals("y")){
						Query query = new Query();
						//query.getFrequency();
						System.out.println("\t\t\t\tChoose your Frequency:  ");
						String input = scanner.nextLine();
						Integer frequency = Integer.parseInt(input);
						System.out.println("Enter any key to view word Documents with this frequency.");
						scanner.nextLine();
						System.out.println("\t\t\t\tWords with Chosen Frequency: " + frequency);
						query.getDocumentsWithXFrequency(frequency);
						System.out.println("Enter any alphanumeric"
								+ " key to return to menu");
						scanner.nextLine();
						break;
					} else {
						System.out.println("Query failed, Scanner value = " + s6);
					}
				break;
			}
			
		case "7" :
			System.out.println("\t\t\t\tTODO: Add menu item code");
			break;
		case "8" :
			System.out.println("\t\t\t\tThis query gets words beginning with a letter of your choice");
			System.out.println("\t\t\t\tBefore you run this query, please ensure you have selected your database and collection");
			System.out.println("The current database is: " + database + " and the current collection is " + collection + ".");
			if(database == "" || collection == "") {
				System.out.println("\t\t\t\tPlease enter a valid database name and a valid collection name at Options 1 & 2");
			} else {
				String requery = "n";
						do {
						Query query = new Query();
						System.out.println("Enter a letter, to view word Documents with a word beginning with the letter of your choice.");
						String s8 = scanner.nextLine();
						System.out.println("\t\t\t\tWords Beginning with: " + s8);
						query.getWordsBeginningWith(s8);
						System.out.println("Would you like to try again? y/n");
						requery = scanner.nextLine();
						} while(requery.equals("y"));	
					} 
			break;
			//else {
					//	System.out.println("Query failed, Scanner value = " + s8);
					//}
					
			//}
		case "9" :
			System.out.println("\t\t\t\tpress any key to exit: ");
			scanner.nextLine();
			display = false;
			System.exit(0);
			break;
		case "default" : 
			System.out.println("\t\t\t\tInvalid Choice, please choose again.");
			System.out.println("\t\t\t\t Select option - Enter a number between 1 and 9");
	  		  System.out.println("\t\t\t\t");
			  choice = scanner.nextLine();
			break;	
		} 
		
	} while(display);
	  
	  scanner.close();
}
    
}
