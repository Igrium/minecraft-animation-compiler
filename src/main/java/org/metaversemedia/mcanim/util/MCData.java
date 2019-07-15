package org.metaversemedia.mcanim.util;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class responsible for writing Minecraft data tags in the right format
 * @author sam54123
 *
 */
public class MCData {
	
	/**
	 * Formats a value to be read by Minecraft
	 * @param value
	 * @return formatted value
	 */
	public static String formatValue(Object value) {
		if (value instanceof JSONObject) {
			return formatObject((JSONObject) value);
		} else if (value instanceof JSONArray) {
			return formatArray((JSONArray) value);
		} else if (value instanceof Float) {
			return formatFloat((Float) value, true);
		} else if (value instanceof Double) {
			return formatFloat(((Double) value).floatValue(), true);
		} else if (value instanceof String) {
			return "\""+value+"\"";
		} else {
			return value.toString();
		}
	}
	
	/**
	 * Write a JSONObject in a format that can be read by Minecraft
	 * @param object to write
	 * @return data string
	 */
	public static String formatObject(JSONObject object) {
		String output = "{";
		
		// Iterate through all keys
		Iterator<String> keys = object.keys();
		
		while(keys.hasNext()) {
		    String key = keys.next();
		    output = output.concat(key+":"+formatValue(object.get(key)));
		    // Make sure we have the commas
		    if (keys.hasNext()) {
		    	output = output.concat(",");
		    }
		}
		
		return output.concat("}");
	}
	
	public static String formatArray(JSONArray array) {
		String output = "[";
		
		// Iterate over array
		for (int i = 0; i < array.length(); i++) {
			output = output.concat(formatValue(array.get(i)));
			// Add commas
			if (i < array.length()-1) {
				output = output.concat(",");
			}
		}
		return output.concat("]");
	}
	
	/**
	 * Format a float for Minecraft
	 * @param float
	 * @param should I include the f at the end of the float?
	 * @return formatted float
	 */
	public static String formatFloat(float value, boolean includeF) {
		String output = String.format("%.10f",value);
		if (includeF) {
			output = output+"f";
		}
		return output;
	}
	
	
}
