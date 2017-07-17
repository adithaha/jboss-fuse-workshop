package org.jboss.fuse.workshop.rest.employee;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="employee")
public class Employee {

	private Integer id;
	private String name;
	private String address;
	private Integer benefitId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getBenefitId() {
		return benefitId;
	}
	public void setBenefitId(Integer benefitId) {
		this.benefitId = benefitId;
	}
	
	
}