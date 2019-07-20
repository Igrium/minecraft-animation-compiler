package org.metaversemedia.mcanim.compiler;

import java.io.BufferedWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class that allows BaseCompiler to write an animation without any actual animation data.
 * Requires empty frame objects
 * @author sam54123
 *
 */
public class EmptyCompiler extends BaseCompiler {

	@Override
	public void compileFrame(JSONObject animation, JSONObject frameObject, int frame, BufferedWriter writer)
			throws JSONException, IOException {
	}

	@Override
	public void reset(JSONObject animation, BufferedWriter writer) throws JSONException, IOException {
	}

}
