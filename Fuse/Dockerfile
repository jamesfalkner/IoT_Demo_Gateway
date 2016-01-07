FROM    psteiner/base

MAINTAINER psteiner <psteiner@redhat.com>

ENV FUSE_INSTALL_IMAGE jboss-fuse-full-6.2.1.redhat-084.zip
ENV FUSE_INSTALL_DIR jboss-fuse-6.2.1.redhat-084
ENV FUSE_LOCATION fuse

USER root

WORKDIR $HOME

# copy fuse distribution
ADD Docker_Files/software/$FUSE_INSTALL_IMAGE $HOME/tmp/

# copy required ( or not ) configurations
ADD Docker_Files/config/activemq.xml $HOME/tmp/

# copy helper_install script
ADD Docker_Files/scripts/install.sh $HOME/tmp/
ADD Docker_Files/scripts/run.sh $HOME/

# let the user 'psteiner' own the files
RUN chown -R psteiner:psteiner $HOME

# continue as 'psteiner'
USER psteiner

RUN chmod +x $HOME/tmp/install.sh
RUN chmod +x $HOME/run.sh

# extract
RUN unzip $HOME/tmp/$FUSE_INSTALL_IMAGE
RUN mv $HOME/$FUSE_INSTALL_DIR $HOME/$FUSE_LOCATION

# remove some overhead
RUN rm -rf $HOME/tmp/$FUSE_INSTALL_IMAGE
RUN rm -rf $HOME/$FUSE_LOCATION/extras
RUN rm -rf $HOME/$FUSE_LOCATION/quickstarts

# run the helper_install script
# RUN $HOME/tmp/install.sh

# remove dummy projects again
RUN rm -rf $HOME/tmp/src && \
    rm -rf $HOME/tmp/pom.xml

# move configuration(s) to their right place
RUN mv $HOME/tmp/activemq.xml $HOME/$FUSE_LOCATION/etc

# creates user
RUN sed -i "s/#admin/admin/" $HOME/$FUSE_LOCATION/etc/users.properties
RUN sed -i "s/#activemq.jmx.user/activemq.jmx.user/" $HOME/$FUSE_LOCATION/etc/system.properties
RUN sed -i "s/#activemq.jmx.password/activemq.jmx.password/" $HOME/$FUSE_LOCATION/etc/system.properties

# open ports
EXPOSE 22 8101 8181 44444 1099 61616 80 8080

# ENTRYPOINT $HOME/$FUSE_LOCATION/bin/fuse
CMD $HOME/run.sh

# Build command -> docker build --rm -t psteiner/fuse .
# run command -> docker run -d -p 10000:8181 psteiner/fuse
# run and connect -> docker run -p 10000:8181 -i -t psteiner/fuse /bin/bash
