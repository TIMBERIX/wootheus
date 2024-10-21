# for build
FROM gradle:8.10.1-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon

# release
FROM eclipse-temurin:17-alpine
EXPOSE 8081
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/* /app/wootheus.jar
ENTRYPOINT ["java","-jar","/app/wootheus.jar"]