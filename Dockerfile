# Use an official Maven image to build the application
FROM maven:3.8.1-openjdk-11 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download the dependencies to cache them
COPY pom.xml .

# Download the project dependencies (this will cache dependencies if they haven't changed)
RUN mvn dependency:go-offline

# Copy the rest of the application files
COPY src /app/src

# Package the application into a JAR file
RUN mvn clean package -DskipTests

# Use OpenJDK 11 as the runtime base image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the generated JAR from the builder stage
COPY --from=builder /app/target/finalproject-0.0.1-SNAPSHOT.jar /app/finalproject.jar

# Optionally, copy the application.properties if it's externalized in the resources
# You can include the application.properties directly in the Docker image.
# If the file is in your resources folder, it should already be included in your JAR,
# but if you have a custom path or want to externalize it, use the following line:
# COPY src/main/resources/application.properties /app/application.properties

# Expose the port that your app will run on
EXPOSE 10000

# Set Spring Boot application properties via command line to bind to 0.0.0.0 and port 10000
ENTRYPOINT ["java", "-Dserver.address=0.0.0.0", "-Dserver.port=10000", "-jar", "finalproject.jar"]
