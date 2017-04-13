import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import twitter4j.GeoLocation;
import twitter4j.Location;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;


// // This sample uses the Apache HTTP client from HTTP Components (http://hc.apache.org/httpcomponents-client-ga/)
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class MiningRunner {
	
	
	// Driver name
	private final static String driver = "com.mysql.jdbc.Driver";
	// Databasename :
	private final static String url = "jdbc:mysql://127.0.0.1:3306/tweet_storage";
	// Mysql username
	private final static String user = "root";
	// Mysql password
	private final static String password = "subroutineMartel";//123456";
	private static Connection conn;
	
	

	public static void main(String[] args) throws TwitterException, SQLException, InterruptedException 
	{

		
		        HttpClient httpclient = HttpClients.createDefault();

		        try
		        {
		            URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment");


		            URI uri = builder.build();
		            HttpPost request = new HttpPost(uri);
		            request.setHeader("Content-Type", "application/json");
		            request.setHeader("Ocp-Apim-Subscription-Key", "{subscription key}");


		            // Request body
		            StringEntity reqEntity = new StringEntity("{body}");
		            request.setEntity(reqEntity);

		            HttpResponse response = httpclient.execute(request);
		            HttpEntity entity = response.getEntity();

		            if (entity != null) 
		            {
		                System.out.println(EntityUtils.toString(entity));
		            }
		        }
		        catch (Exception e)
		        {
		            System.out.println(e.getMessage());
		        }
		    
		
		
		
		getTrending(); 
		
		
	}
	
	
	static List<String> getTimeLine() throws TwitterException 
	{
	    Twitter twitter = getTwitterinstance();
	     
	    return twitter.getUserTimeline("realDonaldTrump", new Paging(1, 10000)).stream()
	      .map(item -> item.getText())
	      .collect(Collectors.toList());
	}


	private static Twitter getTwitterinstance() 
	{
		// TODO Auto-generated method stub
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("GUJPXGn05nx82B7d3GcWpnJxd")
		  .setOAuthConsumerSecret("PFe1cwu5uG8wpzoWO0cxL1IWxSpp2ZsgFNxEdlPCd7ZqnOtyxo")
		  .setOAuthAccessToken("850074770859339776-RwADwOwrXNL7q7LuJDOnLH5o7OvXVRx")
		  .setOAuthAccessTokenSecret("Jav9EdPvMkqZYz1z5KU3hCtcNOr5gGzCHE7BGvzFhSadb");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		return twitter; 
	}
	
	
	public static  ArrayList<Status> searchLocation() throws TwitterException
	{
		Twitter twitter = getTwitterinstance();
		Query query = new Query().geoCode(new GeoLocation(33.753746, -84.386330), 20, "mi"); 
		query.count(1000); //You can also set the number of tweets to return per page, up to a max of 100
		
		
		QueryResult result = twitter.search(query);
		
		/*
		List<String> locationTweets = new List<String>(); 
		locationTweets = (result.getTweets()).toString(); 
	
		 */
		
		ArrayList<Status> sample = new ArrayList<Status>(); 
		
		sample = (ArrayList<Status>) result.getTweets(); 
		
		return sample; 
		
	//	System.out.println(result.getCount()); 
	}
	
	
	public static List<String> searchtweets() throws TwitterException 
	{
		  
	    Twitter twitter = getTwitterinstance();
	    Query query = new Query("donald trump");
	    query.setCount(100);
	    QueryResult result = twitter.search(query);
	     
	    return result.getTweets().stream()
	      .map(item -> item.getText())
	      .collect(Collectors.toList());
	}
	
	public static void insertData() throws SQLException
	{
		initDatabase(); 
		
		Statement statement = null; 
		
		statement = conn.createStatement(); 
		
		String fileName = "companylist-4.csv";
		
		String line = null; 
		
        try 
        {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {

            	
            	if (line.contains("LastSale"))
            		continue; 
            	 
            	
            	System.out.println(line);
            	
            	String [] tempArr = line.split(","); 
            	
            	
            	String companyCode = (tempArr[0]).replace("\"", "");
            	String companyName = (tempArr[1]).replace("\"", "");
            	
        		double companyPrice = 0; 
        		double companyCap = 0;
            	
            	try{
            	companyPrice = Double.parseDouble(tempArr[2]); 
            	companyCap = Double.parseDouble(tempArr[3]); 
            	}
            	catch(NumberFormatException n)
            	{
            		companyPrice = 0; 
            		companyCap = 0; 
            	}
            	
            	String query = "insert into company_info (company_symbol, company_name, price, market_cap) values (\"" + companyCode + "\",  \"" + companyName + "\",  " + companyPrice + ", " + companyCap + ")"; 
            	
            	
            	//System.out.println(query);
            	
            	try{
            	statement.executeUpdate(query); 
            	}
            	
            	catch (MySQLIntegrityConstraintViolationException x)
            	{
            		continue; 
            	}
            	
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
		
		
	}
	
	public static void gatherUsers() throws SQLException, TwitterException
	{
		initDatabase(); 
		
		Statement statement = null; 
		
		statement = conn.createStatement(); 
		
		String fileName = "companylist-4.csv";
		
		String line = null; 
		
		ArrayList<Status> listStatus = searchLocation(); 
		
		for (Status s : listStatus)
		{
			long tempLong = (s.getUser()).getId(); 
			String userTweet = s.getText(); 
			
			String query = null; 
			
			query = "insert into twitter_users (user_id, user_tweet) VALUES (" + tempLong + ", \"" + userTweet + "\")"; 
			System.out.println(query);
			
			try
			{
				statement.executeUpdate(query); 
			}
			
        	catch (MySQLIntegrityConstraintViolationException x)
        	{
        		continue; 
        	}
			
			catch ( MySQLSyntaxErrorException z)
			{
				continue; 
			}
			
		}
		
		
	}
	

	
	
	public static void initDatabase() 
	{

		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(url, user, password);
			if (!conn.isClosed()) {
				System.out.println("\nSucceeded connecting to the Database!\n");
			} else {
				System.out.println("Failed connecting to the Database!\n");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void addInfo() throws SQLException, TwitterException
	{
		initDatabase(); 
		
		Statement statement = null; 
		
		statement = conn.createStatement(); 
		
		String fileName = "companylist-4.csv";
		
		String line = null;
		
		String query = "select * from twitter_users"; 
		
		ResultSet rs = null; 
		
		rs = statement.executeQuery(query);
		
	    Twitter twitter = getTwitterinstance();

		
		while (rs.next())
		{
			Statement s2 = conn.createStatement(); 
			
			

			
			if (rs.getString("username") == null)
			{
				long tempLong = rs.getLong("user_id"); 
				
				System.out.println(tempLong);
				
				
				
				
				try
				{
					User tempUser = twitter.showUser(tempLong); 
					String tempUsername = tempUser.getName(); 
					//double lat = (tempUser
					
					query = "update twitter_users set username = \"" + tempUsername    + "\" where user_id = " + tempLong; 
					
					
					
					s2.executeUpdate(query); 
				}
				
				
				
				catch (TwitterException te)
				{
					continue; 
				}
				
				
			}
			
		}
	}

	
	public static void addData() throws SQLException, TwitterException
	{
		Twitter twitter = getTwitterinstance(); 
		
		initDatabase(); 
		Statement statement = null; 
		statement = conn.createStatement(); 
		String query1 = null;
		String query2 = null; 
		
		ArrayList<Status> listStatus = searchLocation(); 
		
		for (Status s : listStatus)
		{
			User tempuser = s.getUser(); 
			
			String username = tempuser.getName(); 
			long user_id = tempuser.getId(); 
			
			String tweetinfo = s.getText(); 
			
			
			query1 = "INSERT INTO USER(USER_ID, USERNAME) VALUES(?, ?)"; 
			query2 = "INSERT INTO tweet_data (tweet, tweet_id) VALUES(?, ?)"; 
	
			try
			{
				statement = conn.prepareStatement(query1); 
				((PreparedStatement) statement).setLong(1, user_id); 
				((PreparedStatement) statement).setString(2, username); 
				((PreparedStatement)statement).executeUpdate(); 
				 
				
				
				
				statement = conn.prepareStatement(query2); 
				((PreparedStatement) statement).setString(1, tweetinfo); 
				((PreparedStatement) statement).setLong(2, user_id); 
				((PreparedStatement)statement).executeUpdate(); 
			
			}
			
			catch (MySQLIntegrityConstraintViolationException m)
			{
				
				statement = conn.prepareStatement(query2); 
				((PreparedStatement) statement).setString(1, tweetinfo); 
				((PreparedStatement) statement).setLong(2, user_id); 
				((PreparedStatement)statement).executeUpdate();
				
				continue; 
			}
			
		}
		
		
		

	}

	
	public static void getTrending() throws SQLException, TwitterException
	{
		
		// TODO Auto-generated method stub
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("GUJPXGn05nx82B7d3GcWpnJxd")
		  .setOAuthConsumerSecret("PFe1cwu5uG8wpzoWO0cxL1IWxSpp2ZsgFNxEdlPCd7ZqnOtyxo")
		  .setOAuthAccessToken("850074770859339776-RwADwOwrXNL7q7LuJDOnLH5o7OvXVRx")
		  .setOAuthAccessTokenSecret("Jav9EdPvMkqZYz1z5KU3hCtcNOr5gGzCHE7BGvzFhSadb");
		TwitterFactory tf = new TwitterFactory(cb.build());
		
		//TwitterFactory tf = new TwitterFactory(getTwitterinstance());
		Twitter twitter = tf.getInstance();  
		
		initDatabase(); 
		Statement statement = null; 
		statement = conn.createStatement(); 
		
		ResponseList<Location>CurrentTrends;  
		CurrentTrends = twitter.getAvailableTrends(); 
		
		Trends trendData = twitter.getPlaceTrends(2357024); 
		
		for (int i = 0; i<trendData.getTrends().length; i++)
		{
			System.out.println(trendData.getTrends()[i].getName());
		}
		
		
		
	}
	

}
