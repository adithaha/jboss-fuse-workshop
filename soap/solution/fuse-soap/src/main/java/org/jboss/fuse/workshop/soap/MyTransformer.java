package org.jboss.fuse.workshop.soap;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperty;

public class MyTransformer {
	
	
    public void putPhoneList(
    		@ExchangeProperty(value="employee") Employee employee,
    		@Body List<Phone> phoneList
    		) {
    	
    	employee.setPhoneList(phoneList);
    }

    public EmployeeList createEmployeeList(
    		) {
    	EmployeeList eList = new EmployeeList();
    	return eList;
    }
    
    public void putEmployeeList(
    		@ExchangeProperty(value="employeeList") EmployeeList employeelist,
    		@Body List<Employee> employeeL
    		
    		) {
    	employeelist.setEmployeeList(employeeL);
    }
    
}