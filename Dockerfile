FROM openjdk:8

SHELL ["/bin/bash", "-c"]

RUN apt-get update && \
    apt-get -y install zip && \
    curl -s https://get.sdkman.io | bash

RUN source ~/.sdkman/bin/sdkman-init.sh && \
    sdk install gradle 3.1 && \
    mkdir -p ~/.gradle && echo "org.gradle.daemon=false" >> ~/.gradle/gradle.properties && \
    curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py && \
    python get-pip.py && rm get-pip.py && \
    pip install jinja2-cli

COPY . /spinnaker-performace