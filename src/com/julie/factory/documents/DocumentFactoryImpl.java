package com.julie.factory.documents;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.Document;

public class DocumentFactoryImpl implements DocumentFactory {

	private static Logger logger = Logger.getRootLogger();
	public DocumentFactoryImpl() {
	}

	public Document createDocument(String type, String url, Map<String, Integer> wordFrequency) {
		Document document = null;
		if(type == "word") {
			
			document = new WordDocument();
			
			Map<String, String> m = new HashMap<String, String>();
			
			m.put("type", "word");
			m.put("url", url);
			m.put("word", "");
			m.put("frequency", "");
			
			document.putAll(m);
		}
		return document;
	}	
}
