FROM maven:3-openjdk-17-slim as BUILDER
ARG VERSION=0.0.1-SNAPSHOT
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src


RUN mvn clean package
COPY target/habsat_backend-${VERSION}.jar target/application.jar

FROM eclipse-temurin:17.0.1_12-jre-focal
WORKDIR /app/

COPY --from=BUILDER /build/target/application.jar /app/
CMD java -jar /app/application.jar