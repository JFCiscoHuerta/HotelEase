FROM openjdk:21-jdk as builder
WORKDIR /app/Reservation
COPY ./pom.xml /app
COPY ./Reservation/.mvn ./.mvn
COPY ./Reservation/mvnw .
COPY ./Reservation/pom.xml .
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY ./Reservation/src ./src
RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk
WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/Reservation/target/Reservation-1.0-SNAPSHOT.jar .
ARG PORT_APP=8082
ENV PORT $PORT_APP
EXPOSE $PORT
ENTRYPOINT ["java","-jar","./Reservation-1.0-SNAPSHOT.jar"]