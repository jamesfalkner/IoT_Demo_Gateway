FROM    centos:centos7

MAINTAINER psteiner <psteiner@redhat.com>

# Update the system and Install necessary RPMs
RUN yum -y install java-1.7.0-openjdk java-1.7.0-openjdk-devel unzip wget telnet

# Make sure required environments are set
ENV JAVA_HOME /usr/lib/jvm/java-openjdk/
ENV HOME /home/psteiner
ENV MAVEN_HOME /opt/maven

# Set root password
RUN echo "root:redhat" | /usr/sbin/chpasswd

# download and install maven
RUN wget http://ftp.fau.de/apache/maven/maven-3/3.2.5/binaries/apache-maven-3.2.5-bin.zip && \
    unzip apache-maven-3.2.5-bin.zip && \
    mv apache-maven-3.2.5/ /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn && \
    rm apache-maven-3.2.5-bin.zip


# Create user to run processed
RUN useradd -m -d /home/psteiner -p psteiner psteiner

# Switch to new user
USER psteiner

# create a few needed directories
RUN mkdir $HOME/tmp && \
    mkdir $HOME/.m2

# transfer maven configuration file
ADD config/settings.xml $HOME/.m2/

# as ADD always uses root as user, we have to give
# the file back to 'psteiner'
USER root
RUN chown -R psteiner:psteiner $HOME/.m2/*
USER psteiner

# lets create a volume to mount the external maven repo
VOLUME /maven_repo

# make $HOME/tmp the active directory
WORKDIR $HOME/tmp

# Build command -> docker build --rm -t psteiner/base .
# run command -> docker run -d psteiner/base
# run command ->  docker run -i -t -v /home/psteiner/.m2/repository/:/maven_repo  psteiner/base /bin/bash
