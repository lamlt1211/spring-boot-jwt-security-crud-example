FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD FOR JAVA 8

 

ARG SPRING_ACTIVE_PROFILE

 

MAINTAINER lamlt
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn clean install -Dspring.profiles.active=development && mvn package -B -e -Dspring.profiles.active=development
FROM openjdk:8-alpine FOR JAVA 8
WORKDIR /app

 

COPY --from=MAVEN_BUILD /build/target/spring-boot-codebase-*.jar /app/spring-boot-codebase.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=development", "-jar", "spring-boot-codebase.jar"]
 

FROM mysql:8
MAINTAINER lamlt

 

RUN /createDB.sql

 

RUN create database database_randt;

 

EXPOSE 3306