package com.finin.jboss.samples;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SimpleEjbTest {

	@Deployment(testable = true)
	public static Archive<?> createDeployment() {
		JavaArchive ar = ShrinkWrap.create(JavaArchive.class) //
				.addAsManifestResource(new StringAsset("<ejb-jar />"), "ejb-jar.xml") //
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml") //
				.addClass(SimpleEjb.class) //
		;
		System.out.println("~~~ v Web Archive Content v ~~~");
		ar.writeTo(System.out, Formatters.VERBOSE);
		System.out.println();
		System.out.println("~~~ ^ Web Archive Content ^ ~~~");
		return ar;
	}

	@Inject
	private SimpleEjb	ejb;

	@Test
	public void testEjb() {
		String hello = "Hello!";
		Assert.assertEquals("ejb-" + hello, ejb.echo(hello));
	}

	@Test
	public void testEjbBVal() {
		Assert.assertEquals("ejb-null", ejb.echo(null));
	}
}
