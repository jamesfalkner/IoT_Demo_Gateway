#!/bin/bash
#
# Little helper for the installation of Red Hat JBoss Fuse in a Docker container
#

echo "Starting rules server"
java -jar $HOME/target/$APPL
