#!/usr/bin/env bash

echo `Start to build image wallet-deposit ...`
cd wallet-deposit
DEPOSIT_FOLDER=$(pwd)


mvn spring-boot:build-image

echo `End build $DEPOSIT_FOLDER done.` 
echo `Start to build image wallet-history ...`
cd ../wallet-history

HISTORY_FOLDER=$(pwd)


mvn spring-boot:build-image

cd ..

echo `End build $HISTORY_FOLDER done. `