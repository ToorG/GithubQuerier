package ecs161.querying;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONObject;

public class Util {
	
	
	public static JSONObject queryURL(URL url) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = in.read()) != -1) {
			sb.append((char) cp);
		}
		in.close();
		String content = sb.toString();
		// Extract issue-type from the JSON object returned.
		String jsonText = "{root:" + content + "}";
		JSONObject json = new JSONObject(jsonText);
		return json;
	}
}
