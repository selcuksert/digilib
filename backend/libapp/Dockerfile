FROM maven:3-eclipse-temurin-21-alpine AS build
LABEL author="Selcuk SERT"
LABEL mail="selcuk.sert@gmail.com"
LABEL version="1.0"
LABEL description="Builder image for diglib web app"

WORKDIR /workspace/app

COPY . .

RUN mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:21-alpine AS runner
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency

ENV MAIN_CLASS="com.corp.libapp.DigilibApplication"

RUN addgroup --system --gid 1001 appgrp
RUN adduser --system --uid 1001 appuser -G appgrp
RUN apk --no-cache add curl jq

COPY --from=build --chown=appuser:appgrp ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build --chown=appuser:appgrp ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build --chown=appuser:appgrp ${DEPENDENCY}/BOOT-INF/classes /app

USER appuser

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -cp /app:/app/lib/* ${MAIN_CLASS}"]
