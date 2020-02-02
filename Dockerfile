FROM openjdk:8-jdk-alpine
LABEL maintainer="kikisp1@gmail.com"
VOLUME /tmp
ARG JAR_FILE=build/libs/CarWashAgent.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]