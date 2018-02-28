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
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBObject;
import com.mongodb.MongoClientURI;
import com.mongodb.ReplicaSetStatus;
//import com.mongodb.Mongo;
import com.mongodb.client.model.Indexes;
import com.julie.dbConnect.MongoClientS;

/**
 * @author Julie.Meese
 *
 */
public class ConnectDB {
	private static Logger logger = Logger.getRootLogger();
	private static MongoDatabase database;
	private static MongoClient mongo;
	private static MongoCollection<Document> collection;
	private static String collname = "wordsCollection";
	private static String dbname = "challengeDb";
	private static String host = "";
	private static Integer port = 0;
	
	 public static MongoClient createMongoClient() {
		   logger.info( "Starting create Mongo Client...");
		   //mongo = MongoClientS.getInstance().getMongoClient("localhost" , 27017);
		      mongo = new MongoClient( host, port );
		   //mongo = new MongoClient(
		   //mongo = MongoClientS.getInstance().getMongoClient("localhost" , 27017);
		      //mongo = new MongoClient( "localhost" , 27017 );
		   
		    mongo = new MongoClient(
		    new MongoClientURI("mongodb://localhost:27017,localhost:27018,localhost:27019"));
		      logger.info("Mongo Client created successfully");
		      
		      ReplicaSetStatus status = mongo.getReplicaSetStatus();
		      System.out.print("Replica Set Status " + status);
		      return mongo;
	   }
	 
	 public void setHost(String host){
		 this.host = host;
	 }
	 
	 public void setPort(Integer port){
		 this.port = port;
	 }
	 
	public static MongoClient getMongoClient(){
		return mongo;
	}
	
	public MongoCollection<Document> getCollection() {
		return collection = database.getCollection(collname);
	}
	
	public MongoDatabase getDatabase() {
		database = mongo.getDatabase(dbname);
		return database;
	}
	
	
	
	public void dropDatabase() {
		logger.info("Dropping database");
		if(database != null) {
			database.drop();
		}
	}
	
	public void dropCollection() {
		logger.info("Dropping collection");
		if(getCollection(collname) != null) {
			database.getCollection(collname).drop();
		}
	}

	
	public void createDatabase() {
		   	 logger.info("Starting create or get database...");
		     database = createMongoClient().getDatabase("challengeDb");
		     logger.info("Connected to the database successfully");
	   }
	
	public void createDatabase(String dbname) {
	   	 logger.info("Starting create or get database...");
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
	
	public void shardCollection() {
		
		final DBObject cmd = new BasicDBObject("shardCollection", "challengeDb.wordsCollection").
				  append("shardkey", new BasicDBObject("_id", "hashed"));
			@SuppressWarnings("deprecation")
			CommandResult result = mongo.getDB("challengeDb").getSisterDB("admin").command(cmd);
			System.out.println("CommandResult:  " + result);
	}

}
