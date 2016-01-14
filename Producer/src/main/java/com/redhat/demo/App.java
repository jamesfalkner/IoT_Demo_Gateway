package com.redhat.demo;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.redhat.demo.iot_controller_data.DummyDataGenerator;


/**
 * Hello world!
 *
 */
public class App 
{
	
	// Default Values for message producer
	private static final String DEFAULT_DEVICEID     = "42";
	private static final String DEFAULT_DEVICETYPE   = "temperature";
	private static final String DEFAULT_INITIALVALUE = "70";
    private static final String DEFAULT_COUNT 		 = "1";
    private static final String DEFAULT_UNIT		 = "C";
    private static final String DEFAULT_WAIT		 = "1";
    private static final String DEFAULT_RECEIVER	 = "localhost";
 
	 
    public static void main( String[] args ) throws Exception
    {
    	
    	DummyDataGenerator dummy = new DummyDataGenerator();
    	Producer		   producer;
       
        int initialValue = Integer.parseInt(System.getProperty("initialValue", DEFAULT_INITIALVALUE));
        int count = Integer.parseInt(System.getProperty("count", DEFAULT_COUNT));
        int waitTime = Integer.parseInt(System.getProperty("waitTime", DEFAULT_WAIT));
        String brokerURLMQTT = "tcp://" + System.getProperty("receiverURL",DEFAULT_RECEIVER) +  ":1883";
        String deviceID = System.getProperty("deviceID",DEFAULT_DEVICEID);
        String deviceType = System.getProperty("deviceType",DEFAULT_DEVICETYPE);
         
        
        dummy.createInitialDataSet( initialValue ); 
        	
    	producer = new MqttProducer(brokerURLMQTT, "admin", "admin", "mqtt.receiver");
        
        System.out.println(dummy.getDataSetCSV());
        
        int counter = 0;
        while ( counter < count ) {
			
    		producer.run( dummy.getDataSetCSV(), deviceID, deviceType );
        	
			dummy.updateDataSet();
			
			counter++;
			
			Thread.sleep ( waitTime * 1000 );
        }
		    
        producer.close();
    }
}
