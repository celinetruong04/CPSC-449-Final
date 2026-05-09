# stage 1 - build the JAR using Maven
FROM eclipse-temurin:21 AS build
WORKDIR /app

COPY pom.xml .
COPY .mvn ./.mvn
COPY mvnw .
COPY src ./src

RUN ./mvnw clean package -DskipTests

# stage 2 - run the JAR using JRE only
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]