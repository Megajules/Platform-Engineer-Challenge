/**
 * 
 */
package com.julie.factory.documents;

import java.util.Map;

import org.bson.Document;

/**
 * @author Julie.Meese
 *
 */
public interface DocumentFactory {
	
	public Document createDocument(String type, String url, Map<String, Integer> wordFrequency);
}
