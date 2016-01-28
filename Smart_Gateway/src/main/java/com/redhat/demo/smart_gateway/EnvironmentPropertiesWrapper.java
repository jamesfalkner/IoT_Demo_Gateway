package com.redhat.demo.smart_gateway;

public final class EnvironmentPropertiesWrapper {
	 
	private String brokerURL;
	
    public String getBrokerURL() {
    	return System.getenv("Broker_URL");
    }
    
    public void setBrokerURL(String foo)
    {
        brokerURL=foo;
    }
}
