/**
 * 
 */
package com.julie.processFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.julie.main.Challenge;

/**
 * @author Julie.Meese
 *
 */
public class FileProcessor {
	
	private static String url = "";
	
	public FileProcessor(String url){
		this.url = url;
	}
	
	private static Logger logger = Logger.getRootLogger();

	private static final long MAX_WORDS = 5000;
	
	public String getFileAsString() {
		String file = "";
		return file;
	}
	
	public String getPartialFileAsString() {
		String content = "";
		return content;
	}
	
	public Stream<String> getFileAsStream() throws IOException {
		
		String file = url;
		String path = "D:\\Downloads\\" + file;
	    Stream<String> stream = Files.lines(Paths.get(path));
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
	
	public Map<String, Integer> countDistinctWords() throws IOException {
		
		Map<String, Integer> fullListWithCount = new HashMap<String, Integer>();
		ArrayList<String> fullwordlist = new ArrayList<String>();
		Map<String, Integer> finalMap = new HashMap<String, Integer>();

		//first get the file into a stream  - TODO split stream for larger files
		logger.info("streaming file using fullwordstream...");
		Stream<String> fullwordstream = Files.lines(Paths.get(url
				)).parallel().map(line -> line.split("[[^\\w]_\\d]+")).flatMap(Arrays::stream);

		logger.info("Adding the fullwordstream to the arraylist fullwordlist...");
		fullwordstream.parallel().collect(Collectors.toCollection(() -> fullwordlist));
		
		logger.info("calculating the frequency value of the key(word) in the fullwordlist...");
			 for(String key : fullwordlist) {
 
				                                               if(!(key.isEmpty())){
				 Integer value = Collections.frequency(fullwordlist, key);
		logger.info("adding the keys(word) and values(frequency) to the hashmap fullListWithCount");		 
				 fullListWithCount.put(key, value);
				 }
		logger.info("getting distinct values from the fullwordlist and adding them to the fullwordlistDistinct...");
		List<String> fullwordlistDistinct = fullwordlist.stream().parallel().distinct().collect(Collectors.toList());
		logger.info("calculating the frequency of the keys in the fullwordlist using the distinctkey (word from the fullwordlistDistinct..." );
		for(String distinctkey : fullwordlistDistinct){
			Integer value = Collections.frequency(fullwordlist, distinctkey);
		logger.info("adding the word " + distinctkey + " and its frequency " +  value + " to the Map finalMap..." );
			finalMap.put(distinctkey, value);
		}
		}
		return finalMap;
	}	
	
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
	
}
