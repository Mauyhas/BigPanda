package digest.exe;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;

public class ExeReaderDigest {
	//generator fs location
	//@example
	//public static String exeLocation = "C:\\bigpanda\\generator-windows-amd64.exe";			
	//to run [0] - data file path
	//[1] - exe file path
	public static void main(String[] args) {
		if(args == null || args.length < 1)
			System.exit(0);
		try {
			//@example
			//File file = new File("C:\\bigpanda\\data.text");
			File file = new File(args[1]);
			
			LineProcessor proccessor = new LineProcessor();
			Process process = new ProcessBuilder(args[0]).start();
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				try {
					System.out.println(line);
					proccessor.processLine(line);
					CacheData.WriteToFile(file.getAbsolutePath());
					
				}catch(JSONException je) {
					/*do nothing with invalid json */
					System.out.println("unvalid json:" + line );
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			process.waitFor();
			System.out.println("ok!");

			in.close();
			System.exit(0);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
}
