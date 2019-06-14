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
				relativeCoords.X()+" ~"+relativeCoords.Y()+" ~"+relativeCoords.Z()+" "+xRot+" "+yRot);
		
		writer.newLine();

	}
	
	// Method to turn JSONArrays to vectors
	protected Vector JSONArrayToVector(JSONArray array) throws JSONException {
		return new Vector(array.getDouble(0), array.getDouble(1), array.getDouble(2));
	}

}
