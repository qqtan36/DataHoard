package objectlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class Tweet {
	long id;
	String body;
	long userID;
	String user;
	String location;
	Sentiment sentiment;
	double globalSentiment; 
	double overallSentiment; 
	String globalSentimentString; 
	HashMap<Sentiment, String>sentiment_mapper; 
	
	public Tweet(long id, String body, long userID, String user, String location, Sentiment sentiment){
		this.id = id;
		this.body = body;
		this.userID = userID;
		this.user = user;
		this.location = location;
		this.sentiment = sentiment;
		//sentiment_mapper.put(sentiment, body); 
		//this.tweet_holder.add(e)
	}
	
	public Tweet(long id, String body, long userID, String user, Sentiment sentiment){
		this.id = id;
		this.body = body;
		this.userID = userID;
		this.user = user;
		this.sentiment = sentiment;
		//sentiment_mapper.put(sentiment, body); 
		//this.tweet_holder.add(body); 
	}
	

	
	public long getTweetID(){
		return this.id;
	}
	
	public String getUser(){
		return this.user;
	}
	
	public long getUserID(){
		return this.userID;
	}
	
	public String getLocation(){
		return this.location;
	}
	public String toString(){
		return this.body;
	}
	
	public String getTweet()
	{
		return this.body; 
	}
	
	public Sentiment getSentiment()
	{
		return this.sentiment; 
	}
	
	public void setGlobalSentiment(double input)
	{
		this.globalSentiment = input; 
	}
	
	public double getGlobalSentiment()
	{
		return this.globalSentiment; 
	}
	
	public void setGlobalSentimentString(String input)
	{
		this.globalSentimentString= input; 
		
	}
	
	public String getGlobalSentimentString()
	{
		return this.globalSentimentString; 
	}
	
	public void processSentiment ()
	{
		double pos = 0; 
		double neg = 0; 
		double neu = 0; 
		
		pos = this.sentiment.getPos(); 
		neg = this.sentiment.getNeg(); 
		neu = this.sentiment.getNeutral(); 
		
		ArrayList<Double> compareList = new ArrayList<Double>(); 
		compareList.add(pos); 
		compareList.add(neu); 
		compareList.add(neg); 
		
		double max = pos; 
		
		for (Double d : compareList)
		{
			if (d>max)
				max = d; 
		}
		
		this.overallSentiment = max; 
		System.out.println(this.overallSentiment + this.sentiment.label);
	}
	
	public double getOverallSentiment()
	{
		this.processSentiment();
		return this.overallSentiment; 
	}
	
	public String getStringSentiment()
	{
		return this.sentiment.label; 
	}
	
	/*
	public void addTweet(String tweet, String sentiment)
	{
		sentiment_mapper.put(sentiment, tweet); 
	}
	*/
}
