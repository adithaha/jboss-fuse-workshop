
package org.jboss.fuse.workshop.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getEmployeeAllResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getEmployeeAllResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://soap.workshop.fuse.jboss.org/}employeeList" minOccurs="0" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getEmployeeAllResponse", propOrder = {
    "_return"
})
public class GetEmployeeAllResponse {

    @XmlElement(name = "return", namespace = "http://soap.workshop.fuse.jboss.org/")
    protected EmployeeList _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeList }
     *     
     */
    public EmployeeList getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeList }
     *     
     */
    public void setReturn(EmployeeList value) {
        this._return = value;
    }

}
