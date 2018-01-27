//Author Harjot Singh Toor
//ECS161 Fall 2017

package ecs161.querying.github;
import ecs161.querying.Util;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class GithubQuerier
{
  private static final String token = ""; // add your private token here, it allows for more requests per hour
  String account;
  String name;
  
  public static void main(String[] args) throws IOException
		  {	 
		    GithubQuerier querier = new GithubQuerier(args[0], args[1]);
		    String status1 = querier.query(args[2]);
		    String status2 = querier.query(args[3]);
		    System.out.println(status1);
		    System.out.println(status2);
		  }
  
  
  public GithubQuerier(String accountName, String projectName)
  {
    this.account = accountName;
    this.name = projectName;
  }
  
  public String query(String userName)
    throws IOException
  {
	String userID = userName;
    String baseURL = "https://api.github.com/repos/";
    String pullrequest="/pulls?state=all&merge=all&page=";
    
    int countRequest = 0;
    int mergeRequests = 0;
    
    int page_increment = 1;
    String pageNumber = Integer.toString(page_increment);
    
    URL url = new URL(baseURL + this.account + "/" + this.name + pullrequest  + pageNumber + "&access_token=" + token);
    JSONObject json = Util.queryURL(url);

    JSONArray root = json.getJSONArray("root");
    
   // System.out.println(url);
    for (int j = 1; root.length() != 0; j++)
    {
      int i = 0;
      while (i < root.length())
      {
        JSONObject fields = root.getJSONObject(i);
        JSONObject users = fields.getJSONObject("user");
        String login = users.getString("login");
        if (login.equals(userID))
        {
          countRequest = countRequest + 1;
          
          if (!fields.isNull("merged_at"))   //if field is not null then merged_at
          {
            mergeRequests = mergeRequests + 1;
          // System.out.println(mergeRequests+" ");
          }
       
        }
        i++;
      }
      page_increment++;
      pageNumber = Integer.toString(page_increment);
      
      url = new URL(baseURL + this.account + "/" + this.name + pullrequest + pageNumber + "&access_token=" + token);
      json = Util.queryURL(url);
      root = json.getJSONArray("root");
      
    }
    String userTotal = Integer.toString(countRequest);
    String reqMerge = Integer.toString(mergeRequests);
    String result = userTotal + " " + reqMerge;
    return result;
  }
  
}
