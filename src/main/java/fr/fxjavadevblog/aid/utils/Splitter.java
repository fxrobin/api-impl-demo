package fr.fxjavadevblog.aid.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Utility class to Split a string into multiple string and returning a List<String>.
 * 
 * @author robin
 *
 */
public class Splitter {
	
	public static final String DEFAULT_DELIM = ",|";
	
	private Splitter() {
		// protection
	}
	
	/**
	 * splits a String with the default delimiters "," or "|". They can be mixed.
	 * 
	 * @param source
	 * @return
	 */
	public static List<String> split(String source)
	{
		return split(source, DEFAULT_DELIM);
	}
	
	/**
	 * splits a String with the given delimiters.
	 * 
	 * @param source
	 * @param delimiters
	 * @return
	 */
	public static List<String> split(String source, String delimiters)
	{
		String[] splitted = StringUtils.split(source, delimiters);
        return Arrays.asList(splitted);
	}

}
