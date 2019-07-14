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
		Vector coords = null;
		
		if (frame != 0) {
			JSONObject lastFrame = animation.getJSONArray("frames").getJSONObject(frame-1);
			Vector lastCoords = JSONArrayToVector(lastFrame.getJSONArray("loc"));
			Vector currentCoords = JSONArrayToVector(frameObject.getJSONArray("loc"));
			
			coords = Vector.subtract(currentCoords, lastCoords);
		} else {
			coords = JSONArrayToVector(frameObject.getJSONArray("loc"));
		}
		
		// Get rotation
			float xRot = (float)frameObject.getJSONArray("rot").getDouble(0);
			float yRot = (float)frameObject.getJSONArray("rot").getDouble(1);
		
		// Convert to local space in terms of previous frame by aligning forward vector to world
		float oldXRot = 0;
		float oldYRot = 0;
		if (frame != 0) {
			JSONObject lastFrame = animation.getJSONArray("frames").getJSONObject(frame-1);
			oldXRot = (float) lastFrame.getJSONArray("rot").getDouble(0);
			oldYRot = (float) lastFrame.getJSONArray("rot").getDouble(1);
		}
		Vector localCoords = coords.rotateDegrees(oldYRot*-1, oldXRot*-1, 0);
		
		// Get rotation difference
		if (frame != 0) {
			JSONObject lastFrame = animation.getJSONArray("frames").getJSONObject(frame-1);
			xRot = (float) (xRot - lastFrame.getJSONArray("rot").getDouble(0));
			yRot = (float) (yRot - lastFrame.getJSONArray("rot").getDouble(1));
		}
		
		// Write command
		writer.write("execute at @s run teleport @s[scores={"+ Constants.FRAMEOBJECTIVE + "="+frame+"}] ^"+
				formatFloat(localCoords.X())+" ^"+formatFloat(localCoords.Y())+
				" ^"+formatFloat(localCoords.Z())+" ~"+xRot+" ~"+yRot);
		
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
		
		// Convert to local space
		Vector localTeleport = desiredTeleport.rotateDegrees(yRot*-1, xRot*-1, 0);
		
		// Get relative rotation
		
		writer.write("execute at @s run teleport @s[scores={"+ Constants.FRAMEOBJECTIVE + "="+length+"}] ^"+
				formatFloat(localTeleport.X())+" ^"+formatFloat(localTeleport.Y())+
				" ^"+formatFloat(localTeleport.Z())+" ~"+xRot*-1+" ~"+yRot*-1);
		writer.newLine();
	}
	
	// Method to turn JSONArrays to vectors
	protected static Vector JSONArrayToVector(JSONArray array) throws JSONException {
		return new Vector(array.getDouble(0), array.getDouble(1), array.getDouble(2));
	}
	
	protected static String formatFloat(float inFloat) {
		return String.format("%.10f",inFloat);
	}

}
