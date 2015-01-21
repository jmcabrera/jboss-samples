package com.finin.jboss.samples;

import static com.finin.jboss.samples.SimpleWSS4J.PORT;
import static com.finin.jboss.samples.SimpleWSS4J.SERVICE;
import static com.finin.jboss.samples.SimpleWSS4J.SN;
import static com.finin.jboss.samples.SimpleWSS4JImpl.PREFIX;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JOutInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SimpleWSS4JTest {

	@Deployment(testable = false)
	public static Archive<?> createDeployment() {
		WebArchive ar = ShrinkWrap.create(WebArchive.class, "web-test.war") //
				.addClass(SimpleWSS4J.class) //
				.addClass(SimpleWSS4JImpl.class) //
				// .addClass(PasswordCallback.class) //
				.addAsResource("wsdl/test.xml") //
				// Needed for JBoss to add WSS4J as a dependency of this deployment
				// .setManifest(new
				// StringAsset("Dependencies: org.apache.ws.security,org.apache.cxf,org.apache.cxf.impl"))
				// //
				.setManifest(new StringAsset("Dependencies: org.apache.ws.security,org.apache.cxf")) //
		;
		System.out.println("~~~ v Web Archive Content v ~~~");
		ar.writeTo(System.out, Formatters.VERBOSE);
		System.out.println();
		System.out.println("~~~ ^ Web Archive Content ^ ~~~");
		return ar;
	}

	@Test
	@RunAsClient
	public void mainTest(@ArquillianResource URL baseURL) throws MalformedURLException {
		try {
			String hello = "Hello!";
			URL wsdlURL = new URL(baseURL + SN + "?wsdl");
			Service service = Service.create(wsdlURL, SERVICE);
			SimpleWSS4J client = service.getPort(PORT, SimpleWSS4J.class);

			Client proxy = ClientProxy.getClient(client);
			proxy.getOutInterceptors().add(new LoggingOutInterceptor());
			proxy.getInInterceptors().add(new LoggingInInterceptor());
			proxy.getOutInterceptors().add(new PolicyBasedWSS4JOutInterceptor());

			((BindingProvider) client).getRequestContext().put("ws-security.username", "kermit");
			((BindingProvider) client).getRequestContext().put("ws-security.password", "frogg");

			Assert.assertEquals(PREFIX + hello, client.echo(hello));
		}
		catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}
}
