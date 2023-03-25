#!/bin/sh

source ~/.zshrc

docker-compose up -d

tflocal -chdir="./tf" init
tflocal -chdir="./tf" apply -auto-approve