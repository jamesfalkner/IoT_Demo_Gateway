#!/bin/sh

echo "copy deployable to EAP deployment folder"

cp /home/psteiner/deployable/smart_gateway.war /home/psteiner/jboss/jboss-eap-6.4/standalone/deployments

/home/psteiner/jboss/jboss-eap-6.4/bin/standalone.sh -c standalone-full.xml
