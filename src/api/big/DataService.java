package api.big;

import java.io.IOException;

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
	
	
	@GET
	@Produces("application/json")
	public Response convertFtoC() throws JSONException, IOException {
		
		String fullPath = servletContext.getRealPath(pathToData);
		JSONArray jsonArr = new JSONArray(CacheData.readFromFile(fullPath.toString()));

		String result = jsonArr.toString();
		return Response.status(200).entity(result).build();
	}
}
