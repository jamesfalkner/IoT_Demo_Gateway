FROM    psteiner/base

MAINTAINER psteiner <psteiner@redhat.com>

# Make sure required environments are set
ENV EAP_HOME /home/psteiner/jboss/jboss-eap-6.4

# Add Installation files
ADD Docker_Files/software/* /tmp/

USER root
RUN chown -R psteiner /tmp/*.jar  && \
    chown -R psteiner /tmp/*.zip
USER psteiner

# Install JBoss EAP
RUN unzip /tmp/jboss-eap-6.4.0.zip -d /home/psteiner/jboss  && \
    ${EAP_HOME}/bin/add-user.sh psteiner change12_me --silent   && \
    echo "JAVA_OPTS=\"\$JAVA_OPTS -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0\"" >> ${EAP_HOME}/bin/standalone.conf
# Install JBoss Fuse
WORKDIR ${EAP_HOME}
RUN java -jar /tmp/fuse-eap-installer-6.2.1.redhat-084.jar
WORKDIR ${HOME}

EXPOSE 9990 9999 8080 8787

ENTRYPOINT ["/home/psteiner/jboss/jboss-eap-6.4/bin/standalone.sh", "-c=standalone-full.xml"]
CMD []


# Build command -> docker build --rm -t psteiner/fuseeap .
# run command -> docker run -i -t psteiner/fuseeap /bin/bash
