#!/bin/bash

echo "Changing ownership of mounted volume"
chown -R psteiner:psteiner /home/psteiner/target

# copy deployable into folder
echo "Copying deployment package"

cp /home/psteiner/target/smart_gateway.war /home/psteiner/jboss/jboss-eap-6.4/standalone/deployments

if [ "$?" = "0" ]; then
  echo "Copy successful"	
else
  echo "Copy failed" 
  exit 1
fi

# start JBoss EAP
/home/psteiner/jboss/jboss-eap-6.4/bin/standalone.sh -c=standalone-full.xml 
