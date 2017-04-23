package logiclayer;
import com.google.gson.Gson;

import objectlayer.Sentiment;

public class JSONParse 
{
	
	public Sentiment parse(String jsonObj){
		Gson gson = new Gson();
		Sentiment response = gson.fromJson(jsonObj, Sentiment.class);	
		System.out.println("it worked " + response.getNeg());
		return response;
	}
}
