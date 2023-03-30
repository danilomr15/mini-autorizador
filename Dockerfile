FROM openjdk:17-alpine
ADD /target/mini-autorizador.jar mini-autorizador.jar
ENTRYPOINT exec java -jar mini-autorizador.jar
