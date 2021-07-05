FROM openjdk:8

EXPOSE 8081

ADD target/teacher.core-0.0.1-SNAPSHOT.jar teacher.core-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/teacher.core-0.0.1-SNAPSHOT.jar"]