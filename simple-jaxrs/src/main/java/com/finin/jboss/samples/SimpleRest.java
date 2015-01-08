/**
 * 
 */
package com.finin.jboss.samples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @author a208220 - Juan Manuel CABRERA
 *
 */
@Path(SimpleRest.PATH)
public class SimpleRest {

	public static final String	PATH		= "/rest";

	public static final String	PREFIX	= "rest-";

	/**
	 * @param msg
	 * @return {@value #PREFIX} + msg
	 */
	@GET
	@Path("{msg}")
	public String echo(@PathParam("msg") String msg) {
		return PREFIX + msg;
	}

}
