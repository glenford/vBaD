#!/bin/bash

DEPLOY_DIR=$1
JAR_DIR=target/scala_2.8.0
JAR_NAME=vbad_2.8.0-?.?.min.jar
DEST_JAR=vBaD.jar
SCRIPT_DIR=scripts
SCRIPT_NAME=vBaD.sh

function deploy {
  cp ${SCRIPT_DIR}/${SCRIPT_NAME} ${DEPLOY_DIR}
  cp ${JAR_DIR}/${JAR_NAME} ${DEPLOY_DIR}/${DEST_JAR}
}

if [ ! -d ${DEPLOY_DIR} ] ; then
  mkdir ${DEPLOY_DIR}
  deploy
else
  # stop it if its running
  ${SCRIPT_DIR}/${SCRIPT_NAME} stop
  if [ $? -eq 0 ] ; then
    echo "Stopped running vBaD"
    deploy
  else
    echo "Failed to stop running process"
    exit -1
  fi
fi

cd ${DEPLOY_DIR}
./${SCRIPT_NAME} start
exit $?
