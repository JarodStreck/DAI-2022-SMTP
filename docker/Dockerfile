# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy
RUN mkdir /app
COPY MockMock-1.4.1-SelfCompiled.jar /app/MockMock.jar

CMD ["java","-jar" ,"/app/MockMock.jar"]
