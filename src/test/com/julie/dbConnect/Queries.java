/**
 * 
 */
package test.com.julie.dbConnect;

/**
 * @author Julie.Meese
 *
 */
public class Queries {
	
	public static final String GETMAX = "db.collection.find().sort({frequency:+1});";
	public static final String GETMIN = "db.collection.find().sort({age:-1});";
	public static final Integer LIMIT1 = 1;
	public static final Integer LIMIT10 = 10;
	public static final Integer LIMIT50 = 50;
	
	public static final String getMaxFrequency(){
		 return GETMAX;
	}
	
	public static final String getMinFrequency(){
		 return GETMIN;
	}

}
