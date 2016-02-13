package com.redhat.demo.iot.datacenter.monitor;

public class RulesServer {

	public static void main(String[] args) {
		DataSet receivedMessage = null;
		App 	app 			= new App();
    	
    	app.waitForBrokers();
    	
    	app.connectToDistributedCache();
			
		while ( 1 ==1 ) {
			
			// Read a message from queue
			receivedMessage = app.readDataFromQueue();		
			if ( receivedMessage != null ) {
				
				// Send message to BRMS Server for rules and CEP processing
				receivedMessage = app.callBRMS(receivedMessage);
				
				// Did BRMS identify a situation which needs an alert?
				if ( receivedMessage.getErrorCode() != 0 ) {
					app.callBPM(receivedMessage);
					app.sendAlarmToMqtt(receivedMessage);
					app.storeInfoInDistributedCache(receivedMessage);
				}
				
				// Did BRMS decide this message should be forwarded
				if ( receivedMessage.getRequired() == 1 ) {
					
				}
					            	            
			}
            
		}

	}

}
