/**
 * 
 */
package com.finin.jboss.samples;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

/**
 * @author a208220 - Juan Manuel CABRERA
 *
 */
public class PasswordCallback implements CallbackHandler {
	private Map<String, String>	passwords	= new HashMap<String, String>();

	public PasswordCallback() {
		passwords.put("alice", "alicepassword");
		passwords.put("bob", "bobpassword");
		passwords.put("kermitt", "frogg");
	}

	/**
	 * It attempts to get the password from the private
	 * alias/passwords map.
	 */
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
			String pass = passwords.get(pc.getIdentifier());
			if (pass != null) {
				pc.setPassword(pass);
				return;
			}
		}
	}

	/**
	 * Add an alias/password pair to the callback mechanism.
	 */
	public void setAliasPassword(String alias, String password) {
		passwords.put(alias, password);
	}
}
