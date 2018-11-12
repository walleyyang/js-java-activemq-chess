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
import org.apache.activemq.command.ActiveMQBytesMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The ActiveMQ object.
 */
public class ActiveMQ {

	private final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
	private boolean active = true;
	private Producer producer = null;
	private Consumer consumer = null;
	
	ActiveMQ() {
		producer = new Producer();
		consumer = new Consumer();
		
		new Thread(consumer).start();
	}
	
	/** 
	 * Sets the status of ActiveMQ
	 */	
	public void setActive() {
		this.active = false;
	}
	
	/** 
	 * Sends the message
	 * 
	 * @param gameStatus The game status in JSON format
	 */
	public void sendMessage(String gameStatus) {
		producer.sendMessage(gameStatus);
	}
	
	/**
	 * The Producer
	 */
	public class Producer {
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageProducer producer = null;
		
		String CHESS_STATUS_VALIDATED = "Chess.Status.Validated";
		
		Producer() {
			try {
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQ.this.URL);
				
			    connection = connectionFactory.createConnection();
			    connection.start();
			    
			    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			    
			    destination = session.createTopic(CHESS_STATUS_VALIDATED);
			    
			    producer = session.createProducer(destination);
	            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			}  catch (Exception e) {
				System.out.println("ActiveMQ Producer Exception: " + e);
				e.printStackTrace();
			}
		}
		
		/** 
		 * Sends message to ActiveMQ.
		 */
		public void sendMessage(String gameStatus) {
			try {
				if(!gameStatus.isEmpty()) {
					TextMessage message = session.createTextMessage(gameStatus);
					
					producer.send(message);
				}
				
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
	                Message message = consumer.receive(WAIT);

	                if(message instanceof ActiveMQBytesMessage) {
	                	byte[] receivedMessage = ((org.apache.activemq.command.Message) message).getContent().getData();
	                	String text = new String(receivedMessage);
	                	
	                	ObjectMapper mapper = new ObjectMapper();
		                GameMove gameMove = mapper.readValue(text, GameMove.class);
	                    
		                int gameMoveId = gameMove.getId();
		                
		                for(Game game : Games.getCurrentGames()) {
		                	if(game.getId() == gameMoveId) {
		                		game.validateMove(gameMove);
		                	}
		                }
	                } else if(message instanceof TextMessage) {
	                    TextMessage textMessage = (TextMessage) message;
	                    String text = textMessage.getText();

	                    ObjectMapper mapper = new ObjectMapper();
		                GameMove gameMove = mapper.readValue(text, GameMove.class);
	                    
		                int gameMoveId = gameMove.getId();
		                
		                for(Game game : Games.getCurrentGames()) {
		                	if(game.getId() == gameMoveId) {
		                		game.validateMove(gameMove);
		                	}
		                }
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
