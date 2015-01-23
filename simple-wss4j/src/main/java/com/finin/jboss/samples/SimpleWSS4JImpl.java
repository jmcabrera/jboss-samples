/**
 * 
 */
package com.finin.jboss.samples;

import static com.finin.jboss.samples.SimpleWSS4J.PN;
import static com.finin.jboss.samples.SimpleWSS4J.SN;
import static com.finin.jboss.samples.SimpleWSS4J.TNS;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author a208220 - Juan Manuel CABRERA
 *
 */
@WebService(targetNamespace = TNS, serviceName = SN, portName = PN)
//@EndpointConfig(configName = "com.finin.jboss.samples.SimpleWSS4JImpl")
//@EndpointConfig(configFile = "WEB-INF/jaxws-endpoint-config.xml", configName = "aaa")
// @EndpointProperties(//
// @EndpointProperty(key = "ws-security.callback-handler", value =
// "com.finin.jboss.samples.PasswordCallback") //
// )
public class SimpleWSS4JImpl implements SimpleWSS4J {

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
