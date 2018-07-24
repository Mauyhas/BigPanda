package digest.exe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class CacheData {
	public static Map<String, Integer> m_eventCounter = new HashMap<String, Integer>();;
	public static Map<String, Integer> m_wordCounter = new HashMap<String, Integer>();;

	private CacheData() {

	}

	public static void insertEventToCache(String event) {
		if (m_eventCounter.containsKey(event)) {
			int currentValue = m_eventCounter.get(event);
			currentValue++;
			m_eventCounter.put(event, currentValue);
		} else {
			m_eventCounter.put(event, 1);
		}
		System.out.println("Current Events counter" + m_eventCounter.toString());
	}

	public static void insertWordToCache(String word) {
		if (m_wordCounter.containsKey(word)) {
			int currentValue = m_wordCounter.get(word);
			currentValue++;
			m_wordCounter.put(word, currentValue);
		} else {
			m_wordCounter.put(word, 1);
		}
		System.out.println("Current Data Event" + m_wordCounter.toString());
	}

	public static Map<String, Integer> getEventCounter() {
		return m_eventCounter;
	}

	public static Map<String, Integer> getWordCounter() {
		return m_wordCounter;
	}

//	public static void WriteToFile(String fileToWrite) throws IOException {
//		RandomAccessFile f = new RandomAccessFile(new File(fileToWrite), "rw");
//		JSONArray dataArray = new JSONArray();
//		JSONObject jsonObjEvents = new JSONObject(m_eventCounter);
//		JSONObject jsonObjWords = new JSONObject(m_wordCounter);
//		dataArray.put(jsonObjWords);
//		dataArray.put(jsonObjEvents);
//		StringBuilder writeToFile = new StringBuilder();
//		writeToFile.append("writes: ");
//		writeToFile.append(dataArray.toString());
//		System.out.println(writeToFile.toString());
//		f.seek(0); // to the beginning
//		f.write(dataArray.toString().getBytes());
//		f.close();
//
//	}
//
//	public static String readFromFile(String fileToRead) throws IOException {
//
//		// create a new RandomAccessFile with filename test
//		RandomAccessFile raf = new RandomAccessFile(fileToRead, "rw");
//		// write something in the file
//		// set the file pointer at 0 position
//		raf.seek(0);
//		// print the line
//		return raf.readLine();
//
//	}
	public static JSONArray returnLatesetData() {
		JSONArray dataArray = new JSONArray();
		JSONObject jsonObjEvents = new JSONObject(m_eventCounter);
		JSONObject jsonObjWords = new JSONObject(m_wordCounter);
		dataArray.put(jsonObjWords);
		dataArray.put(jsonObjEvents);
		return dataArray;
		
		
	}
	
}
