package com.redhat.demo.iot.gateway.rules_cep;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.activemq.ActiveMQConnectionFactory;

public class App 
{
    private static final Logger log = Logger.getLogger(CepServer.class.getName());
	
    private static final String DEFAULT_SOURCE_AMQ_BROKER	= "tcp://smartgateway:61616";
    private static final String DEFAULT_TARGET_AMQ_BROKER	= "tcp://192.168.178.92:61616";
    private static final String DEFAULT_SOURCE_QUEUE 		= "message.to.rules";
    private static final String DEFAULT_TARGET_QUEUE		= "message.to.datacenter";
    private static final String DEFAULT_BROKER_ADMIN_UID	= "admin";
    private static final String DEFAULT_BROKER_ADMIN_PASSWD	= "change12_me";
    
		 
    public static void main( String[] args ) throws InterruptedException, JMSException, JAXBException
    {
    	String 	messageFromQueue;
    	
    	String 	sourceAMQBroker = System.getProperty("SOURCE_AMQ_BROKER",DEFAULT_SOURCE_AMQ_BROKER);
    	String 	targetAMQBroker = System.getProperty("TARGET_AMQ_BROKER",DEFAULT_TARGET_AMQ_BROKER);
    	String 	sourceQueue 	= System.getProperty("SOURCE_QUEUE",DEFAULT_SOURCE_QUEUE);
    	String 	targetQueue 	= System.getProperty("TARGET_QUEUE",DEFAULT_TARGET_QUEUE);
    	String  brokerUID		= System.getProperty("BROKER_ADMIN_UID",DEFAULT_BROKER_ADMIN_UID);
    	String  brokerPassword  = System.getProperty("BROKER_ADMIN_PASSWD",DEFAULT_BROKER_ADMIN_PASSWD);

        System.out.println("TARGET_AMQ_BROKER = " + targetAMQBroker);
    
	
    	System.out.println(" Check if remote AMQ-Broker are already available");
    	AMQTester tester = new AMQTester(); 
    	
    	tester.waitForBroker(sourceAMQBroker);
    	
		Consumer consumer = new Consumer(sourceQueue, sourceAMQBroker, brokerUID, brokerPassword);
		Producer producer = new Producer(targetQueue, targetAMQBroker, brokerUID, brokerPassword);
	
		CepServer cepServer = new CepServer();
		
		while ( true ) {
			messageFromQueue = consumer.run(20000);		
			
			if ( messageFromQueue != null ) {
				
	            // Convert TextMessage to DataSet via jaxb unmarshalling
	            JAXBContext jaxbContext = JAXBContext.newInstance(DataSet.class);
	            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	
	            StringReader reader = new StringReader( messageFromQueue );
	            DataSet event = (DataSet) unmarshaller.unmarshal(reader);
		
	            event.setRequired(0);	    
	         
            	event = cepServer.insert( event);
      	      	
	            System.out.println("Rules Event-DeviceType <"+event.getDeviceType()+">");
	                     
	            if ( event.getRequired() == 1 ) {
	            	
	            	System.out.println("Have to send the message " + event.toCSV());
	            	
	            	producer.run(event);
	            		
	            } 
	            	            
			}
            
		}
		
    }
}
