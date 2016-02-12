#!/bin/bash

# Start Fuse
$HOME/$FUSE_LOCATION/bin/fuse server

# wait a short while
# sleep 30

# have to run something, or docker will end the container
# as soon as Fuse is done with starting
# tail -f $HOME/$FUSE_LOCATION/data/log/fuse.log
