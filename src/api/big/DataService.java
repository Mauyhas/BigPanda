package api.big;

import java.io.IOException;

import javax.jms.JMSException;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;

import digest.exe.CacheData;

@Path("/data")
public class DataService {

	CacheData cacheService;
	@Context ServletContext servletContext;
	private final String pathToData = "/WEB-INF/data.txt";
	private Reciever m_msgReciver;
	/**
	 * CacheData - get the latest msg from Receiver that run Concurrently with exe and the 
	 * update happens in REAL-TIME
	 */
	
	@GET
	@Produces("application/json")
	public Response convertFtoC() {
		JSONArray jsonArr = new JSONArray();
		String result = jsonArr.toString();
		try {
			jsonArr = CacheData.returnLatesetData();
			result = jsonArr.toString();
			return Response.status(200).entity(result).build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.status(200).entity(result).build();
		}
		
	}
}
