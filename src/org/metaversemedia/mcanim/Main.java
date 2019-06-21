package org.metaversemedia.mcanim;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.metaversemedia.mcanim.compiler.BaseCompiler;

public class Main {

	public static void main(String[] args) {
		// Check usage
		if (args.length < 2) {
			System.out.println("Usage: compiler <input file> <destination path>");
			System.exit(64);
		}
		
		// Load JSONObject from disk
		System.out.println("Loading file: "+args[0]);
		JSONObject animation = null;
		try {
			animation = loadJSON(args[0]);
			// Set name of animation
			animation.put("name", getFilename(args[0]));
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to read file!");
			System.exit(65);
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("Improperly formatted JSON!");
			System.exit(65);
		}
		
		// Compile animation
		System.out.println("Writing function");
		try {
			BaseCompiler.smartCompile(animation, Paths.get(args[1]));
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(65);
		}
		
		System.out.println("Compilation complete!");
	}
	
	private static JSONObject loadJSON(String inputPath) throws IOException, JSONException {
		Path animationFile = Paths.get(inputPath);
		
		List<String> jsonFile = Files.readAllLines(animationFile);
		JSONObject animation = new JSONObject(String.join("", jsonFile));
		
		return animation;
	}
	
	/**
	 * Gets the name of a file without the extension
	 * @param filePath
	 * @return
	 */
	private static String getFilename(String filePath) {
		File file = new File(filePath);
		String fileName = file.getName();
		
		// Return filename without extension
		if (fileName.indexOf(".") > 0) {
			   return fileName.substring(0, fileName.lastIndexOf("."));
			} else {
			   return fileName;
			}
		
	}

}
