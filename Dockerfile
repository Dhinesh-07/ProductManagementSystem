
# Base image
FROM openjdk:17-alpine

WORKDIR /app

COPY Application/target/Application-0.0.1-SNAPSHOT.war /app/Application.war

COPY Application/target/classes/application-prod.properties /app/application-prod.properties
COPY Application/target/classes/application-dev.properties /app/application-dev.properties


ENV AWS_ACCESS_KEY_ID=AKIAY4Q5XY6MPRJP2JSF
ENV AWS_SECRET_ACCESS_KEY=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
ENV ACTIVATED_PROPERTIES=prod
EXPOSE 8080

#CMD ["java", "-jar", "Application.war"]
CMD ["sh", "-c", "if [ \"$ACTIVATED_PROPERTIES\" = \"dev\" ]; then java -jar -Dspring.profiles.active=dev Application.war; else java -jar -Dspring.profiles.active=prod Application.war; fi"]