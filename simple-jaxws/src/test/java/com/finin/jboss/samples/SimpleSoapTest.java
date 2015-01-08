package com.finin.jboss.samples;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SimpleSoapTest {

	@Deployment(testable = false)
	public static Archive<?> createDeployment() {
		WebArchive ar = ShrinkWrap.create(WebArchive.class) //
				.addClass(SimpleSoap.class) //
				.addClass(SimpleSoapImpl.class) //
		;
		System.out.println("~~~ v Web Archive Content v ~~~");
		ar.writeTo(System.out, Formatters.VERBOSE);
		System.out.println();
		System.out.println("~~~ ^ Web Archive Content ^ ~~~");
		return ar;
	}

	@Test
	public void mainTest(@ArquillianResource URL baseURL) throws MalformedURLException {
		String hello = "Hello!";
		URL wsdlURL = new URL(baseURL + SimpleSoapImpl.SN + "?wsdl");
		QName SERVICE_NAME = new QName(SimpleSoapImpl.TNS, SimpleSoapImpl.SN);
		Service service = Service.create(wsdlURL, SERVICE_NAME);
		SimpleSoap client = service.getPort(SimpleSoap.class);
		Assert.assertEquals(SimpleSoapImpl.PREFIX + hello, client.echo(hello));
	}
}
