package com.redhat.demo;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttProducer extends Producer {

	MqttClient client;
	MqttConnectOptions options;
	MemoryPersistence persistence;
	
	public MqttProducer(String brokerURL, String user, String password, String queueName) {
	
		// MemoryPersistence persistence = new MemoryPersistence();
		
		System.out.println("Starting MQTT Producer");
		
		try {
			System.out.println("Creating MQTT-Client");
			client = new MqttClient(brokerURL, "mqtt.temp.receiver");
			System.out.println("Created!");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}
		
		options = new MqttConnectOptions ();
		options.setUserName("admin");
		options.setPassword("change12_me".toCharArray());
		
		try {
			System.out.println("Trying to connect to "+brokerURL);
			client.connect(options);
			System.out.println("Connected!");
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(System.out);
		}
		
	}
	
	public void run(String data, String devID, String devType) throws MqttPersistenceException, MqttException {
		
		MqttMessage message = new MqttMessage();
		
		message.setPayload(data.getBytes());
		
		client.publish("iotdemo/"+devType+"/"+devID, message);
		
		System.out.println("Message published");
		
	}
	
	public void close() throws MqttException{

		client.disconnect();
		client.close();
		
	}
}
