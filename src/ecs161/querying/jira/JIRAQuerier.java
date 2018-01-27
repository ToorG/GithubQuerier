package ecs161.querying.jira;

import ecs161.querying.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.json.JSONObject;

public class JIRAQuerier {

	public static void main(String[] args) throws IOException {
		System.out.println(args.length);
		JIRAQuerier querier = new JIRAQuerier();
		String name = querier.query();
		System.out.println(name);
	}

	public JIRAQuerier() { 
	}

	public static String query() throws IOException {
		String baseURL = "https://issues.apache.org/jira/rest/api/2/issue/";
		String jiraID = "DERBY-1366";
		// Query the appropriate API
		URL url = new URL(baseURL + jiraID);
		
		// Read all bytes from the URL, if it exists. If it does not, the code below will throw an IOException
		
		JSONObject json = Util.queryURL(url);
		
		JSONObject root = json.getJSONObject("root");
		JSONObject fields = root.getJSONObject("fields");
		JSONObject issueType = fields.getJSONObject("issuetype");
		return issueType.getString("name");
	}


	
}
