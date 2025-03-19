#build the client
FROM node:23 AS buildang

WORKDIR /src

COPY ecommerce/client/*.json .
COPY ecommerce/client/src src

#Run npm to install node_modules -> package.json
RUN npm ci
RUN npm i -g @angular/cli
# produce dist/client/browser
RUN ng build

# build the SpringBoot application
FROM eclipse-temurin:23-jdk AS buildjava

WORKDIR /src

COPY ecommerce/mvnw .
COPY ecommerce/pom.xml .
COPY ecommerce/src src
COPY ecommerce/.mvn .mvn

# copy the angular app over to static directory
COPY --from=buildang /src/dist/client-side/browser/* src/main/resources/static

# make mvnw executable
RUN chmod a+x mvnw

# produce target.server-0.0.1-SNAPSHOT.jar
RUN ./mvnw package -Dmaven.test.skip=true

# Deployment container
FROM eclipse-temurin:23-jre

WORKDIR /app

COPY --from=buildjava /src/target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# set environment variable
ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar