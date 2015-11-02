package com.redhat.fuse.sample.rest.processor;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.fuse.sample.rest.model.DataInput;
import com.redhat.fuse.sample.rest.model.DataOutput;

public class Processor {

	private final static Logger LOGGER = LoggerFactory.getLogger(Processor.class);
	
	public DataOutput doSomething(@Body DataInput dataInput) {
		DataOutput dataOutput = new DataOutput();
		
		dataOutput.setTransactionId(UUID.randomUUID().toString());
		
		// <calendarData>2015-10-29T09:59:50.464+07:00</calendarData>
		dataOutput.setCalendarData(dataInput.getCalendarData());
		dataOutput.setStringData(dataInput.getStringData());
		dataOutput.setIntegerData(dataInput.getIntegerData());
		
		dataOutput.setStatus(true);
		
		return dataOutput;
	}
	
	public DataOutput getSomething(@Body List<String> requestList) {
		
		LOGGER.info(requestList.toString());
		
		DataOutput dataOutput = new DataOutput();
		
		dataOutput.setTransactionId(requestList.get(0));
		
		dataOutput.setCalendarData(Calendar.getInstance());
		dataOutput.setStringData(requestList.get(1));
		dataOutput.setIntegerData(123);
		
		dataOutput.setStatus(true);
		
		return dataOutput;
	}
	
	public DataInput createDataInput() {
		DataInput dataOutput = new DataInput();
		
		
		// <calendarData>2015-10-29T09:59:50.464+07:00</calendarData>
		dataOutput.setCalendarData(Calendar.getInstance());
		dataOutput.setStringData("asd");
		dataOutput.setIntegerData(123);
		
		return dataOutput;
	}

}
