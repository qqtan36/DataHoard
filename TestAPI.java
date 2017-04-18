import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.Proxy;

public class TestAPI {
	private final String USER_AGENT = "Chrome/57.0.2987.133";

	public static void main(String[] args) throws Exception{
		TestAPI test = new TestAPI();
		test.sendPost();
		
	}
	public void sendPost() throws Exception{

		String url = "http://text-processing.com/api/sentiment/";
		URL urlObj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setRequestMethod("POST");
		
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String urlParameters = "text=terrible game today";
		
		//send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}

}
