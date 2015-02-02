/**
 * 
 */
package com.finin.jboss.samples;

import static com.finin.jboss.samples.SimpleWSS4J.TNS;

import javax.jws.WebService;

/**
 * @author a208220 - Juan Manuel CABRERA
 *
 */
@WebService(wsdlLocation = "WEB-INF/wsdl/test.xml", targetNamespace = TNS)
public interface SimpleWSS4J {

	String	TNS	= "http://test";

	String echo(String msg);

}
