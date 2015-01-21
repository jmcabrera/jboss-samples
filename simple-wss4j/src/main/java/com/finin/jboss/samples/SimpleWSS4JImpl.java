/**
 * 
 */
package com.finin.jboss.samples;

import static com.finin.jboss.samples.SimpleWSS4J.PN;
import static com.finin.jboss.samples.SimpleWSS4J.SN;
import static com.finin.jboss.samples.SimpleWSS4J.TNS;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.cxf.annotations.EndpointProperties;
import org.apache.cxf.annotations.EndpointProperty;

/**
 * @author a208220 - Juan Manuel CABRERA
 *
 */
@WebService(targetNamespace = TNS, serviceName = SN, portName = PN)
// @EndpointConfig(configFile = "WEB-INF/jaxws-endpoint-config.xml", configName
// = "Custom WS-Security Endpoint")
@EndpointProperties(value = {
		// @EndpointProperty(key = "ws-security.signature.properties", value =
		// "bob.properties"),
		// @EndpointProperty(key = "ws-security.encryption.properties", value =
		// "bob.properties"),
		// @EndpointProperty(key = "ws-security.signature.username", value = "bob"),
		// @EndpointProperty(key = "ws-security.encryption.username", value =
		// "alice"),
		// @EndpointProperty(key = "ws-security.callback-handler", value =
		// "org.jboss.test.ws.jaxws.samples.wsse.policy.basic.KeystorePasswordCallback")
		@EndpointProperty(key = "ws-security.username", value = "bob"),
		@EndpointProperty(key = "ws-security.callback-handler", value = "com.finin.jboss.samples.PasswordCallback") })
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
