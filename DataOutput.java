
package com.redhat.fuse.sample.rest.model;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
		"transactionId",
		"stringData",
	    "integerData",
	    "calendarData",
	    "status"
	})
@XmlRootElement(name="dataOutput")
public class DataOutput {

	private String transactionId;
	private String stringData;
	private Integer integerData;
	private Calendar calendarData;
	private Boolean status;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getStringData() {
		return stringData;
	}
	public void setStringData(String stringData) {
		this.stringData = stringData;
	}
	public Integer getIntegerData() {
		return integerData;
	}
	public void setIntegerData(Integer integerData) {
		this.integerData = integerData;
	}
	public Calendar getCalendarData() {
		return calendarData;
	}
	public void setCalendarData(Calendar calendarData) {
		this.calendarData = calendarData;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}
