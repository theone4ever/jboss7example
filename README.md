jboss7example
========================

What is it?
-----------

This is an example to show how to use infinispan as distribute cache.

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven
3.0 or better.

The application this project produces is designed to be run on an EAP 6,
but not jboss as 7 since latest version of jboss 7.1.1.Final still has a bug of classloader, when second jboss instance
in cluster unmarshall the object from inifinspan, class not found exception threw up.



Deploying the application
-------------------------
 
First you need to start two EAP 6 instance in HA mode, run
  
    $EAP_HOME/bin/standalone.sh -c standalone-ha.xml -Djboss.node.name=nodeA
    $EAP_HOME/bin/standalone.sh -c standalone-ha.xml -Djboss.socket.binding.port-offset=100 -Djboss.node.name=nodeB
  

Then build the package, run

    mvn clean install

Then deploy the package:

    mvn jboss-as:undeploy

Then try to access the example, instance 1: http://localhost:8080/jboss7example/home.xhtml,
instance 2: http://localhost:8180/jboss7example/home.xhtml. Play with it and see how cool infinispan is.


Tips to use infinispan
------------------------
1. There is no need to package inifinispan-core package in war or ear file, but depend on the infinispan-core.jar in
jboss modeules. But to use it, you need to add a depedency in manifest file of war or ear package, here is the tricky in
pom.xml:
            <plugin>
                   <artifactId>maven-war-plugin</artifactId>
                   <version>2.1.1</version>
                   <configuration>
                        <!-- Java EE 6 doesn't require web.xml, Maven needs to catch up! -->
                        <failOnMissingWebXml>false</failOnMissingWebXml>
                        <archive>
                               <manifestEntries>
                                     <Dependencies>org.infinispan export</Dependencies>
                               </manifestEntries>
                        </archive>
                   </configuration>
            </plugin>
2. Don't use jboss 7.1.1.Final, only EAP 6 works!!!

