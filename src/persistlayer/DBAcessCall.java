package persistlayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAcessCall extends DBAcess 
{
	@Override
	public Connection connect() throws ClassNotFoundException 
	{
		
		try 
		{
			
			System.out.println(DB_DRIVER_NAME); 
			
			Class.forName("com.mysql.jdbc.Driver"); 
			System.out.println(DB_CONNECTION_URL);
			conn = DriverManager.getConnection(DB_CONNECTION_URL, DB_CONNECTION_USERNAME, DB_CONNECTION_PASSWORD);
			//establishes connection. 
		} 
		
		
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return conn;

	}

}
