/**
 * 
 */
package com.finin.jboss.samples;

import static com.finin.jboss.samples.SimpleWSS4J.TNS;
import static com.finin.jboss.samples.SimpleWSS4JImpl.PN;
import static com.finin.jboss.samples.SimpleWSS4JImpl.SN;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.namespace.QName;

/**
 * @author a208220 - Juan Manuel CABRERA
 *
 */
@WebService(targetNamespace = TNS, serviceName = SN, portName = PN)
public class SimpleWSS4JImpl implements SimpleWSS4J {

	public static final String	SN			= "SimpleWSS4JImpl";
	public static final String	PN			= SN + "Port";

	public static final QName		SERVICE	= new QName(TNS, SN);
	public static final QName		PORT		= new QName(TNS, PN);
	
	public static final String	PREFIX	= "wss4j-";

	/**
	 * @param msg
	 * @return {@value #PREFIX} + msg
	 */
	@WebMethod
	public String echo(String msg) {
		return PREFIX + msg;
	}

}
