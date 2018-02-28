/**
 * 
 */
package com.julie.main;

import java.io.Console;
import java.util.regex.*;

import test.com.julie.dbConnect.Queries;

import com.julie.dbConnect.*;

/**
 * @author Julie.Meese
 *
 */
public class DisplayConsole extends Thread {

	/**
	 * 
	 */
	public DisplayConsole() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * 
	 */
	public void run() {
		//display();
	}
	
	public void display() {
		
		String choice = "0";
		String database = "CollectDb";
		String collection = "wordsCollection";
		Boolean display = true;
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
	    System.out.println("\t\t\t\t|        6. Option 6  - Query - Get Max Frequency                |");
	    System.out.println("\t\t\t\t|        7. Option 7  - Query - Get words of x Length            |");
	    System.out.println("\t\t\t\t|        8. Option 8  - Query - Get words beginning with [char]  |");
	    System.out.println("\t\t\t\t|        9. Exit                                                 |");
	    System.out.println("\t\t\t\t==================================================================");
	    System.out.println("\n\n\n\n");
	    
	   
	    do {
	    	try{
	    		System.out.println("\t\t\t\t Select option - Enter a number between 1 and 9");
	    		choice = System.console().readLine();
	    	} catch(Exception e) {
	    		e.printStackTrace(); //DEBUG
	    	}
		    
		switch(choice) {
		case "1" :
			System.out.println("\t\t\t\tEnter the name of the database to use:  ");
			database = System.console().readLine();
			System.out.println("\t\t\t\tYou chose: " + database + ". Okay? y/n");
			{
				String s = System.console().readLine();
				if(s != "y"){
					database = "";
				}
			}
			break;
		case "2" :
			System.out.println("\t\t\t\tEnter the name of the collection to use:  ");
			collection = System.console().readLine();
			System.out.println("You chose: " + collection + ". Okay? y/n");
			{
				String s = System.console().readLine();
				if(s != "y"){
					collection = "";
				}
			}
			break;
		case "3" :
			System.out.println("\t\t\t\tEnter the url of the document to analyse :  ");
			System.out.println("(This must be the full path and filename to a file on the local machine)");
			String inputdocurl = System.console().readLine();
			System.out.println("\t\t\t\tYou entered: " + inputdocurl + " Okay? y/n");
			{
				String s = System.console().readLine();
				if(s != "y"){
					inputdocurl = "";
				}
			}
			break;
		case "4" :
			System.out.println("\t\t\t\tThis query gets the minimum frequency for words in a database and collection of your choice");
			System.out.println("\t\t\t\tBefore you run this query, please ensure you have selected your database and collection");
			System.out.println("\t\t\t\tThe current database is: " + database + " and the current collection is " + collection + ".");
			if(database == "" || collection == "") {
				System.out.println("\t\t\t\tPlease enter a valid database name and a valid collection name at Options 1 & 2");
			} else {
				System.out.println("\t\t\t\tRun Query? y/n");
				String s ="";
				s = System.console().readLine();
					if(s == "y"){
						String querystring = Queries.getMinFrequency();
						String result ="";  //TODO run the query
						System.out.println("\t\t\t\tMinFrequency:  " + result);
						break;
					}
				}
				break;
			
			//TODO run query
		case "5" :
			System.out.println("\t\t\t\tThis query gets the maximum frequency for words in a database and collection of your choice");
			System.out.println("\t\t\t\tBefore you run this query, please ensure you have selected your database and collection");
			System.out.println("The current database is: " + database + " and the current collection is " + collection + ".");
			if(database == "" || collection == "") {
				System.out.println("\t\t\t\tPlease enter a valid database name and a valid collection name at Options 1 & 2");
			} else {
				System.out.println("\t\t\t\tRun Query? y/n");
				{
					String s ="";
					s = System.console().readLine();
					if(s == "y"){
						String querystring = Queries.getMaxFrequency();
						String result ="";  //TODO run the query
						System.out.println("\t\t\t\tMaxFrequency:  " + result);
						break;
					}
				}
				break;
			}
			break;
			
		case "6" :
			//TODO  
			System.out.println("\t\t\t\tTODO: Add menu item code");
			break;
		case "7" :
			System.out.println("\t\t\t\tTODO: Add menu item code");
			break;
		case "8" :
			System.out.println("TODO: Add menu item code");
			break;
		case "9" :
			System.out.println("\t\t\t\tpress any key to exit: ");
			System.console().readLine();
			display = false;
			System.exit(0);
			break;
		case "default" : 
			System.out.println("\t\t\t\tInvalid Choice, please choose again.");

			
		} 
		
	} while(display);
}
    
}
