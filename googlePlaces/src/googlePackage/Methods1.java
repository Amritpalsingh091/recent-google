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
		
		int divider_string1=0;
		int divider_string2=0;
		for (int i=0;i<origins.length();i++){
			char a=origins.charAt(i);
			if(a == 124)
			{
				divider_string1=i;
			}
		}
		for (int i=0;i<destinations.length();i++){
			char a=destinations.charAt(i);
			if(a == 124)
			{
				divider_string2=i;
			}
		}
		
		
		String row0_element0=origins.substring(0, divider_string1); //San-jose
		String row0_element1=origins.substring(divider_string1+1,origins.length()); // Toronto
		String row1_element0=destinations.substring(0, divider_string2);//Vancouver
		String row1_element1=destinations.substring(divider_string2+1,destinations.length());// Seattle
		
		String place[] = new String[4];
		place[0] =  row0_element0+ " To " + row1_element0;
		place[1] =  row0_element0+ " To " + row1_element1;
		place[2] =  row0_element1+ " To " + row1_element0;
		place[3] =  row0_element1+ " To " + row1_element1;
	
		
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
		int interm=0;
		
		for(int i = 0;i<routes_array.size();i++){
			JSONObject route = routes_array.getJSONObject(i);
			JSONArray elements_array = route.getJSONArray("elements");
			for(int j =0;j<elements_array.size();j++){
				JSONObject distance_array = elements_array.getJSONObject(j);
				JSONObject start = distance_array.getJSONObject("distance");  //putting information of object distance in start(jsonobject)
				JSONObject duration_array = elements_array.getJSONObject(j);
				JSONObject duration = distance_array.getJSONObject("duration");    //putting information of object duration in duration(jsonobject)
				String value_object = start.getString("value");	      //getting value of object distance
				String value_duration = duration.getString("value");    // getting value of object duration
				double time = Integer.parseInt(value_duration);     // converting String into integer for comparing
				double distance = Integer.parseInt(value_object);     //converting String into integer for comparing
				comparison[interm+j]= distance/time;
				//System.out.println(" Trip "+(interm+j+1)+" is covered at speed of " + comparison[interm+j] +" meter/second");
			}
			interm=interm+2;		
		} 				
		double final_answer =For_max();
		int amrit=0;
		for(int m=0;m<comparison.length;m++){
			if(comparison[m]==final_answer){
				amrit=m;
				
			}
		}
		System.out.println("\n"+"best way is from "+ place[amrit]+  " is covered at speed of " + final_answer+" meter/second");
		int amrit2 = 0;
		double final_answer2=For_min();
		for(int m=0;m<comparison.length;m++){
			if(comparison[m]==final_answer2){
				amrit2=m;
				
			}
		}
		System.out.println("worst way is from " + place[amrit2] + " is covered at speed of " + final_answer2+" meter/second");
	}
	
	public double For_max()
	{
		double a=comparison[0];
		for (int k = 0; k<comparison.length;k++){
			if(a<comparison[k])
			{
				a= comparison[k];
			}
		}
		return a;
	}
	public double For_min()
	{
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