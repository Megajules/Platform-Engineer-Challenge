package com.julie.dbConnect;
/**
 * @author Julie.Meese
 *
 */

import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.julie.dbConnect.*;
import com.julie.main.Challenge;

public class Query {
	private static Integer maxFrequency;
	private static Integer minFrequency;
	private static ConnectDB conn = new ConnectDB();
	private static String collname = new Challenge().getCollname();
	
	public final Integer getMaxFrequency(){
		//First get the max frequency
		BasicDBObject query = new BasicDBObject();
		
		query.put("frequency", -1);
		FindIterable<Document> cursor = conn.getDatabase().getCollection(collname).find().sort(query);
		Document maxFrequencyDoc = cursor.first();
		maxFrequency = maxFrequencyDoc.getInteger("frequency");
		 return maxFrequency;
	}
	
	public final Integer getMinFrequency(){
		//First get the min frequency
		BasicDBObject query1 = new BasicDBObject();
		query1.put("frequency", 1);
		FindIterable<Document> cursor2 = conn.getDatabase().getCollection(collname).find().sort(query1);
		Document minFrequencyDoc = cursor2.first();
		minFrequency = minFrequencyDoc.getInteger("frequency");
		return minFrequency;
	}
	
		public final void getDocumentsWithMaxFrequency() {	
			//Then get the documents that have the max frequency
			BasicDBObject queryA = new BasicDBObject();
			queryA.put("frequency", maxFrequency);
			FindIterable<Document> cursor1 = conn.getDatabase().getCollection(collname).find().filter(queryA);
			System.out.println("SingleMaxResult = " + maxFrequency);
			System.out.println("Words with Max Frequency");
			cursor1.forEach((Block<Document>) System.out::println);
		}
				
			
			
		public final void getDocumentsWithMinFrequency() {
			//Then get the documents that have the min frequency
			BasicDBObject queryB = new BasicDBObject();
			queryB.put("frequency", minFrequency);
			FindIterable<Document> cursor3 = conn.getDatabase().getCollection(collname).find().filter(queryB);
			System.out.println("SingleMinResult = " + minFrequency);
			System.out.println("Words with Min Frequency");
			cursor3.forEach((Block<Document>) System.out::println);	
		}
		
		public final void getDocumentsWithXFrequency(Integer frequencyX) {
			BasicDBObject queryX = new BasicDBObject();
			queryX.put("frequency", frequencyX);
			try {
				FindIterable<Document> cursor5 = conn.getDatabase().getCollection(collname).find().filter(queryX);
				System.out.println("Words with Frequency of " + frequencyX );
				if(cursor5 != null){
					cursor5.forEach((Block<Document>) System.out::println);
				} else {
					System.out.println("There are no words with Frequency of " + frequencyX);
				}
			} catch(Exception e){
				System.out.println("Oops something went wrong!  Run Analysis [menu item 3], and then try again.");
				e.printStackTrace();
			}
			
		}
					
	public final void getWordsBeginningWith(String letter) {
		BasicDBObject queryLetter = new BasicDBObject();
		BasicDBObject regex = new BasicDBObject();
		Pattern pattern = Pattern.compile("/^" + letter + "/");
		regex.put("regex", pattern );
		queryLetter.put("word", regex );
		FindIterable<Document> cursor4 = conn.getDatabase().getCollection(collname).find().filter(new BasicDBObject("word","/^T/"));
		cursor4.forEach((Block<Document>) System.out::println);	
	}
}
