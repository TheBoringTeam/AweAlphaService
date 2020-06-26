FROM adoptopenjdk/openjdk11:alpine
MAINTAINER holkerdev
VOLUME /tmp
EXPOSE 8083
ADD target/alpha-0.0.1.jar alpha-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/alpha-service.jar"]