# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /patient_assess

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN dos2unix mvnw

RUN ./mvnw dependency:resolve

#RUN sed -i 's/\r$//' mvnw
## run with the SH path
#RUN /bin/sh mvnw dependency:resolve
#

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]


