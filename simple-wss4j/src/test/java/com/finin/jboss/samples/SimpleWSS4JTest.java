package com.finin.jboss.samples;

import static com.finin.jboss.samples.SimpleWSS4JImpl.SERVICE;
import static com.finin.jboss.samples.SimpleWSS4JImpl.SN;
import static com.finin.jboss.samples.SimpleWSS4JImpl.PORT;
import static com.finin.jboss.samples.SimpleWSS4JImpl.PREFIX;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
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
		WebArchive ar = ShrinkWrap.create(WebArchive.class) //
				.addClass(SimpleWSS4J.class) //
				.addClass(SimpleWSS4JImpl.class) //
				.addClass(PasswordCallback.class) //
				.addAsWebInfResource("wsdl/test.xml", "wsdl/test.xml")//
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/jboss-webservices.xml"))//
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/endpoint-config.xml"))//
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
	public void okTest(@ArquillianResource URL baseURL) throws MalformedURLException {
		String hello = "Hello!";
		SimpleWSS4J client = getClient(baseURL, "kermitt", "frogg");
		Assert.assertEquals(PREFIX + hello, client.echo(hello));
	}

	@Test(expected = SOAPFaultException.class)
	@RunAsClient
	public void koTest(@ArquillianResource URL baseURL) throws MalformedURLException {
		SimpleWSS4J client = getClient(baseURL);
		Client unproxied = ClientProxy.getClient(client);
		Bus bus = unproxied.getBus();
		List<Interceptor<?>> out = new ArrayList<Interceptor<?>>();
		try {
			String hello = "Hello!";
			out.addAll(bus.getOutInterceptors());
			bus.getOutInterceptors().clear();
			client.echo(hello);
		}
		finally {
			bus.getOutInterceptors().addAll(out);
		}
	}

	@Test(expected = SOAPFaultException.class)
	@RunAsClient
	public void koTest2(@ArquillianResource URL baseURL) throws MalformedURLException {
		String hello = "Hello!";
		SimpleWSS4J client = getClient(baseURL, "kermitt", "nofrogg");
		client.echo(hello);
	}

	private final SimpleWSS4J getClient(URL baseURL, String username, String password) throws MalformedURLException {
		SimpleWSS4J client = getClient(baseURL);
		((BindingProvider) client).getRequestContext().put("ws-security.username", username);
		((BindingProvider) client).getRequestContext().put("ws-security.password", password);
		return client;
	}

	private final SimpleWSS4J getClient(URL baseURL) throws MalformedURLException {
		URL wsdlURL = new URL(baseURL + SN + "?wsdl");
		Service service = Service.create(wsdlURL, SERVICE);
		SimpleWSS4J client = service.getPort(PORT, SimpleWSS4J.class);
		Client proxy = ClientProxy.getClient(client);
		proxy.getOutInterceptors().add(new LoggingOutInterceptor());
		proxy.getInInterceptors().add(new LoggingInInterceptor());
		return client;
	}
}
