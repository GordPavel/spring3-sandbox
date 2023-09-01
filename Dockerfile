FROM bellsoft/liberica-openjdk-alpine:17
ADD build/libs/application.jar application.jar
ENTRYPOINT [ "java", "-jar", "application.jar" ]