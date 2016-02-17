#!/bin/bash
#
# Little helper for the installation of Red Hat JBoss Fuse in a Docker container
#

echo TARGET_AMQ_BROKET

echo "Starting rules_cep"
java -jar $HOME/target/$APPL
