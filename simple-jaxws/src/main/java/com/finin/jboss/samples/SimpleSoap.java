/**
 * 
 */
package com.finin.jboss.samples;

import javax.jws.WebService;

/**
 * @author a208220 - Juan Manuel CABRERA
 *
 */
@WebService
public interface SimpleSoap {

	String echo(String msg);

}
