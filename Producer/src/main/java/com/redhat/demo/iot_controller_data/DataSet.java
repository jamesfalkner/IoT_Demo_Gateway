package com.redhat.demo.iot_controller_data;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "dataSet")
@XmlType(propOrder = { "timestamp", "deviceType", "deviceID", "count", "payload", "unit", "required"})
public class DataSet {
	private int		count;
	private	int		payload;
//	private int		deviceID;
	

	public DataSet()
	{
		this.count		= 0;
		this.payload	= 0;
	}
	
	public DataSet(int count, int pay )
	{
		this.count		= count;
		this.payload	= pay;
	}


	/**
	 * @return the payload
	 */
	public int getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	public void setPayload(int payload) {
		this.payload = payload;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
//	public int getDeviceID() {
//		return deviceID;
//	}
//
//	public void setDeviceID(int deviceID) {
//		this.deviceID = deviceID;
//	}
}
	