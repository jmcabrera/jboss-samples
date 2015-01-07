/**
 * 
 */
package com.finin.jboss.samples;

/**
 * @author a208220 - Juan Manuel CABRERA
 *
 */
public class SimpleEjb {

	public static final String	PREFIX	= "ejb-";

	/**
	 * @param msg
	 * @return {@value #PREFIX} + msg
	 */
	public String echo(String msg) {
		return PREFIX + msg;
	}

}
