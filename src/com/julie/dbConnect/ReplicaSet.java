package com.julie.dbConnect;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBAddress;
import com.mongodb.Mongo;

public final class ReplicaSet {
	
	    private static final int [] ports = { 27017, 27018, 27019 };

	    public void setupMongo() throws Exception {

	        final BasicDBObject config = new BasicDBObject("_id", "rsconfig");

	        final List<BasicDBObject> servers = new ArrayList<BasicDBObject>();

	        int i=0;
	        for (final int port : ports) {
	            final BasicDBObject server = new BasicDBObject("_id", i);
	            server.put("host", ("127.0.0.1:" + port));

	            // We're going to specify that only the first server can be the master.
	            // Additionally, we're going to configure the slaves to be 5 seconds behind
	            // the master.
	            if (i > 0 && i != 3) {
	                server.put("priority", 0);
	                server.put("slaveDelay", 1);
	            }

	            if (i == 2) server.put("arbiterOnly", true);
	            	servers.add(server);
	            	i++;
	        	}

	        config.put("members", servers);

	        final Mongo mongo = new Mongo(new DBAddress("127.0.0.1", 27017, "admin"));

	        final CommandResult result
	        = mongo.getDB("admin").command(new BasicDBObject("replSetInitiate", config));

	        System.out.println(result);

	        // Sleep for a bit to wait for all the nodes to be intialized.
	        Thread.sleep(5000);
	    }
}
