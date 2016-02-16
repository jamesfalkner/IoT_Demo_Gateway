package com.redhat.demo.iot.gateway.rules_cep;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
	
	 private ConnectionFactory factory;
	 private Connection connection;
	 private Session session;
	 private MessageProducer producer;
 
	 public Producer(String queueName, String brokerURL, String uid, String passwd) throws JMSException
	 {
		// setup the connection to ActiveMQ
	    factory = new ActiveMQConnectionFactory(uid, passwd, brokerURL);
	    	
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        producer = session.createProducer(destination);
	 }
 
	 public void run(DataSet data) {
		 // Convert message to XML
         String result;
         StringWriter sw = new StringWriter();
         
         try {
             JAXBContext dataSetContext = JAXBContext.newInstance(DataSet.class);
             Marshaller dataSetMarshaller = dataSetContext.createMarshaller();
             dataSetMarshaller.marshal(data, sw);
             result = sw.toString();
         } catch (JAXBException e) {
             throw new RuntimeException(e);
         }
         
         run(result);
         
	 }
	 
	 public void run(String data)
	 {
    
        Message message;
		try {
			message = session.createTextMessage( data );
			System.out.println("Sending " + data);
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
