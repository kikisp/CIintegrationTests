FROM gradle:6.1.1-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean assemble --no-daemon 


FROM adoptopenjdk/openjdk11:alpine
LABEL maintainer="kikisp1@gmail.com"
RUN apk --update --no-cache add curl
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar
WORKDIR /app
ARG profile=prod
ENV profile_env ${profile}
HEALTHCHECK CMD curl --fail https://iwashbackend.icebergarts.com:8443/actuator/health || exit 1
CMD ["java","-Dspring.profiles.active=${profile_env}","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]