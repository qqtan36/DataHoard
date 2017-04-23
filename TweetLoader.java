import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.mysql.jdbc.ResultSetImpl;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class TweetLoader {

		// Driver name
		private final static String driver = "com.mysql.jdbc.Driver";
		// Databasename :
		private final static String url = "jdbc:mysql://127.0.0.1:3306/tweet_storage";
		// Mysql username
		private final static String user = "root";
		// Mysql password
		private final static String password = "";//123456";
		private static Connection conn;
		
//		public static void main(String[] args) throws SQLException{
//			TweetLoader load = new TweetLoader();
//			List<Tweet> array = load.dataArray();
//			
//			for(Tweet i : array){
//				System.out.println(i);
//			}
//		}
		
		
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
		/**
		 * pulls tweet and tweet info from database given a keyword and location data
		 * @author: Yongquan Tan
		 */
		public List dataArray(String keyword, String location) throws SQLException{
			initDatabase();
			List<Tweet> tweetList = new ArrayList();
			System.out.println("\nSucceeded creating list!\n");

			Statement statement = conn.createStatement();
			String query = "SELECT a.tweet, a.ID, b.user_id, b.username, a.location FROM tweet_data a, user b WHERE tweet_id = user_id AND a.tweet LIKE '%"+keyword+"%' AND a.location LIKE %"+location+"%";
			ResultSet rs = statement.executeQuery(query);
			System.out.println("\nSucceeded executing query!\n");

			Tweet tweet = null;
			while(rs.next()){
				long id = rs.getLong("a.ID");
				String text = rs.getString("a.tweet");
				long userID = rs.getLong("b.user_id");
				String username = rs.getString("b.username");
				String place = rs.getString("a.location");
				tweet = new Tweet(id, text,userID, username, place);
				//tweetList.add(tweet);
			}
			
			return tweetList;
		}
		
		/**
		 * pulls tweet and tweet info from database given a user
		 * @author: Yongquan Tan
		 */
		public List dataUserArray(String user) throws SQLException{
			initDatabase();
			List<Tweet> tweetList = new ArrayList();
			System.out.println("\nSucceeded creating list!\n");
			Statement statement = conn.createStatement();
			String query = "SELECT a.tweet, a.ID, b.user_id, b.username, b.location FROM tweet_data a, user b WHERE tweet_id = user_id AND b.username LIKE '%"+user+"%'";
			ResultSet rs = statement.executeQuery(query);
			System.out.println("\nSucceeded executing query!\n");

			Tweet tweet = null;
			while(rs.next()){
				long id = rs.getLong("a.ID");
				String text = rs.getString("a.tweet");
				long userID = rs.getLong("b.user_id");
				String username = rs.getString("b.username");
				String location = rs.getString("a.location");
				tweet = new Tweet(id, text,userID, username, location);
				//tweetList.add(tweet);
			}
			
			return tweetList;
		}
}
