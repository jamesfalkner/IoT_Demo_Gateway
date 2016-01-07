#!/bin/bash
#
# Little helper for the installation of Red Hat JBoss Fuse in a Docker container
#

# make sure Fuse listens to the right IP
echo '
bind.address=0.0.0.0
'>> $HOME/$FUSE_LOCATION/etc/system.properties
echo '
admin=admin,admin' >> $HOME/$FUSE_LOCATION/etc/users.properties


