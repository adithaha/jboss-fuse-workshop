package com.redhat.fuse.sample.rest.model;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
	    "stringData",
	    "integerData",
	    "calendarData",
	})
@XmlRootElement(name="dataInput")
public class DataInput {

	private String stringData;
	private Integer integerData;
	private Calendar calendarData;
	
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
}
