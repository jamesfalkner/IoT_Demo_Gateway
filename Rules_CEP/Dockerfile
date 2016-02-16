FROM    psteiner/base

MAINTAINER psteiner <psteiner@redhat.com>

USER root

ENV APPL iot_datacenter_monitor-jar-with-dependencies.jar

# Transfer application
ADD target/$APPL $HOME/tmp/

# transfer install-script
ADD Docker_Files/scripts/run.sh $HOME/tmp/

# make 'psteiner' the owner
RUN chown -R psteiner:psteiner $HOME/tmp && \
    chmod u+x $HOME/tmp/run.sh

# switch to psteiner
USER psteiner

WORKDIR $HOME/tmp

CMD $HOME/tmp/run.sh



# Build command -> docker build --rm -t workspace_iotdatacentermonitor .
# run command -> docker run -h iotcontrollerrouter -d workspacefuse_iotdatacentermonitor
# run and connect -> docker run -P -i -t -h iotdatacentermonitor workspacefuse_iotdatacentermonitor /bin/bash
