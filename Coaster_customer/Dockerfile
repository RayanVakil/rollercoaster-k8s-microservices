# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Build the WAR file, skipping tests to speed up the build
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM tomcat:10.1-jdk21-temurin
WORKDIR $CATALINA_HOME

# Copy the built WAR from the builder stage
COPY --from=builder /app/target/*.war ./webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]