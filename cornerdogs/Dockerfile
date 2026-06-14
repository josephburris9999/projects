# Copyright (c) 2024 Joseph Burris, all9s Solutions LLC
# SPDX-License-Identifier: BSD-3-Clause

FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B package

FROM tomcat:11-jdk17-temurin

ENV PORT=10000

RUN rm -rf "$CATALINA_HOME/webapps"/*
COPY --from=build /app/target/cornerdogs.war "$CATALINA_HOME/webapps/ROOT.war"

EXPOSE 10000

CMD ["sh", "-c", "sed -i \"s/port=\\\"8080\\\"/port=\\\"${PORT:-10000}\\\"/\" \"$CATALINA_HOME/conf/server.xml\" && exec catalina.sh run"]
