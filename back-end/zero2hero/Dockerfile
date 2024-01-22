FROM adoptopenjdk:11-jre-hotspot-focal@sha256:eac1c6cff5fded2dd35fc94bb23e7862a08277bd71f9b352a99df5bc740459c3
ARG JAR_FILE=build/libs/Spring-React-Demo-App-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} Spring-React-Demo-App.jar
ENTRYPOINT ["java","-jar","/Spring-React-Demo-App.jar"]
EXPOSE 8080
