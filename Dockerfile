FROM openjdk:1.8.0_73
ADD target/ChatApplicationSpringBoot-0.0.1-SNAPSHOT.jar ChatApplicationSpringBoot-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ChatApplicationSpringBoot-0.0.1-SNAPSHOT.jar"]