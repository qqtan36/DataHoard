import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import twitter4j.GeoLocation;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class MiningRunner {
	
	
	// Driver name
	private final static String driver = "com.mysql.jdbc.Driver";
	// Databasename :
	private final static String url = "jdbc:mysql://127.0.0.1:3306/twitter_info";
	// Mysql username
	private final static String user = "root";
	// Mysql password
	private final static String password = "subroutineMartel";//123456";
	private static Connection conn;
	
	

	public static void main(String[] args) throws TwitterException, SQLException 
	{
		// TODO Auto-generated method stub

		
		ArrayList<String>testing = new ArrayList<String>(); 
		ArrayList<String>testing2 = new ArrayList<String>(); 
		
		testing = (ArrayList<String>) getTimeLine(); 
		testing2 = (ArrayList<String>) searchtweets(); 
				
		/*
		for (int i = 0; i<testing.size(); i++)
		{
			System.out.println(testing.get(i) + " " + i); 
		}
		*/
		
/*
			testing2 = (ArrayList<String>) searchtweets();
			
			for (int i = 0; i<testing2.size(); i++)
			{
				System.out.println(testing2.get(i)); 
			}
		
		*/
		/*
		ArrayList<Status> testData = searchLocation(); 
		
		for (Status s : testData)
		{
			System.out.println((s.getUser()).getId());
		}
		
		//insertData(); 
		 * */
		
		
		//gatherUsers(); 
		addInfo(); 
		
		
		
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
	
	
	

}
