/**
 * 
 */
package com.julie.factory.documents;

import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.Document;

/**
 * @author Julie.Meese
 *
 */
public class WordDocument extends Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getRootLogger();

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		super.putAll(m);
	}	
}
