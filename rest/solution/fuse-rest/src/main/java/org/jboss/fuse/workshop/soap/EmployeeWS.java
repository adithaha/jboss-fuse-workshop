package org.jboss.fuse.workshop.soap;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.11.fuse-000243-redhat-1
 * 2019-03-26T14:24:11.552+07:00
 * Generated source version: 3.1.11.fuse-000243-redhat-1
 * 
 */
@WebServiceClient(name = "EmployeeWS", 
                  wsdlLocation = "file:/Users/adityanugraha/workspace/workspace-workshop/employeeWS.wsdl",
                  targetNamespace = "http://soap.workshop.fuse.jboss.org/") 
public class EmployeeWS extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://soap.workshop.fuse.jboss.org/", "EmployeeWS");
    public final static QName EmployeeWSPort = new QName("http://soap.workshop.fuse.jboss.org/", "EmployeeWSPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/Users/adityanugraha/workspace/workspace-workshop/employeeWS.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(EmployeeWS.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/Users/adityanugraha/workspace/workspace-workshop/employeeWS.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public EmployeeWS(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public EmployeeWS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EmployeeWS() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public EmployeeWS(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public EmployeeWS(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public EmployeeWS(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns EmployeeWSPortType
     */
    @WebEndpoint(name = "EmployeeWSPort")
    public EmployeeWSPortType getEmployeeWSPort() {
        return super.getPort(EmployeeWSPort, EmployeeWSPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EmployeeWSPortType
     */
    @WebEndpoint(name = "EmployeeWSPort")
    public EmployeeWSPortType getEmployeeWSPort(WebServiceFeature... features) {
        return super.getPort(EmployeeWSPort, EmployeeWSPortType.class, features);
    }

}
