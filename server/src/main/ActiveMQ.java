package main;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * The ActiveMQ object.
 */
public class ActiveMQ {

	private final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
	private boolean active = true;
	
	ActiveMQ() {		
		new Thread(new Producer()).start();
		new Thread(new Consumer()).start();
	}
	
	public void setActive() {
		this.active = false;
	}
	
	/**
	 * The Producer
	 */
	public class Producer implements Runnable {
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageProducer producer = null;
		
		String CHESS_STATUS_VALIDATED = "Chess.Status.Validated";
		
		/** 
		 * Sends message to ActiveMQ.
		 */
		public void sendMessage() {
			TextMessage message = null;
			
			try {
				String text = "hello world!";
				message = session.createTextMessage(text);
				
				producer.send(message);
				
				disconnect();
			} catch (Exception e) {
				System.out.println("ActiveMQ Send Message Exception: " + e);
				e.printStackTrace();
			}
		}
		
		/**
		 * Disconnects Producer
		 */
		public void disconnect() {
			try {
				session.close();
				connection.close();
				
				System.out.println("Producer disconnected");
			} catch (JMSException e) {
				System.out.println("ActiveMQ Disconnect Exception: " + e);
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQ.this.URL);
				
	            connection = connectionFactory.createConnection();
	            connection.start();
	            
	            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	            
	            destination = session.createTopic(CHESS_STATUS_VALIDATED);
	            
	            producer = session.createProducer(destination);
	            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	            
	            sendMessage();
			} catch (Exception e) {
                System.out.println("ActiveMQ Producer Exception: " + e);
                e.printStackTrace();
            }
		}
		
	}
	
	/**
	 * The Consumer
	 */
	public class Consumer implements Runnable, ExceptionListener {
		
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageConsumer consumer = null;
		Message message = null;
		
		String CHESS_STATUS = "Chess.Status";
		String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
		
		int WAIT = 2000;
		
		/**
		 * Disconnects Consumer
		 */
		public void disconnect() {
			try {
				consumer.close();
				session.close();
				connection.close();
				
				System.out.println("Consumer disconnected");
			} catch (JMSException e) {
				System.out.println("ActiveMQ Disconnect Exception: " + e);
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQ.this.URL);
				
	            connection = connectionFactory.createConnection();
	            connection.start();
	            connection.setExceptionListener(this);
	            
	            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	            
	            destination = session.createTopic(CHESS_STATUS);
	            
	            consumer = session.createConsumer(destination);
			} catch (Exception e) {
                System.out.println("ActiveMQ Consumer Exception: " + e);
                e.printStackTrace();
            }
			
			while (ActiveMQ.this.active) {
				try {
	                message = consumer.receive(WAIT);
	 
	                if (message instanceof TextMessage) {
	                    TextMessage textMessage = (TextMessage) message;
	                    String text = textMessage.getText();
	                    System.out.println("Received: " + text);
	                } else {
	                    System.out.println("Received: " + message);
	                }

	            } catch (Exception e) {
	                System.out.println("ActiveMQ Consumer Message Exception: " + e);
	                e.printStackTrace();
	            }
			}
		}

		@Override
		public void onException(JMSException arg0) {
			System.out.println("JMS Exception occured. Shutting down client.");
		}
		
	}
	
}
