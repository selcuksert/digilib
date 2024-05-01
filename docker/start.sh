#!/usr/bin/env zsh

SCRIPT_DIR="${0:a:h}"
CERT_DIR=${SCRIPT_DIR}/certificates

[ ! -d "${CERT_DIR}" ] && mkdir "${CERT_DIR}"

[ ! -d "${CERT_DIR}"/digilib.crt ] && \
[ ! -d "${CERT_DIR}"/digilib.key ] && \
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout "${CERT_DIR}"/digilib.key -out "${CERT_DIR}"/digilib.crt \
-subj "/C=TR/ST=Ankara/L=Cankaya/O=The Corp/OU=The Corp Tech/CN=*.digilib.local" \
-addext "subjectAltName = DNS:zipkin.digilib.local"

sudo keytool -delete -cacerts -alias digilib
sudo keytool -importcert -cacerts -file "${CERT_DIR}"/digilib.crt -alias digilib -storepass changeit -noprompt

UID=${UID} GID=${GID} docker-compose -f "${SCRIPT_DIR}"/docker-compose.yml -f "${SCRIPT_DIR}"/docker-compose.production.yml down --remove-orphans

UID=${UID} GID=${GID} docker-compose -f "${SCRIPT_DIR}"/docker-compose.yml -f "${SCRIPT_DIR}"/docker-compose.production.yml up -d --build
