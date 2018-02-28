package com.julie.dbConnect;

import com.mongodb.MongoClient;

public class MongoClientS {
	
		private static MongoClientS instance = null;
		private static MongoClient mongo;
		
		private MongoClientS() {
			
			mongo = new MongoClient();
		}
		
		public static MongoClientS getInstance() {
			if(instance == null) {
		         instance = new MongoClientS();
			}
			return instance;
		}
		
		protected static MongoClient getMongoClient(String host, int port) {
			mongo = new MongoClient(host, port);
			return mongo;
		}

	}
