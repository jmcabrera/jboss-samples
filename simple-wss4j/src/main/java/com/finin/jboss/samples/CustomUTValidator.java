/*
 ****************************************************
 * Worldline -- Software Development Community Office
 *
 * Worldline is an Atos company
 * 
 * All rights reserved
 *****************************************************
 */
package com.finin.jboss.samples;

import org.apache.cxf.common.security.UsernameToken;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.handler.RequestData;
import org.apache.ws.security.validate.UsernameTokenValidator;

public class CustomUTValidator extends UsernameTokenValidator {

	protected void verifyPlaintextPassword(final UsernameToken usernameToken, RequestData data) throws WSSecurityException {
		// Should do something with the token.
		System.out.println(usernameToken.getName());
	}

}
