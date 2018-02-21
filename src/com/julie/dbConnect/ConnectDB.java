/**
 * 
 */
package com.julie.dbConnect;

import org.apache.log4j.Logger;
import org.bson.Document;
//import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
//import com.mongodb.util.JSON;
//import com.mongodb.DBObject;
import com.mongodb.client.model.Indexes;

//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.JSONException;
//import org.json.XML;

//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.*;

/**
 * @author Julie.Meese
 *
 */
public class ConnectDB {
	private static Logger logger = Logger.getRootLogger();
	private static MongoDatabase database;
	private static MongoClient mongo;
	
	 public static MongoClient createMongoClient() {
		   logger.info( "Starting create Mongo Client...");
		// Creating a Mongo client 
		      mongo = new MongoClient( "localhost" , 27017 );
		      logger.info("Mongo Client created successfully");
		      return mongo;
	   }
	 
	public static MongoClient getMongoClient(){
		return mongo;
	}
	
	public void dropDatabase() {
		logger.info("Dropping database");
		if(database != null) {
			database.drop();
		}
	}
	public void createDatabase() {
		   	 logger.info("Starting create or get database...");
		 // creating the database
		     database = createMongoClient().getDatabase("challengeDb");
		     logger.info("Connected to the database successfully");
	   }
	
	public void createDatabase(String dbname) {
	   	 logger.info("Starting create or get database...");
	 // creating the database
	     database = createMongoClient().getDatabase(dbname);
	     logger.info("Connected to the database successfully");
  }
	
	public void createCollection(){
	      logger.info( "Starting create collection..."); 
		  if(database.getCollection("challengeCollection") == null) {
		      database.createCollection("challengeCollection"); //default collection
		      logger.info("Collection created successfully");
	      } else {
	    	  logger.info("challengeCollection already exists." );
	      }
	}
	
	public void createCollection(String name){
	      logger.info( "Starting create collection...");
	      if(database.getCollection(name) == null) {
	    	  database.createCollection(name);
	          logger.info("Collection created successfully");
	      } else {
	    	  logger.info("Collection with name " + name + " already exists." );
	      }
	      
	}
	
	public void createIndex(String collname, String fieldtoindex) {
		String index = database.getCollection(collname).createIndex(Indexes.ascending(fieldtoindex));
		logger.info("index created: " + index);
	}
	
	public MongoCollection<Document> getCollection(String collname){
		MongoCollection<Document> collection;
	   	  logger.info("getting the collection...");
	   	  try{
	   		  createMongoClient();
	   		  createDatabase();
	   		  collection = database.getCollection(collname);
	   	  }catch(NullPointerException npe) {
	   		  logger.info("collection does not exist...creating collection");
	   		  database.createCollection(collname);
	   		  collection = database.getCollection(collname); 
	   	  }
	   	logger.info("successful get collection");
	   	return collection;
	}

}
