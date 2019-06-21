package org.metaversemedia.mcanim.compiler;

import java.io.BufferedWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.metaversemedia.mcanim.Constants;
import org.metaversemedia.mcanim.util.Vector;

/**
 * Basic compiler that moves entity around based on xyz/yp channels.
 * Designed with basic, non-articulated objects (works on all entities)
 * @author Igrium
 *
 */
public class TransformCompiler extends BaseCompiler {
	

	@Override
	public void compileFrame(JSONObject animation, JSONObject frameObject, int frame, BufferedWriter writer)
			throws JSONException, IOException {
		
		// Get previous frame and calculate relative coordinates if not frame 1
		Vector relativeCoords = null;
		
		if (frame != 0) {
			JSONObject lastFrame = animation.getJSONArray("frames").getJSONObject(frame-1);
			Vector lastCoords = JSONArrayToVector(lastFrame.getJSONArray("loc"));
			Vector currentCoords = JSONArrayToVector(frameObject.getJSONArray("loc"));
			
			relativeCoords = Vector.subtract(currentCoords, lastCoords);
		} else {
			relativeCoords = JSONArrayToVector(frameObject.getJSONArray("loc"));
		}
		
		// Get rotation
		float xRot = (float)frameObject.getJSONArray("rot").getDouble(0);
		float yRot = (float)frameObject.getJSONArray("rot").getDouble(1);
		
		// Write command
		writer.write("execute at @s run teleport @s[scores={"+ Constants.FRAMEOBJECTIVE + "="+frame+"}] ~"+
				String.format("%.10f",relativeCoords.X())+" ~"+String.format("%.10f",relativeCoords.Y())+
				" ~"+String.format("%.10f",relativeCoords.Z())+" "+xRot+" "+yRot);
		
		writer.newLine();

	}
	
	@Override
	public void reset(JSONObject animation, BufferedWriter writer) throws JSONException, IOException {
		// Get the current offset from the start position
		int length = animation.getJSONArray("frames").length();
		JSONObject frame = animation.getJSONArray("frames").getJSONObject(length-1);
		Vector locOffset = JSONArrayToVector(frame.getJSONArray("loc"));
		
		// Calculate and write the needed teleport command to arrive at position 1
		Vector desiredTeleport = Vector.multiply(locOffset, -1);
		
		float xRot = (float)frame.getJSONArray("rot").getDouble(0);
		float yRot = (float)frame.getJSONArray("rot").getDouble(1);
		
		writer.write("execute at @s run teleport @s[scores={"+ Constants.FRAMEOBJECTIVE + "="+length+"}] ~"+
				String.format("%.10f",desiredTeleport.X())+" ~"+String.format("%.10f",desiredTeleport.Y())+
				" ~"+String.format("%.10f",desiredTeleport.Z())+" "+xRot+" "+yRot);
	}
	
	// Method to turn JSONArrays to vectors
	protected Vector JSONArrayToVector(JSONArray array) throws JSONException {
		return new Vector(array.getDouble(0), array.getDouble(1), array.getDouble(2));
	}

}
