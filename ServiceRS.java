package com.redhat.fuse.sample.rest.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.fuse.sample.rest.model.DataInput;
import com.redhat.fuse.sample.rest.model.DataOutput;

@WebService
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceRS {

	@WebMethod
    @POST
    @Path(value="doSomethingPost")
    public DataOutput doSomethingPost(DataInput request) {
		return null;
	}
	
	@WebMethod
    @GET
    @Path(value="doSomethingGet/{transactionId}/{stringData}")
    public DataOutput doSomethingGet(@PathParam("transactionId") String transactionId, @PathParam("stringData") String stringData) {
		return null;
	}
	
	@WebMethod
    @PUT
    @Path(value="doSomethingPut")
    public DataOutput doSomethingPut(DataInput request) {
		return null;
	}
	
	
	

}
