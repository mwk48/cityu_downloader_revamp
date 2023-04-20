FROM maven:3.8-openjdk-17 as maven
WORKDIR /app

COPY pom.xml .
COPY ./src ./src
RUN mvn package -DskipTests=true
WORKDIR /app/target/dependency
RUN jar -xf ../downloader.jar

From openjdk:17.0.1
COPY --from=maven /app/target/dependency/BOOT-INF/lib /app/lib
COPY --from=maven /app/target/dependency/META-INF /app/META-INF
COPY --from=maven /app/target/dependency/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*", "com.example.downloader.DownloaderApplication"]
