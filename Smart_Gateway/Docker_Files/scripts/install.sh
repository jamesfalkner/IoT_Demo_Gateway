#!/bin/bash
#
# Little helper for the installation of Red Hat JBoss Fuse in a Docker container
#

# echo "Building deployables"
# mvn clean install

echo "Start Fuse and wait for start procedure to end"
$HOME/$FUSE_LOCATION/bin/start

$HOME/$FUSE_LOCATION/bin/status
while [ "$?" != "0" ]
do
   echo "."
   sleep 10
   $HOME/$FUSE_LOCATION/bin/status
done

echo "waiting for 5 minutes, until I found a more clever way to check if Fuse is running"

sleep 300

echo "Now let's deploy some prereq bundles"
echo "commons-dbcp"
$HOME/$FUSE_LOCATION/bin/client -h 127.0.0.1 "osgi:install -s wrap:mvn:commons-dbcp/commons-dbcp/1.4"
echo "camel-mqtt"
$HOME/$FUSE_LOCATION/bin/client -h 127.0.0.1 "features:install camel-mqtt"
# echo "Our code"
# $HOME/$FUSE_LOCATION/bin/client -h 127.0.0.1 "osgi:install -s file://$HOME/tmp/$BUNDLE_NAME"

