package digest.exe;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.json.JSONException;

public class ExeReaderDigest {
	//generator fs location
	//@example
	public static String exeLocation = "C:\\bigpanda\\generator-windows-amd64.exe";			
	//to run [0] - data file path
	//[1] - exe file path
	public static void main(String[] args) {
		//if(args == null || args.length < 1)
			//System.exit(0);
		try {
			//@example
			//File file = new File("C:\\bigpanda\\data.text");
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
	                  "org.apache.naming.java.javaURLContextFactory");
			LineProcessor proccessor = new LineProcessor();
			Process process = new ProcessBuilder(exeLocation).start();
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				try {
					System.out.println(line);
					proccessor.processLine(line);
					//CacheData.WriteToFile(file.getAbsolutePath());
					InitialContext initCtx = new InitialContext();
					Context envContext = (Context) initCtx.lookup("java:comp/env");
					ConnectionFactory connectionFactory = (ConnectionFactory) envContext.lookup("jms/ConnectionFactory");
					Connection connection = connectionFactory.createConnection();
					Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
					Destination destination = session.createTopic("jms/topic/MyTopic");
					MessageProducer producer = session.createProducer(destination);
					TextMessage msg=session.createTextMessage();
					msg.setText(line);
					System.out.println("sending msg:" + msg.getText());
					producer.send(msg);
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
