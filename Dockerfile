FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY target/ms-vindecode.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]