#!/bin/bash

# copy deployable into folder
cp /home/psteiner/target/smart_gateway.war /home/psteiner/jboss/jboss-eap-6.4/standalone/deployments

# start JBoss EAP
/home/psteiner/jboss/jboss-eap-6.4/bin/standalone.sh -c=standalone-full.xml 
