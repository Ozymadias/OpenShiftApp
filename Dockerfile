FROM openjdk:9.0.4-jdk
ADD target/OpenShiftApp-1.0-SNAPSHOT.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","app.jar"]

