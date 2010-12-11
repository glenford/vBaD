#!/bin/bash

#
# resource to test Process handling
#

if [ -z ${1} ] ; then
  exit 0
fi

if [ ${1} == "exit" ] ; then
  exit ${2}
fi
