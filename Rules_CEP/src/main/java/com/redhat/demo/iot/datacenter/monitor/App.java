package com.redhat.demo.iot.datacenter.monitor;

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
    private static final Logger log = Logger.getLogger(BRMSServer.class.getName());
	
    private static final String DEFAULT_MQTT_BROKER	= "192.168.178.103";
	
    
	public static String sourceBrokerURL = "tcp://receiver:61616";
	public static String sourceQueueName = "message.to.rules";
	 
    public static void main( String[] args ) throws InterruptedException, JMSException, JAXBException
    {
    	String				cacheValue=null;
    	String 				messageFromQueue;
    	String 				brokerURLMQTT = "tcp://" + System.getProperty("mqttBrokerURL",DEFAULT_MQTT_BROKER) +  ":1883";
            	
    	System.out.println(" Check if remote AMQ-Broker are already available");
    	AMQTester tester = new AMQTester(); 
    	
    	while( tester.testAvailability( sourceBrokerURL ) == false ) {
    		System.out.println(" AMQ-Broker " + sourceBrokerURL + " not yet available ");
    		Thread.sleep(10000);
    	}
    	
    	System.out.println(" AMQ-Broker " + sourceBrokerURL + " ready to work! ");
    	
		Consumer consumer = new Consumer(sourceQueueName, sourceBrokerURL);
	
		BRMSServer brmsServer = new BRMSServer();
		
		while ( 1 ==1 ) {
			messageFromQueue = consumer.run(20000);		
			
			if ( messageFromQueue != null ) {
				
	            // Convert TextMessage to DataSet via jaxb unmarshalling
	            JAXBContext jaxbContext = JAXBContext.newInstance(DataSet.class);
	            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	
	            StringReader reader = new StringReader( messageFromQueue );
	            DataSet event = (DataSet) unmarshaller.unmarshal(reader);
		
	            event.setRequired(0);	    
	            
	            System.out.println("checking with cache if we know this event already.");
	                        
	            System.out.println("Cached value for <"+event.getDeviceType()+event.getDeviceID()+"> is <"+cacheValue+">");
	            
	            if ( ( cacheValue == null ) || ( cacheValue.contains("solved")) ) {
	         
	            	event = brmsServer.insert( event);
	      	      	
		            System.out.println("Rules Event-DeviceType <"+event.getDeviceType()+">");
		                     
		            if ( event.getRequired() == 1 ) {
		            	
				// Forward message
		            	
		            	System.out.println("Need to turn on alarm light!");
//		            	MQTTProducer producer = new MQTTProducer(brokerURLMQTT, "admin", "change12_me", "rules_server");
//		            	producer.run("iotdemocommand/light", "an");
		            		
		            } 

	            } else {
	            	System.out.println("No need to call BRMS, we know this already");
	            }
	            	            
			}
            
		}
		
    }
}
