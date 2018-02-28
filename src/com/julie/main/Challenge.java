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
import com.julie.dbConnect.*;

/**
 * @author Julie.Meese
 *
 */
public class Challenge extends Thread {
	
	private static Logger logger = Logger.getRootLogger();
	private static Shard shard = new Shard();
	//private static MongoCollection<Document> collection;
	//private static MongoDatabase database;
	private static ConnectDB conn;
	private static String collname; // = "wordsCollection";
	private static String dbname; //= "challengeDb";
	private static String url; // = "D:\\Workspaces\\JAVA DEV\\Platform Engineer Challenge\\src\\com\\julie\\main\\partfile.txt";//"D:\\Downloads\\enwiki-latest-pages-articles-multistream.xml";
	private static DisplayConsole console = new DisplayConsole();
	private static String host;
	private static Integer port;
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		logger.info("Initialising Challenge...");
		
		if(args.length > 0) {
			
			url = ".//" + args[1];
			dbname = args[3];
			collname = args[5];
			host = args[7];
			port = Integer.parseInt(args[9]);
			conn = new ConnectDB();
			conn.setHost(host);
			conn.setPort(port);
		}
		
		//shard.setupCluster();
		
		Challenge challenge = new Challenge();

		try{
			challenge.init();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		console.display();
	}
	
	
	private void connect() {
		System.out.println("Dropping Collection: " + collname);
		conn.dropCollection();
		System.out.println("Dropping Database: " + dbname);
		conn.dropDatabase();
		conn.createDatabase(dbname);
		conn.createCollection(collname);
		//database = conn.getDatabase();
		//collection = conn.getCollection();
		//conn.shardCollection();
	}
	
	private void init() throws IOException{
		connect();
		FileProcessor proc = new FileProcessor(url);
		logger.info("Starting processing input file...");
		Map<String, Integer> words = proc.countDistinctWords();
		System.out.println("Words Map Size = " + words.size());
		System.out.println("Empty? :  " + words.entrySet().isEmpty());
		logger.info("Finished processing input file...");
		
		String type = "word";
		//String url = "D:\\Workspaces\\JAVA DEV\\Platform Engineer Challenge\\src\\com\\julie\\main\\partfile.txt";
		//List<Document> documents = new ArrayList<Document>();
		
		for(Map.Entry<String, Integer> entry : words.entrySet()) {
			 if(!(entry.getKey().isEmpty())){
				 String word = entry.getKey();
				 Integer frequency = entry.getValue();
				 System.out.println("word: " + word);
			     System.out.println("frequency: " + frequency);
			     
				 Document doc = new Document("type", type).append("url", url).append("word", word).append("frequency", frequency);
				 conn.getCollection(collname).insertOne(doc);	 
			 }
		}
		
		String fieldtoindex = "word";
		conn.createIndex(collname, fieldtoindex);
		(new Thread(new DisplayConsole())).start();
		Thread.currentThread().setPriority(NORM_PRIORITY);
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
	
	public String getUrl() {
		return url;
	}
	
	public String getHost() {
		return host;
	}
	
	public Integer getPort() {
		return port;
	}
}


