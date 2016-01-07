FROM    psteiner/base

MAINTAINER psteiner <psteiner@redhat.com>

# Make sure required environments are set
ENV AMQ_HOME=/home/psteiner/jboss/jboss-a-mq-6.2.1.redhat-084

# Add Installation files
ADD Docker_Files/software/* /tmp/

USER root
RUN chown -R psteiner /tmp/*.zip
USER psteiner

# Install JBoss A-MQ
RUN unzip /tmp/jboss-a-mq-6.2.1.redhat-084.zip -d /home/psteiner/jboss  && \
    echo "admin=change12_me,admin,manager,viewer,Operator, Maintainer, Deployer, Auditor, Administrator, SuperUser" >> ${AMQ_HOME}/etc/users.properties

EXPOSE 8181

ENTRYPOINT ["/home/psteiner/jboss/jboss-a-mq-6.2.1.redhat-084/bin/standalone"]
CMD []


# Build command -> docker build --rm -t psteiner/amq .
# run command -> docker run -i -t psteiner/amq /bin/bash
