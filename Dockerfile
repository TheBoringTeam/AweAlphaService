FROM adoptopenjdk/openjdk11:alpine
MAINTAINER holkerdev
VOLUME /tmp
EXPOSE 8083
ADD target/alpha-0.0.1.jar alpha-service.jar
ENTRYPOINT ["java", "-jar","/alpha-service.jar"]