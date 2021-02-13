FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
#EXPOSE 8080 8081
#ENV _JAVA_OPTIONS '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]