FROM    psteiner/base

MAINTAINER psteiner <psteiner@redhat.com>

# Make sure required environments are set
ENV EAP_HOME /home/psteiner/jboss/jboss-eap-6.4

# Add Installation files
ADD Docker_Files/software/* /tmp/
ADD Docker_Files/scripts/start.sh /home/psteiner/

USER root
RUN chown -R psteiner /tmp/*.jar  && \
    chown -R psteiner /tmp/*.zip && \
    chown -R psteiner /home/psteiner/start.sh && \
    chmod u+x /home/psteiner/start.sh 

# Install JBoss EAP
RUN unzip /tmp/jboss-eap-6.4.0.zip -d /home/psteiner/jboss  && \
    ${EAP_HOME}/bin/add-user.sh psteiner change12_me --silent   && \
    echo "JAVA_OPTS=\"\$JAVA_OPTS -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0 -Xmx512m -Xms512m\"" >> ${EAP_HOME}/bin/standalone.conf && \
    sed -i "s/-Xms1303m/-Xms512m/" $EAP_HOME/bin/standalone.conf && \
    sed -i "s/-Xmx1303m/-Xmx512m/" $EAP_HOME/bin/standalone.conf 

# Install JBoss Fuse
WORKDIR ${EAP_HOME}
RUN java -jar /tmp/fuse-eap-installer-6.2.1.redhat-084.jar
WORKDIR ${HOME}

# Cleanup
RUN rm /tmp/*.zip && \
    rm /tmp/*.jar

EXPOSE 9990 9999 8080 8787

ENTRYPOINT ["/home/psteiner/start.sh"]
CMD []


# Build command -> docker build --rm -t psteiner/fuseeap .
# run command -> docker run -i -t psteiner/fuseeap /bin/bash
