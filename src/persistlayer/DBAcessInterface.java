package persistlayer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBAcessInterface 
{


		
		public Connection connect() throws ClassNotFoundException;
		//connect to db. 
		
		public ResultSet retrieve (PreparedStatement prepstatement) throws SQLException;
		//get info from db. 

		public int create (PreparedStatement prepstatement) throws SQLException;
		//creates connection. 
		
		public int update (PreparedStatement prepstatement);
		//update info on db. 
		
		public int delete ( PreparedStatement prepstatement) throws SQLException;
		//delete info on db. 
		
		public void disconnect();
		//disconnect from db. 
		

	
}
