# Use a Maven image to build the app
FROM maven:3.8.4-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and install dependencies
COPY pom.xml .

# Download dependencies early for Docker cache
RUN mvn dependency:go-offline

# Copy source files and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Use OpenJDK runtime to run the JAR
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
