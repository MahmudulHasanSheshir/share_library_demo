# Use a base image with Java 11
FROM openjdk:11-jdk

# Copy the artifact to the container
COPY /target/spark-lms-0.0.1-SNAPSHOT.jar /app/

# Set the working directory
WORKDIR /app

# Expose the port that the embedded Tomcat server is listening on (change it if necessary)
EXPOSE 8080

# Start the Java application with the embedded Tomcat server
CMD ["java", "-jar", "spark-lms-0.0.1-SNAPSHOT.jar"]
