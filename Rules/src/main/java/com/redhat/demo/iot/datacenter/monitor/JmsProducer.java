package com.redhat.demo.iot.datacenter.monitor;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsProducer {

	private ConnectionFactory factory;
	 private Connection connection;
	 private Session session;
	 private MessageProducer producer;

	 public JmsProducer(String queueName, String brokerURL) throws JMSException
	 {
		// setup the connection to ActiveMQ
	    factory = new ActiveMQConnectionFactory("admin", "admin", brokerURL);
	    	
	    connection = factory.createConnection();
	    connection.start();
	    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    Destination destination = session.createQueue(queueName);
	    producer = session.createProducer(destination);
	 }

	 public void run(String data) 
	 {
   
		 Message message;
		try {
			message = session.createTextMessage( data );
			producer.send(message);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	 }

    public void close() throws JMSException
    {
        if (connection != null)
        {
            connection.close();
        }
    }

	
}
