package com.redhat.demo.smart_gateway;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@XmlRootElement(name = "dataSet")
@XmlType(propOrder = { "deviceType", "deviceID", "payload", "count", "timestamp" })
// temperature, 4711, 70,1,14.0
@CsvRecord(separator = ",")
public class Dataset {
	@DataField(pos = 1, required = true) 
	private String	deviceType;
	@DataField(pos = 2, required = true) 
	private String	deviceID;
	@DataField(pos = 3, required = true) 
	private	String	payload;
	@DataField(pos = 4, required = true) 
	private String	timestamp;
	@DataField(pos = 5, required = true)
	private String	count;
	
	public Dataset()
	{
		this.timestamp 	= "";
		this.deviceType = "";
		this.deviceID	= "";
		this.count		= "";
		this.payload	= "";
	}
	
	public Dataset(String time, String devType, String devID, String count, String pay )
	{
		this.timestamp 	= time;
		this.deviceType = devType;
		this.deviceID	= devID;
		this.count		= count;
		this.payload	= pay;
	}


	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the deviceID
	 */
	public String getDeviceID() {
		return deviceID;
	}

	/**
	 * @param deviceID the deviceID to set
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	/**
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
}