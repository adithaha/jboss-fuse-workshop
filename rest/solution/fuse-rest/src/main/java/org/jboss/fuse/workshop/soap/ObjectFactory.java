
package org.jboss.fuse.workshop.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.jboss.fuse.workshop.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Employee_QNAME = new QName("http://soap.workshop.fuse.jboss.org/", "employee");
    private final static QName _EmployeeList_QNAME = new QName("http://soap.workshop.fuse.jboss.org/", "employeeList");
    private final static QName _Phone_QNAME = new QName("http://soap.workshop.fuse.jboss.org/", "phone");
    private final static QName _GetEmployeeAll_QNAME = new QName("http://soap.workshop.fuse.jboss.org/", "getEmployeeAll");
    private final static QName _GetEmployeeAllResponse_QNAME = new QName("http://soap.workshop.fuse.jboss.org/", "getEmployeeAllResponse");
    private final static QName _AddEmployee_QNAME = new QName("http://soap.workshop.fuse.jboss.org/", "addEmployee");
    private final static QName _AddEmployeeResponse_QNAME = new QName("http://soap.workshop.fuse.jboss.org/", "addEmployeeResponse");
    private final static QName _GetEmployee_QNAME = new QName("http://soap.workshop.fuse.jboss.org/", "getEmployee");
    private final static QName _GetEmployeeResponse_QNAME = new QName("http://soap.workshop.fuse.jboss.org/", "getEmployeeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.jboss.fuse.workshop.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Employee }
     * 
     */
    public Employee createEmployee() {
        return new Employee();
    }

    /**
     * Create an instance of {@link EmployeeList }
     * 
     */
    public EmployeeList createEmployeeList() {
        return new EmployeeList();
    }

    /**
     * Create an instance of {@link Phone }
     * 
     */
    public Phone createPhone() {
        return new Phone();
    }

    /**
     * Create an instance of {@link GetEmployeeAll }
     * 
     */
    public GetEmployeeAll createGetEmployeeAll() {
        return new GetEmployeeAll();
    }

    /**
     * Create an instance of {@link GetEmployeeAllResponse }
     * 
     */
    public GetEmployeeAllResponse createGetEmployeeAllResponse() {
        return new GetEmployeeAllResponse();
    }

    /**
     * Create an instance of {@link AddEmployee }
     * 
     */
    public AddEmployee createAddEmployee() {
        return new AddEmployee();
    }

    /**
     * Create an instance of {@link AddEmployeeResponse }
     * 
     */
    public AddEmployeeResponse createAddEmployeeResponse() {
        return new AddEmployeeResponse();
    }

    /**
     * Create an instance of {@link GetEmployee }
     * 
     */
    public GetEmployee createGetEmployee() {
        return new GetEmployee();
    }

    /**
     * Create an instance of {@link GetEmployeeResponse }
     * 
     */
    public GetEmployeeResponse createGetEmployeeResponse() {
        return new GetEmployeeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Employee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.workshop.fuse.jboss.org/", name = "employee")
    public JAXBElement<Employee> createEmployee(Employee value) {
        return new JAXBElement<Employee>(_Employee_QNAME, Employee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmployeeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.workshop.fuse.jboss.org/", name = "employeeList")
    public JAXBElement<EmployeeList> createEmployeeList(EmployeeList value) {
        return new JAXBElement<EmployeeList>(_EmployeeList_QNAME, EmployeeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Phone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.workshop.fuse.jboss.org/", name = "phone")
    public JAXBElement<Phone> createPhone(Phone value) {
        return new JAXBElement<Phone>(_Phone_QNAME, Phone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.workshop.fuse.jboss.org/", name = "getEmployeeAll")
    public JAXBElement<GetEmployeeAll> createGetEmployeeAll(GetEmployeeAll value) {
        return new JAXBElement<GetEmployeeAll>(_GetEmployeeAll_QNAME, GetEmployeeAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.workshop.fuse.jboss.org/", name = "getEmployeeAllResponse")
    public JAXBElement<GetEmployeeAllResponse> createGetEmployeeAllResponse(GetEmployeeAllResponse value) {
        return new JAXBElement<GetEmployeeAllResponse>(_GetEmployeeAllResponse_QNAME, GetEmployeeAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.workshop.fuse.jboss.org/", name = "addEmployee")
    public JAXBElement<AddEmployee> createAddEmployee(AddEmployee value) {
        return new JAXBElement<AddEmployee>(_AddEmployee_QNAME, AddEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.workshop.fuse.jboss.org/", name = "addEmployeeResponse")
    public JAXBElement<AddEmployeeResponse> createAddEmployeeResponse(AddEmployeeResponse value) {
        return new JAXBElement<AddEmployeeResponse>(_AddEmployeeResponse_QNAME, AddEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.workshop.fuse.jboss.org/", name = "getEmployee")
    public JAXBElement<GetEmployee> createGetEmployee(GetEmployee value) {
        return new JAXBElement<GetEmployee>(_GetEmployee_QNAME, GetEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.workshop.fuse.jboss.org/", name = "getEmployeeResponse")
    public JAXBElement<GetEmployeeResponse> createGetEmployeeResponse(GetEmployeeResponse value) {
        return new JAXBElement<GetEmployeeResponse>(_GetEmployeeResponse_QNAME, GetEmployeeResponse.class, null, value);
    }

}
