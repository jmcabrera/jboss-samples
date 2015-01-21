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
@WebService(wsdlLocation = "wsdl/test.xml", portName = PN, serviceName = SN, targetNamespace = TNS)
public interface SimpleWSS4J {

	String	TNS			= "http://test";
	String	SN			= "SimpleWSS4JImpl";
	String	PN			= SN + "Port";

	QName		SERVICE	= new QName(TNS, SN);
	QName		PORT		= new QName(TNS, PN);

	String echo(String msg);

}
