FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY Main.java Student.java StudentMarks.java ./

RUN javac Main.java Student.java StudentMarks.java

CMD ["java", "Main"]
