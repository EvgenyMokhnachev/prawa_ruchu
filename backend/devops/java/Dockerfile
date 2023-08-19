# DEPS CACHE
FROM maven:3.9.3-eclipse-temurin-20-alpine as DEPS

WORKDIR /project

COPY ./pom.xml ./pom.xml

RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline




# BUILDER
FROM maven:3.9.3-eclipse-temurin-20-alpine as BUILDER

WORKDIR /project

COPY --from=DEPS /root/.m2 /root/.m2
COPY --from=DEPS /project/ /project

COPY ./src ./src

# build all dependencies
RUN mvn -T 2C clean install -Dmaven.javadoc.skip=true -Dmaven.test.skip=true






# RUNNER
# At this point, BUILDER stage should have your .jar or whatever in some path
FROM openjdk:20-ea-oracle
WORKDIR /project
COPY --from=BUILDER /project/target/prawaruchu-0.0.1-SNAPSHOT.jar ./boot-0.0.1-SNAPSHOT.jar
