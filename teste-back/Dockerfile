FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn clean install -DskipTests


FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/back-0.0.1-SNAPSHOT.jar ./back.jar
EXPOSE 8080
CMD ["java", "-jar", "back.jar"]
