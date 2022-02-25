#!/usr/bin/env bash

echo "Start to build image wallet-deposit ..."
cd wallet-deposit
DEPOSIT_FOLDER=$(pwd)
mvn spring-boot:build-image
echo "End build build image at path : ${DEPOSIT_FOLDER} done."

echo "Start to build image wallet-history ..."
cd ../wallet-history
HISTORY_FOLDER=$(pwd)

mvn spring-boot:build-image
echo "End build image at path : ${HISTORY_FOLDER} done. "
echo "End build all images."