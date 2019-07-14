package org.metaversemedia.mcanim.compiler;

import java.io.BufferedWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.metaversemedia.mcanim.Constants;

public class ArmatureCompiler extends TransformCompiler {
	
	@Override
	public void compileFrame(JSONObject animation, JSONObject frameObject, int frame, BufferedWriter writer)
			throws JSONException, IOException {
		super.compileFrame(animation, frameObject, frame, writer);
		
		// Pose in command is formatted like a JSON object,
		// so we can just write the JSON object to the command
		writer.write("data merge entity @s[scores={"+ Constants.FRAMEOBJECTIVE + "="+frame+"}] "
				+ "{Pose:"+frameObject.getJSONObject("pose")+"}");
		writer.newLine();
	}
	
	@Override
	public void reset(JSONObject animation, BufferedWriter writer) throws JSONException, IOException {
		super.reset(animation, writer);
		
		int length = animation.getJSONArray("frames").length();
		JSONObject startFrame = animation.getJSONArray("frames").getJSONObject(0);
		
		writer.write("data merge entity @s[scores={"+ Constants.FRAMEOBJECTIVE + "="+length+"}] "
				+ "{Pose:"+startFrame.getJSONObject("pose")+"}");
		writer.newLine();
	}
}
