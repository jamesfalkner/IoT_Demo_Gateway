package com.redhat.demo.iot.datacenter.monitor;

import java.util.Properties;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;

public class DataGridHelper {
	
	private RemoteCacheManager cacheManager;
	private RemoteCache<String, String> cache;
	private Properties properties;
	
	
	public DataGridHelper( ) {
		Properties properties = new Properties();
		properties.setProperty(ConfigurationProperties.SERVER_LIST, "jdg:11222");
		
//		cacheManager = new RemoteCacheManager(properties);
		cacheManager = new RemoteCacheManager("jdg:11222");
		cache = cacheManager.getCache("default");
	}

	public void put(String key, String value){
		cache.put(key, value);
	}
	
	public String get(String key) {
		return cache.get(key);
	}
	
	public void remove(String key){
		cache.remove(key);
	}

}
