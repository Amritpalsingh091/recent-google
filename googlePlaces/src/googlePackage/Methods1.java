


package googlePackage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Methods1 {


	private static final String GOOGLE_API_KEY = "AIzaSyARiki0HBLlyR7xH0K3e4eifaSLzx8b-7E";
	double comparison[]=new double[4];

	private final HttpClient client = HttpClientBuilder.create().build();;



	public void performSearch(final String origins, final String destinations, final String mode) throws ParseException, IOException,URISyntaxException
	{
		final URIBuilder builder = new URIBuilder().setScheme("https").setHost("maps.googleapis.com").setPath("/maps/api/distancematrix/json");
		builder.addParameter("origins",origins);
		builder.addParameter("destinations",destinations);
		builder.addParameter("mode",mode);
		builder.addParameter("key",GOOGLE_API_KEY);
		final HttpUriRequest request = new HttpGet(builder.build());
		System.out.print(request);
		final HttpResponse execute = this.client.execute(request);
		final String response = EntityUtils.toString(execute.getEntity());
		System.out.println(response);
		JSONObject object = JSONObject.fromObject(response);
		JSONArray routes_array = object.getJSONArray("rows");
		System.out.println(routes_array.size() +" size of root");
		int amrit;
		int interm=0;
		for(int i = 0;i<routes_array.size();i++){
			//System.out.println(routes_array.size());
			JSONObject route = routes_array.getJSONObject(i);
			JSONArray elements_array = route.getJSONArray("elements");
			//comparison = new int[routes_array.size()+elements_array.size()];
			for(int j =0;j<elements_array.size();j++){
				JSONObject distance_array = elements_array.getJSONObject(j);
				JSONObject start = distance_array.getJSONObject("distance");
				JSONObject duration_array = elements_array.getJSONObject(j);
				JSONObject duration = distance_array.getJSONObject("duration");
				String value_object = start.getString("value");	
				String value_duration = duration.getString("value");
				double time = Integer.parseInt(value_duration);
				double distance = Integer.parseInt(value_object);
				comparison[interm+j]= distance/time;
				System.out.println(" Trip "+(interm+j+1)+" is covered at speed of " + comparison[interm+j] +" meter/second");
			}
			interm=interm+2;
		
		} 
				
	double final_answer =For_max();
System.out.println("\n"+"best trip is covered at speed of " + final_answer+" meter/second");
	double final_answer2=For_min();
	System.out.println("worst trip is covered at speed of " + final_answer2+" meter/second");
	}
	public double For_max(){
		double a=comparison[0];
		for (int k = 0; k<comparison.length;k++){
			if(a<comparison[k])
			{
				a= comparison[k];
			}
		}
		return a;
	}
	public double For_min(){
		double a=comparison[0];
		for (int k = 0; k<comparison.length;k++){
			if(a>comparison[k])
			{
				a= comparison[k];
			}


		}
		return a;


	}
}