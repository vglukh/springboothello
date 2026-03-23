# JDK 17 light-weighs OS Alpine
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# copy Maven settings
COPY .mvn ./.mvn
COPY mvnw .
COPY pom.xml .

# download and cache dependencies
# go-offline command downloads all jar-files without bild project
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# copy sources
COPY src ./src


# build .jar inside cuntainer
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# (Runtime)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# take jar
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]