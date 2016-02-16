package com.redhat.demo.iot.datacenter.monitor;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AMQTester {

	private ActiveMQConnectionFactory 	connectionFactory;
	private	Connection				  	connection;
	
	public AMQTester( ) {
	}

	public boolean testAvailability( String brokerURL) {
		boolean res = false;
		
        try {
        	// Create a ConnectionFactory
            connectionFactory = new ActiveMQConnectionFactory("admin", "change12_me", brokerURL);

            // Create a Connection
            connection = connectionFactory.createConnection();
        	
			connection.start();
			
			connection.close();
			
			res = true;
		} catch (JMSException e) {
			
			res = false;
			
		}
		
		return res;
	}
}
