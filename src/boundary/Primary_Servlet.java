package boundary;



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import logiclayer.QueryProcessor;
import objectlayer.Sentiment;
import objectlayer.Tweet;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * Servlet implementation class Primary_Servlet
 */
@WebServlet("/Primary_Servlet")
public class Primary_Servlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Primary_Servlet() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("here");
		response.setContentType("text/html");
		String input = request.getParameter("selected_category");
		String input_query = request.getParameter("input_area"); 
		
		String input_keyword = request.getParameter("keyword_input"); 
		String input_location = request.getParameter("location_input"); 
		
		
		String path = this.getServletContext().getRealPath("WEB-INF/templates/");
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(path));
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		cfg.setLogTemplateExceptions(false);
		
		HashMap<String, Object> root = new HashMap();
		
		
		root.put("data", input);
		
		QueryProcessor qp = new QueryProcessor(); 

		
		if (input != null)
		{
			
			
			if (input.equalsIgnoreCase("location_search"))
			{
				
				System.out.println("Found");
				ArrayList<Tweet>tweets = new ArrayList<Tweet>(); 
				tweets = null; 
				
				tweets = qp.processTrendingLocationTweets(input_location, input_keyword); 
				
				double pos = 0; 
				double neutral = 0; 
				double negative = 0; 
				
				for (Tweet t : tweets)
				{
					Sentiment temp = t.getSentiment(); 
					
					pos+=temp.getPos(); 
					negative += temp.getNeg(); 
					neutral += temp.getNeutral(); 
					
					
				}
				
				pos = pos/tweets.size(); 
				neutral = neutral/tweets.size(); 
				negative = negative/tweets.size(); 
				
				ArrayList<Double> compareList = new ArrayList<Double>(); 
				compareList.add(pos); 
				compareList.add(neutral); 
				compareList.add(negative); 
				double max = 0; 
				
				for (Double d : compareList)
				{
					if (d>max)
						max = d; 
				}
				
				String globalString = null; 
				
				if (max == pos)
					globalString = "positive";
				else if (max == negative)
					globalString = "negative"; 
				else
					globalString = "neutral"; 
				
					
				
				for (Tweet t : tweets)
				{
					t.setGlobalSentiment(max);
					t.setGlobalSentimentString(globalString);
				}
				
				
				
				
				
				root.put("trending_tweets", tweets); 
				
				/*
				ArrayList<Tweet>tweets = new ArrayList<Tweet>(); 
				tweets = null; 
				
				tweets = qp.processTrendingLocationTweets(input_query); 
				root.put("trending_tweets", tweets); 
				*/
			}
			
			else if (input.equalsIgnoreCase("user_search"))
			{
				ArrayList<Tweet>tweets = new ArrayList<Tweet>(); 
				tweets = null; 
				
				tweets = qp.processUserTweets(input_query); 
				root.put("user_search", tweets); 
			}
			
			
			

		}
		
		
		Template template = null;
		//Writer out = new OutputStreamWriter(response.getOutputStream());
		PrintWriter out = null; 
		
		
		
		try 
		{
			out = response.getWriter(); 
			
			System.out.println("Input is: " + input);
			
			//depending on what we want to return we will prepare the template for response. 
			if (input.equalsIgnoreCase("location_search"))
			{
				template = cfg.getTemplate("location_search.ftl"); 
			}
			
			
			else if (input.equals("user_search"))
			{	
				template = cfg.getTemplate("user_search.ftl");	
			}

			
			template.process(root, out);
			//sends response template.
			
		}
		
		
		catch (TemplateException e )
		{
			e.printStackTrace();
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}

		
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		System.out.println("here");
		doGet(request, response);
	}

}
