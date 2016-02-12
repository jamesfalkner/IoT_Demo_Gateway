package com.redhat.demo.iot.datacenter.monitor;

import java.util.logging.Logger;

import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.time.SessionClock;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.FactHandle;
import java.util.concurrent.TimeUnit;

public class BRMSServer {
  
    private KieServices 	kieServices;
    private KieContainer 	kieContainer;
    private KieSession 		kieSession;
    
    private static final String CEP_STREAM = "IOTStream";
    
    public BRMSServer() {
    	initKieSession();
    }
    
    private void initKieSession() {
    	kieServices = KieServices.Factory.get();
	    kieContainer = this.kieServices.getKieClasspathContainer();
    	kieSession = kieContainer.newKieSession("ksession-rules");
    }
    
    public DataSet insert( DataSet event ) {
    	SessionClock clock = kieSession.getSessionClock();
    	
		SessionPseudoClock pseudoClock = (SessionPseudoClock) clock;
		EntryPoint ep = kieSession.getEntryPoint(CEP_STREAM);

		// First insert the fact
		FactHandle factHandle = ep.insert(event);
		
		// Now let's fire the rules
		kieSession.fireAllRules();

		// And then advance the clock
		// We only need to advance the time when dealing with Events. Our facts don't have timestamps.
		long advanceTime = 100;	// ?? need to make this more meaningfull

		pseudoClock.advanceTime(advanceTime, TimeUnit.MILLISECONDS);
		
		return event;
	}

}
