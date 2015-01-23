/**
 * 
 */
package com.finin.jboss.samples;

import javax.jws.WebService;
import javax.xml.namespace.QName;

import static com.finin.jboss.samples.SimpleWSS4J.*;

/**
 * @author a208220 - Juan Manuel CABRERA
 *
 */
@WebService(wsdlLocation = "WEB-INF/wsdl/test.xml", targetNamespace = TNS, serviceName = SN, portName = PN)
public interface SimpleWSS4J {

	String	TNS			= "http://test";
	String	SN			= "SimpleWSS4JImpl";
	String	PN			= SN + "Port";

	QName		SERVICE	= new QName(TNS, SN);
	QName		PORT		= new QName(TNS, PN);

	String echo(String msg);

}
