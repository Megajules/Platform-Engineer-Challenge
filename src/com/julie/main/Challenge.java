/**
 * 
 */
package com.julie.main;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import com.julie.dbConnect.*;
import com.julie.factory.documents.WordDocument;
import com.julie.processFiles.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
//import com.mongodb.reactivestreams.client.MongoCollection;

import org.apache.log4j.*;
import org.bson.Document;

import com.julie.main.DisplayConsole;

/**
 * @author Julie.Meese
 *
 */
public class Challenge extends Thread {
	
	private static Logger logger = Logger.getRootLogger();
	private ConnectDB conn = new ConnectDB();
	//private static MongoCollection<Document> collection;
	//private static MongoDatabase database;
	private static String collname = "wordsCollection";
	private static String dbname = "challengeDb"; 
	
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
		
		while(true) {
		(new Thread(new DisplayConsole())).start();
		Thread.currentThread().setPriority(NORM_PRIORITY);
        }
	}
	
	public void run(){
		
	}
	
	private void connect() {
		
		conn.dropCollection();
		conn.dropDatabase();
		conn.createDatabase(dbname);
		conn.createCollection(collname);
		//database = conn.getDatabase();
		//collection = conn.getCollection();
		conn.shardCollection();
	}
	
	private void init() throws IOException{
		connect();
		FileProcessor proc = new FileProcessor();
		logger.info("Starting processing input file...");
		Map<String, Integer> words = proc.countDistinctWords();
		System.out.println("Words Map Size = " + words.size());
		System.out.println("Empty? :  " + words.entrySet().isEmpty());
		logger.info("Finished processing input file...");
		
		String type = "word";
		//String url = "D:\\Downloads\\enwiki-latest-pages-articles-multistream.xml";
		String url = "D:\\Workspaces\\JAVA DEV\\Platform Engineer Challenge\\src\\com\\julie\\main\\partfile.txt";
		//List<Document> documents = new ArrayList<Document>();
		
		for(Map.Entry<String, Integer> entry : words.entrySet()) {
			 //if(!(entry.getKey().isEmpty())){
				 String word = entry.getKey();
				 Integer frequency = entry.getValue();
				 System.out.println("word: " + word);
			     System.out.println("frequency: " + frequency);
			     
				 Document doc = new Document("type", type).append("url", url).append("word", word).append("frequency", frequency);
				 conn.getCollection(collname).insertOne(doc);
				 //documents.add();
//				 System.out.println("adding to document: ");
//				 System.out.println("word: " + word);
//			     System.out.println("frequency: " + frequency);
				    
				 			 
				// insertDocument(type, url, word, frequency);
//				 	System.out.print("total words: " + words.size());
//					System.out.println("\n");
//				    System.out.println("Inserted from map to database");
//				 	System.out.println("word: " + word);
//					System.out.println("frequency: " + frequency);
//					System.out.println("\n");
				 //Map<String, Integer> wordfrequency = new HashMap<String, Integer>();
				// wordfrequency.put(word, frequency);
				 
//			 } else {
//				 System.out.println("Something went wrong\n");
//			 }
			 
			// MongoCollection<Document> coll = conn.getCollection(collname);
			// conn.getCollection(collname).insertMany(documents);
			// coll.count();
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
			logger.info("adding a new document using " + type + ", "  + url + ", "  + word + ", "  + frequency);
			Document worddoc = new WordDocument()
		      .append("type", type)
		      .append("url", url)
		      .append("word", word)
			  .append("frequency", frequency);
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
	   	Integer frequency = document.getInteger("frequency");
	   	System.out.println("printing documents in collection");
	   	System.out.println("type:  " + type);
	   	System.out.println("url:  " + url);
	   	System.out.println("word:  " + word);
	   	System.out.println("frequency:  " + frequency.toString());
	   	}
	}	
}


