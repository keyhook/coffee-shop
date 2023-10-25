FROM amazoncorretto:17

EXPOSE 8080

ADD ./build/libs/coffee-shop-0.0.1.jar app.jar

RUN sh -c 'touch /app.jar'

ENTRYPOINT [ "java", "-jar", "/app.jar" ]
