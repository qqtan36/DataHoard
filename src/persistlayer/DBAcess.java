package persistlayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class DBAcess 
{

	
	
	protected Connection conn = null; 
	protected Statement statement; 
	
	protected ResultSet result; 
	
	
	protected static String DB_CONNECTION_USERNAME = "root"; 
	protected static String DB_CONNECTION_PASSWORD = "subroutineMartel"; 
	
	protected static String DB_ERROR_AREA = "default_none"; 
	protected static ResultSet rs; 
	
	static String DB_DRIVER_NAME = "com.mysql.jdbc.Driver"; 
	static String DB_CONNECTION_URL = "jdbc:mysql://localhost/tweet_storage";
	//set the varibales for conenctions. 
	public Connection connect() throws ClassNotFoundException 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//it wouldnt make sense to return info life password so thus minimal methods. 
	
}
