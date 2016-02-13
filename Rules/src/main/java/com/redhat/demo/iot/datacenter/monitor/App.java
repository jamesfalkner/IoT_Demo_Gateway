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
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class App 
{
    private static final Logger log = Logger.getLogger(BRMSServer.class.getName());
	
    private static final String DEFAULT_MQTT_BROKER			= "192.168.178.103";
    private static final String DEFAULT_DATACENTER_BROKER	= "192.168.178.103";
	
	private static String sourceBrokerURL = "tcp://receiver:61616";
	private static String sourceQueueName = "message.to.rules";
	
	private String	cacheValue;
	private String 	brokerURLMQTT;
	private String 	brokerURLDatacenter;
	
	private DataGridHttpHelper 	jdgHelper;
	private Consumer 			consumer;
	private BRMSServer 			brmsServer;
	private JmsProducer			datacenterJmsBroker;
    
	public App() {
		
		cacheValue 			= null;
		brokerURLMQTT 		= "tcp://" + System.getProperty("mqttBrokerURL",DEFAULT_MQTT_BROKER) +  ":1883";
		brokerURLDatacenter = "tcp://" + System.getProperty("datacenterBrokerURL",DEFAULT_MQTT_BROKER) +  ":61616";
		
		brmsServer 			= new BRMSServer();
	}
	
	public void waitForBrokers() {
	
	 	System.out.println(" Check if remote AMQ-Broker are already available");
    	AMQTester tester = new AMQTester(); 
    	
    	while( tester.testAvailability( sourceBrokerURL ) == false ) {
    		System.out.println(" AMQ-Broker " + sourceBrokerURL + " not yet available ");
    		
    		try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	System.out.println(" AMQ-Broker " + sourceBrokerURL + " ready to work! ");
    	
    	try {
    		// provides JMS Messages which are created by the broker running on the Gateway
			consumer 			= new Consumer(sourceQueueName, sourceBrokerURL);
			
			// target to deliver messages for further processing
			datacenterJmsBroker = new JmsProducer("message.from.rules", brokerURLDatacenter);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
	}
	
	public void connectToDistributedCache() {
		// Connecting to JBDG
    	jdgHelper = new DataGridHttpHelper();
	}
	
	public void storeInfoInDistributedCache(DataSet message) {
		System.out.println("Pushing this event to distributed Cache");
    	try {
			jdgHelper.putMethod("http://jdg:8080/rest/default/"+message.getDeviceType()+message.getDeviceID(),"known");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void callBPM(DataSet event) {
		
		BPMClient bpmClient = new BPMClient();
		
		bpmClient.doCall("http://bpm:8080/business-central", 
    				     "com.redhat.demo.iot.datacenter:HumanTask:1.0",
    				     "IoT_Human_Task.low_voltage",
    				     "psteiner", "change12_me",
    				     event);
	}
	
	public DataSet readDataFromQueue() {
		
		DataSet readMessage=null;
		String	stringMessageFromQueue;
		
		stringMessageFromQueue = consumer.run(20000);
		
		if ( stringMessageFromQueue != null ) {
			// Convert TextMessage to DataSet via jaxb unmarshalling
            JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(DataSet.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader( stringMessageFromQueue );
	            readMessage= (DataSet) unmarshaller.unmarshal(reader);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            readMessage.setRequired(0);	    
		}
		
		return readMessage;
		
	}
	
	public Boolean checkIfKnown(DataSet message) {
		Boolean res = false;
		
		try {
			cacheValue = jdgHelper.getMethod("http://jdg:8080/rest/default/"+message.getDeviceType()+message.getDeviceID());
		} catch (IOException e) {
			cacheValue=null;
		}
		
		System.out.println("Cached value for <"+message.getDeviceType()+message.getDeviceID()+"> is <"+cacheValue+">");
        
		if ( ( cacheValue == null ) || ( cacheValue.contains("solved")) ) {
			res = false;
		} else {
			res = true;
		}
		
		return res;
	}
	
	public void forwardMessage ( DataSet event ) {
		
		// Convert message back to XML
        String result;
        StringWriter sw = new StringWriter();
        
        try {
            JAXBContext dataSetContext = JAXBContext.newInstance(DataSet.class);
            Marshaller dataSetMarshaller = dataSetContext.createMarshaller();
            dataSetMarshaller.marshal(event, sw);
            result = sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
		
		datacenterJmsBroker.run(result);
		
	}
	
	public DataSet callBRMS(DataSet message) {
		
		return brmsServer.insert( message );
      
	}
	 
	public void sendAlarmToMqtt(DataSet message) {
    	System.out.println("Need to turn on alarm light!");
    	MQTTProducer producer = new MQTTProducer(brokerURLMQTT, "admin", "change12_me", "rules_server");
    	
    	try {
			producer.run("iotdemocommand/light", "an");
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
