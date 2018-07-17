package digest.exe;

import org.json.JSONException;
import org.json.JSONObject;

public class LineProcessor {
	private String eventKey = "event_type";
	private String dataKey = "data";
	private CacheData cacherFordata;
	
	public LineProcessor() {
	};	
	
	public void processLine(String i_line) throws JSONException {
					
		JSONObject jsonObj = new JSONObject(i_line);
		if(jsonObj.has(eventKey)) {
			String event = jsonObj.getString(eventKey);
			CacheData.insertEventToCache(event);
		}
		if(jsonObj.has(dataKey)) {
			String word = jsonObj.getString(dataKey);
			CacheData.insertWordToCache(word);
		}
		
	}
	
}

