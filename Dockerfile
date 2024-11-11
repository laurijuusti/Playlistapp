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

# Expose the port that your app will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "finalproject.jar"]
