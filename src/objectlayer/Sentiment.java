package objectlayer;
import java.util.HashMap;

//Sentiment pojo
public class Sentiment {
	HashMap<String, Double> probability;
	String label;
	
	public Sentiment(HashMap<String, Double> probability, String label){
		this.probability = probability;
		this.label = label;
	}
	
	public HashMap<String, Double> getNegative(){
		
		return this.probability;
	}
	
	public double getNeg(){
		
		return this.probability.get("neg");
		
	}
	
	public double getNeutral(){
		return this.probability.get("neutral");
	}

	public double getPos(){
		return this.probability.get("pos");
	}
	
	public String getLabel(){
		return this.label;
	}
	
	
 }
