#!/bin/bash

PORT=8000
PID_FILE=/tmp/vbad_lock_${PORT}.pid
KILL_DELAY=3
VBAD=./vBaD.jar

function start {
  if [ -e ${PID_FILE} ] ; then
    echo "vBaD is still running, please stop it first"
  else
    JAVA_OPTS="${JAVA_OPTS} -DVBAD_PORT=${PORT}"
    nohup java -jar ${JAVA_OPTS} ${VBAD} &
    echo $! > ${PID_FILE}
  fi
}

function stop {
  if [ -e ${PID_FILE} ] ; then
    PID=$(head -1 ${PID_FILE})
    kill -0 ${PID}
    if [ $? -ne 0 ] ; then
      echo "vBaD not running on pid [${PID}]"
      rm -f ${PID_FILE}
    else
      kill ${PID}
      sleep ${KILL_DELAY}
      kill -0 ${PID} 2> /dev/null
      if [ $? -eq 0 ] ; then
        echo "vBaD still running after kill and [${KILL_DELAY}] seconds, killing forcibly"
        kill -9 ${PID}
      fi
      rm -f ${PID_FILE}
    fi    
  fi
}

case $1 in
  start)    start;;
  stop)     stop;;
  restart)  stop; start;;
esac
