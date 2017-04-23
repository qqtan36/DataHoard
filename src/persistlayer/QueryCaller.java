package persistlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryCaller 
{
	private DBAcessCall dba = new DBAcessCall();
	private PreparedStatement tempStatement = null; 
	private Connection queryConnection = null; 
	
	
	public QueryCaller()
	{
		try 
		{
			queryConnection = dba.connect();
		} 
		
		
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			queryConnection = null; 
		} 
	}
	
	
	public ResultSet returnTrendingLocationData(String location, String keyword)
	{
		String query = null; 
		ResultSet rs = null; 
		
		
		query = "SELECT * FROM TWEET_DATA td, USER u WHERE u.LOCATION = ? AND td.tweet LIKE ? AND td.tweet_id = u.user_id LIMIT 5"; 
		
		try 
		{
			tempStatement = queryConnection.prepareStatement(query); 
			tempStatement.setString(1,  location );
			tempStatement.setString(2, "%" + keyword + "%");
			rs = tempStatement.executeQuery(); 
			
			System.out.println(tempStatement.toString());
			//rs = tempStatement.executeQuery(); 
			
		} 
		
		
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//rs = null; 
			
		} 
		
		return rs; 
		

	}
	
	
	public ResultSet returnUsers(String inputUsername)
	{
		
		String query = null; 
		ResultSet rs = null; 
		
		query = "SELECT DISTINCT * FROM USER U, TWEET_DATA TD WHERE U.user_id = TD.tweet_id AND U.username LIKE ? LIMIT 1";
		//select distinct * from user u, tweet_data td where td.tweet_id = u.user_id AND u.username like "%gleejr9472%" limit 10; 
		try 
		{
			tempStatement = queryConnection.prepareStatement(query); 
			tempStatement.setString(1, "%"+inputUsername+"%");
			rs = tempStatement.executeQuery(); 
			//rs = tempStatement.executeQuery(); 
			
		} 
		
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			rs = null;
		}
		
		return rs; 
	}
	
	
}
