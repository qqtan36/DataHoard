
public class Tweet {
	long id;
	String body;
	long userID;
	String user;
	String location;
	
	public Tweet(long id, String body, long userID, String user, String location){
		this.id = id;
		this.body = body;
		this.userID = userID;
		this.user = user;
		this.location = location;
	}
	
	public Tweet(long id, String body, long userID, String user){
		this.id = id;
		this.body = body;
		this.userID = userID;
		this.user = user;
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
