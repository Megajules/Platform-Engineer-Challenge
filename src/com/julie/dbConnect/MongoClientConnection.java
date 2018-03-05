package com.julie.dbConnect;

import java.util.Arrays;
import org.apache.log4j.Logger;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MongoClientConnection {
	private static Logger logger = Logger.getRootLogger();
	private static MongoClientConnection instance = null;
	private static MongoClient mongo;
	
	
	
	private MongoClientConnection() {
		
		//mongo = MongoClientS.getInstance().getMongoClient("localhost" , 27017);
	      //mongo = new MongoClient( host, port );
	   //mongo = new MongoClient(
	   //mongo = MongoClientS.getInstance().getMongoClient("localhost" , 27017);
	   
//	   mongo = new MongoClient( "localhost" , 27017 );  //standalone server connection
	   
//	    mongo = new MongoClient(
//	    new MongoClientURI("mongodb://localhost:27017,localhost:27018,localhost:27019"));  //replica set server connection
	  
	   mongo = new MongoClient(             //replica set server connection
			   Arrays.asList(
				new ServerAddress("localhost",27017),
				new ServerAddress("localhost",27018),
				new ServerAddress("localhost",27019)
			   )
		   );
			   
	      logger.info("Mongo Client created successfully");
	      
	}
	
//	public static MongoClient getMongoClientInstance() {
//		 if(mongo == null) {
//			mongo = createMongoClient();
//		 }
//		 return mongo;
//	 }
	public static MongoClient getInstance() {
		if(instance == null) {
	         instance = new MongoClientConnection();
		}
		return mongo;
	}
	
	private static MongoClient getMongoClient() {
		return mongo;
	}

}
