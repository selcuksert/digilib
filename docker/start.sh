#!/usr/bin/env zsh

MODE="development"

Help()
{
   # Display Help
   echo "Start script for digilib app"
   echo
   echo "Syntax: start.sh [-h|p|d]"
   echo "options:"
   echo "h     Prints this Help."
   echo "p     Start in production mode."
   echo "d     Start in developer mode."
   echo "u     Uninstall setup."
   echo
}

while getopts ":hpdu" option; do
   case $option in
      h) # display Help
         Help
         exit;;
      p) # set mode to production
         MODE="production"
         ;;
      d) # set mode to development
         MODE="development"
         ;;
      u) # uninstall setup
         MODE="uninstall"
         ;;
     \?) # Invalid option
         echo "Error: Invalid option"
         Help
         exit;;
   esac
done

if [[ $OPTIND -eq 1 ]]; then
  Help
  exit;
fi

SCRIPT_DIR="${0:a:h}"
CERT_DIR=${SCRIPT_DIR}/certificates

if [[ $MODE != "uninstall" ]]; then
  [ ! -d "${CERT_DIR}" ] && mkdir "${CERT_DIR}"

  [ ! -d "${CERT_DIR}"/digilib.crt ] && \
  [ ! -d "${CERT_DIR}"/digilib.key ] && \
  openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout "${CERT_DIR}"/digilib.key -out "${CERT_DIR}"/digilib.crt \
  -subj "/C=TR/ST=Ankara/L=Cankaya/O=The Corp/OU=The Corp Tech/CN=*.digilib.local" \
  -addext "subjectAltName = DNS:zipkin.digilib.local"

  sudo keytool -delete -cacerts -alias digilib
  sudo keytool -importcert -cacerts -file "${CERT_DIR}"/digilib.crt -alias digilib -storepass changeit -noprompt
fi

if [[ $MODE == "development" ]]; then
  echo "Running in $MODE mode"
  UID=${UID} GID=${GID} docker-compose -f "${SCRIPT_DIR}"/docker-compose.yml -f "${SCRIPT_DIR}"/docker-compose.production.yml down --remove-orphans
  UID=${UID} GID=${GID} docker-compose -f "${SCRIPT_DIR}"/docker-compose.yml -f "${SCRIPT_DIR}"/docker-compose.production.yml up -d --build
elif [[ $MODE == "production" ]]; then
  echo "Running in $MODE mode"
  UID=${UID} GID=${GID} docker-compose -f "${SCRIPT_DIR}"/docker-compose.yml -f "${SCRIPT_DIR}"/docker-compose.production.yml down --remove-orphans
  UID=${UID} GID=${GID} docker-compose -f "${SCRIPT_DIR}"/docker-compose.yml -f "${SCRIPT_DIR}"/docker-compose.production.yml up -d --build
elif [[ $MODE == "uninstall" ]]; then
  echo "Running in $MODE mode"
  sudo keytool -delete -cacerts -alias digilib
  UID=${UID} GID=${GID} docker-compose -f "${SCRIPT_DIR}"/docker-compose.yml -f "${SCRIPT_DIR}"/docker-compose.production.yml down --remove-orphans --rmi all -v
fi
