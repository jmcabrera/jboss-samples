package com.finin.jboss.samples;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SimpleCdiBeanTest {

	@Deployment(testable = true)
	public static Archive<?> createDeployment() {
		JavaArchive ar = ShrinkWrap.create(JavaArchive.class) //
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml") //
				.addClass(SimpleCdiBean.class) //
		;
		System.out.println("~~~ v Web Archive Content v ~~~");
		ar.writeTo(System.out, Formatters.VERBOSE);
		System.out.println();
		System.out.println("~~~ ^ Web Archive Content ^ ~~~");
		return ar;
	}

	@Inject
	private SimpleCdiBean	cdi;

	@Test
	public void testEjb() {
		String hello = "Hello!";
		Assert.assertEquals(SimpleCdiBean.PREFIX + hello, cdi.echo(hello));
	}

}
