# Use an official Maven image with Java 21 to build the application
FROM maven:3.8.1-openjdk-17 as builder

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

# Use OpenJDK 21 as the runtime base image
FROM openjdk:17-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the generated JAR from the builder stage
COPY --from=builder /app/target/finalproject-0.0.1-SNAPSHOT.jar /app/finalproject.jar

# Expose the port Render expects
EXPOSE 10000

# Run the application, binding it to port 10000 and to 0.0.0.0
ENTRYPOINT ["java", "-jar", "/app/finalproject.jar", "--server.port=10000", "--server.address=0.0.0.0"]
