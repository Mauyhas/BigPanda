package api.big;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.JSONObject;

import digest.exe.LineProcessor;

public class Reciever {

	protected Topic queue;

	protected String queueName = "jms/topic/MyTopic";

	protected String url = "tcp://localhost:61616";

	protected int ackMode = Session.AUTO_ACKNOWLEDGE;
	
	public String m_latestMsg;
	
	public JSONObject m_latestJsonInReciver;
	
	public String getM_latestMsg() {
		return m_latestMsg;
	}

	public void setM_latestMsg(String m_latestMsg) {
		this.m_latestMsg = m_latestMsg;
	}

	// for testing only
	public static void main(String[] args) {
		Reciever rec = new Reciever();
		try {
			rec.run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() throws JMSException {

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		TopicConnection connection = (TopicConnection) connectionFactory.createTopicConnection();

		connection.start();
		MessageConsumer consumer = null;
		Session session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = session.createTopic(queueName);
		consumer = session.createConsumer(queue);

		System.out.println(" Waiting for message (max 5) ");

		Message message = consumer.receive();
		processMessage(message);

		System.out.println("Closing connection");
		consumer.close();
		session.close();
		connection.close();

	}

	public void processMessage(Message message) {

		try {

			TextMessage txtMsg = (TextMessage) message;

			System.out.println("Received a message: " + txtMsg.getText());
			//validate json
			
			setM_latestMsg(txtMsg.getText());
			LineProcessor lp = new LineProcessor();
			lp.processLine(txtMsg.getText());
			
		} catch (Exception e) {

			e.printStackTrace();

		}
	}
}
