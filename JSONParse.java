import com.google.gson.Gson;

public class JSONParse {
	
	public Sentiment parse(String jsonObj){
		Gson gson = new Gson();
		Sentiment response = gson.fromJson(jsonObj, Sentiment.class);		
		return response;
	}
}
