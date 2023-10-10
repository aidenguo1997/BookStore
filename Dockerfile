FROM openjdk:11
COPY target/demo-0.0.1-SNAPSHOT.jar ../app.jar
ENV PORT 8084
EXPOSE $PORT
ENTRYPOINT ["java","-jar","/app.jar"]
