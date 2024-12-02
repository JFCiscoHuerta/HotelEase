FROM openjdk:21-jdk as builder
WORKDIR /app/Room
COPY ./pom.xml /app
COPY ./Room/.mvn ./.mvn
COPY ./Room/mvnw .
COPY ./Room/pom.xml .
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY ./Room/src ./src
RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk
WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/Room/target/Room-1.0-SNAPSHOT.jar .
ARG PORT_APP=8081
ENV PORT $PORT_APP
EXPOSE $PORT
ENTRYPOINT ["java","-jar","./Room-1.0-SNAPSHOT.jar"]