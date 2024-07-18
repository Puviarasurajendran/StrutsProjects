# Use the Tomcat 9.0 image with JDK 17 (Temurin)
FROM tomcat:9.0-jdk17-temurin

# Set the working directory inside the container
WORKDIR /usr/local/tomcat

# Update package list and install necessary tools
RUN apt-get update && apt-get install -y \
    iputils-ping \
    mysql-client

# Clean up
RUN apt-get clean

# Copy the packaged WAR file into the Tomcat webapps directory
COPY ZohoSuperMarketStruts.war /usr/local/tomcat/webapps/ZohoSuperMarketStruts.war

# Expose the port that Tomcat runs on
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]