/**
 * 
 */
package com.julie.processFiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
//import java.util.HashSet;
import java.util.List;
import java.util.Map;
//import java.util.Set;
//import java.util.Spliterator;
//import java.util.TreeSet;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//import java.util.function.Predicate;
//import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import org.bson.Document;
import org.json.JSONObject;
import org.json.XML;

import com.julie.dbConnect.ConnectDB;
//import com.julie.factory.documents.WordDocument;
//import com.mongodb.DBObject;
//import com.mongodb.MongoClient;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.reactivestreams.client.MongoClient;
//import com.mongodb.util.JSON;

import org.apache.log4j.*;


/**
 * @author Julie.Meese
 *
 */
public class FileProcessor extends Thread {
	
	private static Logger logger = Logger.getRootLogger();
	private static String url1 = "D:\\Downloads\\enwiki-latest-pages-articles-multistream.xml";
	private static String url2 = "D:\\Workspaces\\JAVA DEV\\Platform Engineer Challenge\\src\\com\\julie\\main\\xmlexample.xml";
	private static String url3 = "D:\\Workspaces\\JAVA DEV\\Platform Engineer Challenge\\src\\com\\julie\\main\\partfile.txt";
	private ConnectDB conn = new ConnectDB();
	//private static Collector<String,String, ConcurrentMap<String, List<String>>> collector;
	//private ConcurrentMap<String, Object> map = new ConcurrentHashMap<String, Object>();
	private static final long MAX_WORDS = 5000;
	
	
	public String getFileAsString() {
		String file = "";
		return file;
	}
	
	public String getPartialFileAsString() {
		String content = "";
		return content;
	}
	
	// -----------Jules Choice ------------
	//Gets the file as a stream then puts it into a list
	public Stream<String> getFileAsStream() throws IOException {
		
		//String file = url1;
		String file = url1;
		String path = "D:\\Downloads\\" + file;
	    Stream<String> stream = Files.lines(Paths.get(path));
	    //stream.forEach(System.out::println);
	    return stream;
	}
	
	public List<String> convertStreamToList(Stream<String> stream) throws IOException {
		 int maxSize = 100000;
		 stream = getFileAsStream().limit(maxSize); 
		 //Convert stream to a list
		List<String> result = stream.collect(Collectors.toList());
		result.forEach(System.out::println);
		return result;
	}
	
	// -----------End Jules Choice ------------
	
	
	// -----------Jules Choice Revised ------------
//	public ArrayList<String> extractDistinctWords() throws IOException {
//		
//		Stream<String> lines = Files.lines(Paths.get(url1)).map(line -> line.split("[[^\\w]_\\d]+")).flatMap(Arrays::stream).distinct();
//		ArrayList<String> wordlist = new ArrayList<String>();
//		lines.sequential().collect(Collectors.toCollection(() -> wordlist));
//		System.out.println("items:  ");  //DEBUG
//		for(String item : wordlist) {	 //DEBUG
//			System.out.println(item);    //DEBUG
//		}
//		
//		lines.close();
//		return wordlist;	
//	}
//	
//	public ArrayList<String> extractAllWords() throws IOException {
//		Stream<String> lines = Files.lines(Paths.get(url2)).map(line -> line.split("[[^\\w]_\\d]+")).flatMap(Arrays::stream);
//		ArrayList<String> fullwordlist = new ArrayList<String>();
//		lines.close();
//		return fullwordlist;
//	}
	
	public Map<String, Integer> countDistinctWords() throws IOException {
		
		Map<String, Integer> fullListWithCount = new HashMap<String, Integer>();
		ArrayList<String> fullwordlist = new ArrayList<String>();
		Map<String, Integer> finalMap = new HashMap<String, Integer>();
		//Long maxSize = 10000L;

		//first get the file into a stream  - TODO split stream for larger files
		logger.info("streaming file using fullwordstream...");
		Stream<String> fullwordstream = Files.lines(Paths.get(url3)).parallel().map(line -> line.split("[[^\\w]_\\d]+")).flatMap(Arrays::stream);
		//fullwordstream.collect(Collectors.partitioningBy(p -> fullwordstream.count() > maxSize));
		
		
		//ADD THE FULLWORDSTREAM TO THE ARRAYLIST FULLWORDLIST
		logger.info("Adding the fullwordstream to the arraylist fullwordlist...");
		fullwordstream.parallel().collect(Collectors.toCollection(() -> fullwordlist));
		
		//use the keys(words) from the fullwordlist to calculate the frequency of the word in the list 
		//List<String> fullwordlistDistinct;
		logger.info("calculating the frequency value of the key(word) in the fullwordlist...");
			 for(String key : fullwordlist) {
				 if(!(key.isEmpty())){
				 Integer value = Collections.frequency(fullwordlist, key);
		logger.info("adding the keys(word) and values(frequency) to the hashmap fullListWithCount");		 
				 fullListWithCount.put(key, value);
				 //System.out.println(key + ": xx" + value); 
			 //System.out.println(wordcount.entrySet().iterator().next().getKey());
			 //System.out.println(wordcount.entrySet().iterator().next().getValue());
				 }
		logger.info("getting distinct values from the fullwordlist and adding them to the fullwordlistDistinct...");
		List<String> fullwordlistDistinct = fullwordlist.stream().parallel().distinct().collect(Collectors.toList());
		logger.info("calculating the frequency of the keys in the fullwordlist using the distinctkey (word from the fullwordlistDistinct..." );
		/*String type = "Word Frequency Document";
		String url = url3;*/
		for(String distinctkey : fullwordlistDistinct){
			Integer value = Collections.frequency(fullwordlist, distinctkey);
		logger.info("adding the word and its frequency to the Map finalMap..." );
			finalMap.put(distinctkey, value);
			System.out.println("Final Map Size: " + finalMap.size());
			System.out.println("FinalMap : " + finalMap.containsKey(distinctkey) +  ", " + finalMap.containsValue(value));
			System.out.println("word: " + distinctkey);
			System.out.println("frequency: " + value);
			System.out.println("\n");
		}
		}
		return finalMap;
	}	
		

	
	// -----------End Jules Choice Revised ------------
	
	
	
