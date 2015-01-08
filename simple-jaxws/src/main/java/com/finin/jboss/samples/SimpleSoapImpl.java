/**
 * 
 */
package com.finin.jboss.samples;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author a208220 - Juan Manuel CABRERA
 *
 */
@WebService(targetNamespace = SimpleSoapImpl.TNS, serviceName = SimpleSoapImpl.SN)
public class SimpleSoapImpl implements SimpleSoap {

	public static final String	TNS			= "http://test";
	public static final String	SN			= "SimpleSoapImpl";
	public static final String	PREFIX	= "soap-";

	/**
	 * @param msg
	 * @return {@value #PREFIX} + msg
	 */
	@WebMethod
	public String echo(String msg) {
		return PREFIX + msg;
	}

}
