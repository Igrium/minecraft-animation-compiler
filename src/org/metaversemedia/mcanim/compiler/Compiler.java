package org.metaversemedia.mcanim.compiler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.metaversemedia.mcanim.Constants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Path;

/**
 * Base compiler class for different types of compilers to use
 * @author Igrium
 *
 */
public abstract class Compiler {
	
	/**
	 * Compile a JSON object used to store animations in production to a runnable function file
	 * @param input JSON object to compile
	 * @param outputDir Directory to compile to
	 * @throws JSONException, IOException
	 */
	public void compile(JSONObject animation, Path outputDir) throws JSONException, IOException {
		// Get the filename from the file
		String filename = "anim"+Integer.toString(animation.getInt("id"));
		
		// Generate writer
		File outputFile = new File(outputDir.toString(), filename);	
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		
		writeMetadata(animation, writer);
		
		// Iterate over frames
		JSONArray frames = animation.getJSONArray("frames");
		for (int i = 0; i < frames.length(); i++) {
			compileFrame(animation, frames.getJSONObject(i), i, writer);
			writer.newLine();
		}
		writer.newLine();
		
		// Write commands
		JSONArray commands = animation.getJSONArray("commands");
		for (int i = 0; i < commands.length(); i++) {
			writeCommand(commands.getJSONObject(i), writer);
			writer.newLine();
		}
		
		writer.write("scoreboard players add @s "+ Constants.FRAMEOBJECTIVE + " 1");
		
		writer.close();
	}
	
	// Writes the metadata at the top of the animation
	private void writeMetadata(JSONObject animation, BufferedWriter writer) throws IOException, JSONException {
		writer.write("# Created with Igrium's Minecraft Animation System");
		writer.newLine();
		writer.write("# length: "+animation.getJSONArray("frames").length()+", name: "+animation.getString("name"));
		writer.newLine();
		writer.newLine();
	}
	
	/**
	 * Write a command object to the file
	 * @param command command to write
	 * @param writer writer to write to
	 * @throws JSONException
	 * @throws IOException
	 */
	protected void writeCommand(JSONObject command, BufferedWriter writer) throws JSONException, IOException {
		int frame = command.getInt("frame");
		String contents = command.getString("contents");
		writer.write("execute at @s if score @s " + Constants.FRAMEOBJECTIVE + " matches " + frame + " run " + contents);
		writer.newLine();
	}
	
	/**
	 * Compile a frame of animation. A new line is automatically added after each frame To be implemented by children
	 * @param animation animation being compiled
	 * @param frameObject frame object to compile
	 * @param frame frame number
	 * @param writer writer to write to
	 * @return success
	 */
	public abstract void compileFrame(JSONObject animation, JSONObject frameObject, int frame, BufferedWriter writer) throws JSONException, IOException;
}