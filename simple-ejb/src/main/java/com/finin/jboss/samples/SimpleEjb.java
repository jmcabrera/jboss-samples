/**
 * 
 */
package com.finin.jboss.samples;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * @author a208220 - Juan Manuel CABRERA
 *
 */
@Stateless
@LocalBean
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
