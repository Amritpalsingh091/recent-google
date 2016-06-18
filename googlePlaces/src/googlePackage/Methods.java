


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

public class Methods {
	
	
	private static final String GOOGLE_API_KEY = "AIzaSyARiki0HBLlyR7xH0K3e4eifaSLzx8b-7E";
	
	private final HttpClient client = HttpClientBuilder.create().build();;
	
	public void performSearch(final String origin, final String destination, final String mode) throws ParseException, IOException,
	URISyntaxException{
		final URIBuilder builder = new URIBuilder().setScheme("https").setHost("maps.googleapis.com").setPath("/maps/api/directions/json");


		builder.addParameter("origin",origin);
		builder.addParameter("destination",destination);


		builder.addParameter("mode",mode);
		builder.addParameter("key",GOOGLE_API_KEY);
		final HttpUriRequest request = new HttpGet(builder.build());
		//System.out.print(request);
		final HttpResponse execute = this.client.execute(request);
		final String response = EntityUtils.toString(execute.getEntity());
		System.out.println(response);

		JSONObject object_response = JSONObject.fromObject(response);
		JSONObject object = JSONObject.fromObject(response);

		JSONArray routes_array = object.getJSONArray("routes");

		for(int i = 0;i<routes_array.size();i++){

			System.out.println("The route "+routes_array.size());

			JSONObject route = routes_array.getJSONObject(i);

			JSONArray legs_array = route.getJSONArray("legs");

			System.out.println("The legs "+legs_array.size());

			for(int j =0;j<legs_array.size();j++){

				JSONObject legs = legs_array.getJSONObject(j);

				String start = legs.getString("start_address");

				System.out.println("The start address is: "+start);

				String end = legs.getString("end_address");

				System.out.println("The end address is: "+end);
			
				
		
	}

}}}