FROM openjdk:8-jdk-alpine
ADD target/TransactionService-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]