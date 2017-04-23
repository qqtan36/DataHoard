package logiclayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import objectlayer.Sentiment;
import objectlayer.Tweet;
import persistlayer.QueryCaller;

public class QueryProcessor 
{
	
	QueryCaller qc = new QueryCaller(); 
	SentimentAnalysis sa = new SentimentAnalysis(); 

	public ArrayList<Tweet> processTrendingLocationTweets(String inputLocation, String keyword)
	{
		ArrayList<Tweet>tweets = new ArrayList<Tweet>(); 
		
		ResultSet rs = null; 
		rs = qc.returnTrendingLocationData(inputLocation, keyword); 
		
		JSONParse jsonparse = new JSONParse(); 
		
		
		if (rs != null)
		{
			
			//long id, String body, long userID, String user, String location
			
			try 
			{
				while (rs.next())
				{
					long id = rs.getLong("id"); 
					String tweet = rs.getString("tweet"); 
					long userID = rs.getLong("tweet_id"); 
					String username = rs.getString("username"); 
					String location = rs.getString("location"); 
					String sentiment = null; 
					sentiment = (sa.sendPost(tweet)).toString();
					
					/*
					if (rs.getString("tweet_sentiment") == null)
						sentiment = (sa.sendPost(tweet)).toString();
					
					else 
					{
						sentiment = rs.getString("tweet_sentiment"); 
						System.out.println("Didnt pull from DB"); 
					}
					*/
					 
					
					System.out.println(sentiment);
					
					Sentiment tempSentiment = jsonparse.parse(sentiment); 
					tweets.add(new Tweet(id, tweet, userID, username, location, tempSentiment));
					
					
					 
					//tweets.add(new Tweet(id, tweet, userID, username, location, sentiment)); 
					
				}
			} 
			
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return tweets; 
		
		
	}
	
	public ArrayList<Tweet> processUserTweets(String inputUsername)
	{
		ArrayList<Tweet>tweets = new ArrayList<Tweet>(); 
		HashMap<Long, ArrayList<Tweet>>tweetMap = new HashMap<Long, ArrayList<Tweet>>(); 
		
		ResultSet rs = null; 
		rs = qc.returnUsers(inputUsername); 
		JSONParse jsonparse = new JSONParse(); 
		
		try 
		{
			while (rs.next())
			{
				//ArrayList<String>tweet_holder = ArrayList<String>(); 
				
				
				long id = rs.getLong("user_id");
				String tweet = rs.getString("tweet"); 
				long userID = rs.getLong("tweet_id"); 
				String username = rs.getString("username"); 
				String location = rs.getString("location");
				String sentiment = (sa.sendPost(tweet)).toString(); 
				
				Sentiment tempSentiment = jsonparse.parse(sentiment); 
				tweets.add(new Tweet(id, tweet, userID, username, location, tempSentiment)); 
				
				/*
				if (tweetMap.get(id)  == null)
				{
					//validate if we have called this data already. 
					String tweet = rs.getString("tweet"); 
					long userID = rs.getLong("tweet_id"); 
					String username = rs.getString("username"); 
					String location = rs.getString("location"); 
					String sentiment = (sa.sendPost(tweet)).toString(); 
					
					Sentiment tempSentiment = jsonparse.parse(sentiment); 

					tweets.add(new Tweet(id, tweet, userID, username, location, tempSentiment)); 
					tweetMap.put(id, tweets);
	
					
				}
				
				else
				{
					
					ArrayList<Tweet>tempList = new ArrayList<Tweet>(); 
					
					
					//validate if we have called this data already. 
					String tweet = rs.getString("tweet"); 
					long userID = rs.getLong("tweet_id"); 
					String username = rs.getString("username"); 
					String location = rs.getString("location"); 
					String sentiment = (sa.sendPost(tweet)).toString(); 

					Sentiment tempSentiment = jsonparse.parse(sentiment); 
					 
					tempList = tweetMap.get(id); 
					
					tempList.add(new Tweet(id, tweet, userID, username, location, tempSentiment)); 
					tweetMap.put(id, tempList); 
					
					
				}
				*/
				
 
			}
		} 
		
		
		
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return tweets; 
	}
	
	
	
	
}
