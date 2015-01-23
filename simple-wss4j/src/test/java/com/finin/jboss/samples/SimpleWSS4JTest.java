package com.finin.jboss.samples;

import static com.finin.jboss.samples.SimpleWSS4J.PORT;
import static com.finin.jboss.samples.SimpleWSS4J.SERVICE;
import static com.finin.jboss.samples.SimpleWSS4J.SN;
import static com.finin.jboss.samples.SimpleWSS4JImpl.PREFIX;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
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
				.addClass(PasswordCallback.class) //
				// .addAsResource("wsdl/test.xml") //
				.addAsWebInfResource("wsdl/test.xml", "wsdl/test.xml")//
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/jboss-webservices.xml"))//
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/endpoint-config.xml"))
				// .addAsManifestResource(new
				// File("src/main/webapp/WEB-INF/endpoint-config.xml"))
				// .addAsResource(new
				// File("src/main/webapp/WEB-INF/endpoint-config.xml"))
				// .addAsResource(new
				// File("src/main/webapp/WEB-INF/endpoint-config.xml"),
				// "META-INF/endpoint-config.xml")
				// .add(new FileAsset(new
				// File("src/main/webapp/WEB-INF/endpoint-config.xml")),
				// "endpoint-config.xml") //
				// Needed for JBoss to add WSS4J as a dependency of this deployment
				.setManifest(new StringAsset("Dependencies: org.apache.ws.security,org.jboss.ws.api")) //
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

			((BindingProvider) client).getRequestContext().put("ws-security.username", "kermitt");
			((BindingProvider) client).getRequestContext().put("ws-security.password", "frogg");

			Assert.assertEquals(PREFIX + hello, client.echo(hello));
		}
		catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}

	@Test(expected = SOAPFaultException.class)
	@RunAsClient
	public void koTest(@ArquillianResource URL baseURL) throws MalformedURLException {
		String hello = "Hello!";
		URL wsdlURL = new URL(baseURL + SN + "?wsdl");
		Service service = Service.create(wsdlURL, SERVICE);
		SimpleWSS4J client = service.getPort(PORT, SimpleWSS4J.class);
		((BindingProvider) client).getRequestContext().put("ws-security.username", "kermitt");
		((BindingProvider) client).getRequestContext().put("ws-security.password", "nofrogg");
		client.echo(hello);
		Assert.fail();
	}

	@Test(expected = SOAPFaultException.class)
	@RunAsClient
	public void koTest2(@ArquillianResource URL baseURL) throws MalformedURLException {
		String hello = "Hello!";
		URL wsdlURL = new URL(baseURL + SN + "?wsdl");
		Service service = Service.create(wsdlURL, SERVICE);
		SimpleWSS4J client = service.getPort(PORT, SimpleWSS4J.class);
		client.echo(hello);
		Assert.fail();
	}
}
