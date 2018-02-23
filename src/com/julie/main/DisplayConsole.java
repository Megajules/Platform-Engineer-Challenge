/**
 * 
 */
package com.julie.main;

import java.io.Console;

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
		display();
	}
	
	public void display() {
		
		String choice = "0";
		String database = "";
		String collection = "";
		Boolean display = true;
		
		while (display) {
		System.out.println("==================================================================");
	    System.out.println("|   PLATFORM ENGINEER CHALLENGE                                  |");
	    System.out.println("==================================================================");
	    System.out.println("| Options:                                                       |");
	    System.out.println("|        1. Option 1  - Choose Database                          |");
	    System.out.println("|        2. Option 2  - Choose Collection                        |");
	    System.out.println("|        3. Option 3  - Analyse Data                             |");
	    System.out.println("|        4. Option 4  - Query - Get Min Frequency                |");
	    System.out.println("|        5. Option 5  - Query - Get Max Frequency                |");
	    System.out.println("|        6. Option 6  - Query - Get Max Frequency                |");
	    System.out.println("|        7. Option 7  - Query - Get words of x Length            |");
	    System.out.println("|        8. Option 8  - Query - Get words beginning with [char]  |");
	    System.out.println("|        9. Exit                                                 |");
	    System.out.println("==================================================================");
	    
	    System.out.println(" Select option: ");
	    choice = System.console().readLine();
	    
		switch(choice) {
		case "1" :
			System.out.println("Enter the name of the database to use:  ");
			database = System.console().readLine();
			System.out.println("You chose: " + database + ". Okay? y/n");
			{
				String s = System.console().readLine();
				if(s != "y"){
					database = "";
				}
			}
			break;
		case "2" :
			System.out.println("Enter the name of the collection to use:  ");
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
			System.out.println("Enter the url of the document to analyse :  ");
			System.out.println("(This must be the full path and filename to a file on the local machine)");
			String inputdocurl = System.console().readLine();
			System.out.println("You entered: " + inputdocurl + " Okay? y/n");
			{
				String s = System.console().readLine();
				if(s != "y"){
					inputdocurl = "";
				}
			}
			break;
		case "4" :
			System.out.println("This query gets the minimum frequency for words in a database and collection of your choice");
			System.out.println("Before you run this query, please ensure you have selected your database and collection");
			System.out.println("The current database is: " + database + " and the current collection is " + collection + ".");
			if(database == "" || collection == "") {
				System.out.println("Please enter a valid database name and a valid collection name at Options 1 & 2");
			} else {
				System.out.println("Run Query? y/n");
				String s ="";
				s = System.console().readLine();
					if(s == "y"){
						//TODO  Call getMinFrequency()
						System.out.println("TODO:  Call getMinFrequency()");
						break;
					}
				}
				break;
			
			//TODO run query
		case "5" :
			System.out.println("This query gets the maximum frequency for words in a database and collection of your choice");
			System.out.println("Before you run this query, please ensure you have selected your database and collection");
			System.out.println("The current database is: " + database + " and the current collection is " + collection + ".");
			if(database == "" || collection == "") {
				System.out.println("Please enter a valid database name and a valid collection name at Options 1 & 2");
			} else {
				System.out.println("Run Query? y/n");
				{
					String s ="";
					s = System.console().readLine();
					if(s == "y"){
						//TODO  Call getMaxFrequency()
						//TODO  Call getMinFrequency()
						System.out.println("TODO:  Call getMaxFrequency()");
						break;
					}
				}
				break;
			}
			break;
			
		case "6" :
			//TODO  
			System.out.println("TODO: Add menu item code");
			break;
		case "7" :
			System.out.println("TODO: Add menu item code");
			break;
		case "8" :
			System.out.println("TODO: Add menu item code");
			break;
		case "9" :
			System.out.println("press any key to exit: ");
			System.console().readLine();
			display = false;
		}
	}
		System.exit(0);	
  }
    
}
