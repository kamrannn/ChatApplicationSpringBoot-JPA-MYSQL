FROM openjdk:8
COPY ./target/ChatApplicationSpringBoot-0.0.1-SNAPSHOT.jar ChatApplicationSpringBoot-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java","-jar","ChatApplicationSpringBoot-0.0.1-SNAPSHOT.jar"]