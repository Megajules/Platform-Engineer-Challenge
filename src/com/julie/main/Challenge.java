/**
 * 
 */
package com.julie.main;

//import java.io.BufferedReader;
//import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.Arrays;
//import java.io.InputStreamReader;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
import java.util.Map;
//import java.util.stream.Stream;






import com.julie.dbConnect.*;
import com.julie.factory.documents.WordDocument;
//import com.julie.factory.documents.DocumentFactory;
//import com.julie.factory.documents.DocumentFactoryImpl;
import com.julie.processFiles.*;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
//import com.mongodb.client.model.Filters;

import com.mongodb.client.model.Filters;

import org.apache.log4j.*;
//import org.bson.Document;
import org.bson.Document;
//import org.bson.conversions.Bson;

/**
 * @author Julie.Meese
 *
 */
public class Challenge {
	
	private static Logger logger = Logger.getRootLogger();
	//private String partfile = "";
	private ConnectDB conn = new ConnectDB();
	String collname = "wordsCollection";
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		logger.info("Initialising Challenge...");
		Challenge challenge = new Challenge();
		try{
			challenge.init();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		challenge.printAllDocumentsInCollection();
		logger.info("\nEnd of initialisation phase");
	}
	
	private void connect() {
		
		conn.dropDatabase();
		conn.createDatabase("challengeDb");
		conn.createCollection(collname);
	}
	
	private void init() throws IOException{
		connect();
		FileProcessor proc = new FileProcessor();
		logger.info("Starting processing input file...");
		Map<String, Integer> words = proc.countDistinctWords();
		
		
		logger.info("Finished processing input file...");
		
		
		String type = "word";
		String url = "D:\\Downloads\\enwiki-latest-pages-articles-multistream.xml";
		
		for(Map.Entry<String, Integer> entry : words.entrySet()) {
			 if(!(entry.getKey().isEmpty())){
				 String word = entry.getKey();
				 Integer frequency = entry.getValue();
				 insertDocument(type, url, word, frequency);
//				 	System.out.print("total words: " + words.size());
//					System.out.println("\n");
//				    System.out.println("Inserted from map to database");
//				 	System.out.println("word: " + word);
//					System.out.println("frequency: " + frequency);
//					System.out.println("\n");
				 //Map<String, Integer> wordfrequency = new HashMap<String, Integer>();
				// wordfrequency.put(word, frequency);
				 
			 } else {
				 System.out.println("Something went wrong\n");
			 }
		}
		
		String fieldtoindex = "word";
		conn.createIndex(collname, fieldtoindex);
	}
	
	 public void findDocuments() {
		   logger.info("Finding documents in database that have the keyword 'word':  ");
		   FindIterable<Document> docs = conn.getCollection(collname).find(Filters.exists("word", true));
		   logger.info("printing documents to console...");
		   while(docs.iterator().hasNext()) {
			   System.out.println("Database doc:  ");
			   System.out.print(docs.iterator().next().toString());
		   }
		   logger.info("completed printing documents to console");
	 }
	
	 public void insertDocument(String type, String url, String word, Integer frequency ) {
			String collname = "wordsCollection";
			logger.info("adding a new document using " + type + ", "  + url + ", "  + word + ", "  + frequency);
			Document worddoc = new WordDocument()
		      .append("type", type)
		      .append("url", url)
		      .append("word", word)
			  .append("frequency", frequency.toString());
			conn.getCollection(collname).insertOne(worddoc);
	 }
			
	
	/*public void insertDocument(String type, String url, String word, Integer frequency ) {
		Document worddoc = new WordDocument()
	      .append("type", type)
	      .append("url", url)
	      .append("word", word)
		  .append("frequency", frequency.toString());
		conn.getCollection(collname).insertOne(worddoc);
	}*/
	
	public void updateDocument(String type, String url, String word, Integer frequency ) {
		Document worddoc = new WordDocument()
	      .append("type", type)
	      .append("url", url)
	      .append("word", word)
		  .append("frequency", frequency.toString());
		conn.getCollection(collname).insertOne(worddoc);
	}
	
	public void printAllDocumentsInCollection() {
		
		FindIterable<Document> d = conn.getCollection(collname).find();
	   	Document document = d.iterator().next();
	   	while(d.iterator().hasNext()){
	   	String type = document.getString("type");
	   	String url = document.getString("url");
	   	String word = document.getString("word");
	   	String frequency = document.getString("frequency");
	   	System.out.println("printing documents in collection");
	   	System.out.println("type:  " + type);
	   	System.out.println("url:  " + url);
	   	System.out.println("word:  " + word);
	   	System.out.println("frequency:  " + frequency);
	   	}
	}
	
}