	public Map<Boolean,List<String>> partitionFile(Stream<String> words) {
		Map<Boolean, List<String>> wordcount = words.collect(Collectors.partitioningBy(noOfWords -> words.count() > MAX_WORDS));
		return wordcount;
	}
	
	public void splitCollectionForProcessing() {
		
	}
	
	public List<String> getWordsToList(Stream<String> words) {
		 List<String> list = words.distinct().collect(Collectors.toList());
		 return list;
	}
	
	public List<String> getDistinctWords(Stream<String> words) {
		List<String> list = words.distinct().collect(Collectors.toList());
		return list;
	}
	
	public Map<String,Integer> getDistinctWordsAndCountToMap(Stream<String> words) {
		 
		Map<String,Integer> wordmap = new HashMap<String, Integer>();
		return wordmap;
	}
	
	/*public void splitStringIntoWords(Stream<String> words) {
		String separators = "^[^a-zA-Z0-9-]*$";
		String[] parts = words.toString().split(separators);
	}*/
	
	
	
	/*Implementations of Collector that implement various useful reduction operations, such as accumulating elements into collections, summarizing elements according to various criteria, etc. 

	The following are examples of using the predefined collectors to perform common mutable reduction tasks: 

	// Accumulate names into a List
	     List<String> list = people.stream().map(Person::getName).collect(Collectors.toList());

	     // Accumulate names into a TreeSet
	     Set<String> set = people.stream().map(Person::getName).collect(Collectors.toCollection(TreeSet::new));

	     // Convert elements to strings and concatenate them, separated by commas
	     String joined = things.stream()
	                           .map(Object::toString)
	                           .collect(Collectors.joining(", "));

	     // Compute sum of salaries of employee
	     int total = employees.stream()
	                          .collect(Collectors.summingInt(Employee::getSalary)));

	     // Group employees by department
	     Map<Department, List<Employee>> byDept
	         = employees.stream()
	                    .collect(Collectors.groupingBy(Employee::getDepartment));

	     // Compute sum of salaries by department
	     Map<Department, Integer> totalByDept
	         = employees.stream()
	                    .collect(Collectors.groupingBy(Employee::getDepartment,
	                                                   Collectors.summingInt(Employee::getSalary)));

	     // Partition students into passing and failing
	     Map<Boolean, List<Student>> passingFailing =
	         students.stream()
	                 .collect(Collectors.partitioningBy(s -> s.getGrade() >= PASS_THRESHOLD));*/
	
public void xmlToJson() {
		
	    String fileName = "D:\\tempjson.txt";
	    try {           
	        
	    	
	        
	        
	       
			
	        StringBuilder builder =  new StringBuilder();  
//	        int ptr = 0;  
//	        while ((ptr = inputStream.read()) != -1 ) {  
//	            builder.append((char) ptr);
//	            //System.out.println(ptr);
//	        }  
	
	        String xml  = builder.toString();  
	        JSONObject jsonObj = XML.toJSONObject(xml);   
	         System.out.println(jsonObj.toString()); 
	         System.out.println(jsonObj.toString().split(",").length);
	        // Assume default encoding.
	        FileWriter fileWriter =
	            new FileWriter(fileName);
	
	        // Always wrap FileWriter in BufferedWriter.
	        BufferedWriter bufferedWriter =
	            new BufferedWriter(fileWriter);
	
	        // Always close files.
	
	        for(int i= 0 ;i < jsonObj.toString().split(",").length; i ++) {
	           System.out.println(jsonObj.toString().split(",")[i]);
	           bufferedWriter.write(jsonObj.toString().split(",")[i]);
	           bufferedWriter.write("\n");
	        }
	
	        bufferedWriter.close();
	   // }
	        
	        
	        //______________________________________________________________________________
	        
	        String path = "D:\\Workspaces\\JAVA DEV\\Platform Engineer Challenge\\src\\com\\julie\\main\\";
	        String xmlString = "";
			//File file2 = new File(path + "partfile.txt");
	        //File file2 = new File(path + "xmlexample.txt");
			BufferedReader reader = null;	
			
			try {
				String line;
				reader = Files.newBufferedReader(Paths.get(path + "xmlexample.xml"));
			
				while((line = reader.readLine()) != null) {
					System.out.println(line);
					if(line.length() > 0 ) {
					xmlString += reader.readLine();
				}
			}
				
				//System.out.print(partfile);
			} catch (IOException e) {
				e.printStackTrace();
			}	
			
			
	        //______________________________________________________________________________ 
	      
	        //System.out.print("xmlString:  " + xmlString);
	        JSONObject jsonObj2 = XML.toJSONObject(xmlString);
	        //JSONArray jsonArr = jsonObj2.getJSONArray("food");
	        //System.out.println(jsonObj2.toString());
//	        for (int i = 0; i < jsonArr.length(); i++) {
//	        	System.out.println(jsonArr.toString());
//	        }
	    }
	        
	      catch(IOException ex) {
	            System.out.println(
	                "Error writing to file '"
	                + fileName + "'");
	            // Or we could just do this:
	             ex.printStackTrace();
	        } catch(Exception e) {  
	            e.printStackTrace();  
	        }
	}
}
