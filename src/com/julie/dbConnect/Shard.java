/**
 * 
 */
package com.julie.dbConnect;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBAddress;
import com.mongodb.Mongo;


/**
 * @author Julie.Meese
 *
 */
@SuppressWarnings("deprecation")
public class Shard {
	
	 private static final int [] shardPorts = { 27018, 27019 }; 
	  
	    public void setupCluster() throws Exception { 
	 
	        // Connect to mongos 
	        final Mongo mongo = new Mongo(new DBAddress("127.0.0.1", 27017, "admin"));
	 
	        // Add the shards 
	        for (final int shardPort : shardPorts) { 
	            final CommandResult result 
	            = mongo.getDB("admin").command(new BasicDBObject("addshard", ("localhost:" + shardPort))); 
	            System.out.println("Result: " + result); 
	        } 
	 
	        // Sleep for a bit to wait for all the nodes to be intialized. 
	        Thread.sleep(5000); 
	 
	        // Enable sharding on a collection. 
	        CommandResult result = mongo.getDB("admin").command(new BasicDBObject("enablesharding", "test")); 
	        System.out.println(result); 
	 
	        final BasicDBObject shardKey = new BasicDBObject("word", 1); 
	        shardKey.put("hash", 1); 
	 
	        final BasicDBObject cmd = new BasicDBObject("shardcollection", "challengeDb.wordsCollection"); 
	 
	        cmd.put("key", shardKey); 
	 
	        result = mongo.getDB("admin").command(cmd); 
	 
	        System.out.println(result); 
	    } 

}
