
public class Tweet {
	int id;
	String body;
	int userID;
	String user;
	String location;
	
	public Tweet(int id, String body, int userID, String user, String location){
		this.id = id;
		this.body = body;
		this.userID = userID;
		this.user = user;
		this.location = location;
	}
	
	public int getTweetID(){
		return this.id;
	}
	
	public String getUser(){
		return this.user;
	}
	
	public int getUserID(){
		return this.userID;
	}
	
	public String getLocation(){
		return this.location;
	}
	public String toString(){
		return this.body;
	}
}
