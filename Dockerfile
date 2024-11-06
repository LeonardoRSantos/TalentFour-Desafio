FROM maven:3.8.5-openjdk-17-slim as BUILD
ARG ACCESS_TOKEN
ENV ACCESS_TOKEN=${ACCESS_TOKEN}

COPY . /src
WORKDIR /src
COPY settings.xml .
RUN mvn -s settings.xml -DskipTests install  -Dmaven.repo.local=.m2/repository

FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY --from=BUILD /src/target/talentfour-service*.jar /app.jar

EXPOSE 8080
ENV TZ="America/Sao_Paulo"
ENTRYPOINT ["java", "-jar", "app.jar"]