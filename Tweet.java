
public class Tweet {
	long id;
	String body;
	long userID;
	String user;
	String location;
	String sentiment; 
	
	public Tweet(long id, String body, long userID, String user, String location, String sentiment){
		this.id = id;
		this.body = body;
		this.userID = userID;
		this.user = user;
		this.location = location;
		this.sentiment = sentiment; 
	}
	
	public Tweet(long id, String body, long userID, String user, String sentiment){
		this.id = id;
		this.body = body;
		this.userID = userID;
		this.user = user;
		this.sentiment = sentiment; 
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
}
