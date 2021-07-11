FROM openjdk:8-jdk-alpine
RUN apk add --no-cache maven
COPY . .
RUN ["mvn", "clean", "package", "-DskipTests"]
COPY target/cafe-manager-1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]