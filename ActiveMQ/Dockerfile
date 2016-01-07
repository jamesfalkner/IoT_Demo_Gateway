FROM    psteiner/base

MAINTAINER psteiner <psteiner@redhat.com>

# Make sure required environments are set
ENV AMQ_HOME=/home/psteiner/jboss/jboss-a-mq-6.2.1.redhat-084
ENV ACTIVEMQ_HOME=/home/psteiner/apache-activemq-5.11.0.redhat-621084

# Add Installation files
ADD Docker_Files/software/* /tmp/

USER root
RUN chown -R psteiner /tmp/*.zip
USER psteiner

# Install JBoss A-MQ
RUN unzip /tmp/jboss-a-mq-6.2.1.redhat-084.zip -d /home/psteiner/jboss  && \
    unzip ${AMQ_HOME}/extras/apache-activemq-5.11.0.redhat-621084-bin.zip -d /home/psteiner && \
    echo "admin=change12_me" >> ${ACTIVEMQ_HOME}/conf/users.properties

EXPOSE 61616 5672 61613 1884 61614

ENTRYPOINT ["/home/psteiner/apache-activemq-5.11.0.redhat-621084/bin/activemq","console"]
CMD []


# Build command -> docker build --rm -t psteiner/activemq .
# run command -> docker run -i -t psteiner/activemq /bin/bash
