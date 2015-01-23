<link href="https://raw.githubusercontent.com/mrcoles/markdown-css/master/markdown.css" rel="stylesheet"></link>


#JBOSS SIMPLE SAMPLES
Yet another bunch of samples on how to use JBoss in DEV.
The main focus is to:

 -	come up with the skinniest possible samples to highlight one purpose.
 -	use Arquillian to spin the simplest possible test
 -	When it makes sense, use JBoss AS Maven plugin to start the deployment and toy around with it

I'm looking forward reading your comments, especially:

 -	if you have a non-hacky (I mean not too deeply tied to JBoss) way to make one of the samples skinnier
 -	if you find a missing sample
	
Any feedback will be appreciated :)

[@slowcoding](https://twitter.com/slowcoding)

## SETUP
 -	An environment variable named `JBOSS_HOME` pointing to a [JBoss EAP 6.3 install](http://jbossas.jboss.org/downloads) (requires an account), e.g `JBOSS_HOME=/opt/local/jboss_eap_630`
 -	Maven >= 3
 -	Java >= 7

## Running all in remote
 -	Start an instance of JBoss (for instance with `$JBoss_HOME/bin/standalone.sh`
 -	`mvn clean test -Premote` in project `aggregate`

## Running all in managed
Contrary to remote, this recipe does not need a running instance of JBoss.

Just `mvn clean test -Pmanaged` in project `aggregate`.

## Running just one
You can run each and every module of `aggregate` separately.

Just cd to the module you like, e.g. `simple-cdi` and run

	me@domain:~/gh/jboss-samples/simple-cdi
	$ mvn clean test -Premote
or

	me@domain:~/gh/jboss-samples/simple-cdi
	$ mvn clean test -Pmanaged


You can also deploy the project to a running JBoss instance with:

	me@domain:~/gh/jboss-samples/simple-cdi
	$ mvn jboss-as:deploy

Finally, you can start a JBoss instance with the project deployed with:

	me@domain:~/gh/jboss-samples/simple-cdi
	$ mvn jboss-as:run
 