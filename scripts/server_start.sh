#!/usr/bin/env bash
cd /home/ec2-user/server/teacher-core

sudo rm -rf /home/ec2-user/server/teacher-core/teacher-service.pid

echo "eliminando archivo"

sudo java -jar -Dspring.profiles.active=prod -Dlogging.file.name=/home/ec2-user/server/teacher-core/debug.log \
    teacher.core-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null & echo $! > teacher-service.pid
