package com.redhat.fuse.sample.rest.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.redhat.fuse.sample.rest.model.DataInput;
import com.redhat.fuse.sample.rest.model.DataOutput;

@WebService(targetNamespace = "http://redhat.com/fuse/sample/soap", name = "ServiceWS")
public interface ServiceWS {

	@WebResult(name = "dataOutput", targetNamespace = "http://redhat.com/fuse/sample/soap")
	@WebMethod(operationName = "doSomething")
	DataOutput doSomething(
			@WebParam(name = "dataInput", targetNamespace = "http://redhat.com/fuse/sample/soap")
			DataInput request);

}
