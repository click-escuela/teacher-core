#!/usr/bin/env bash
cd /home/ec2-user/server/teacher-core
sudo java -jar -Dspring.profiles.active=prod -Dspring.datasource.url=jdbc:mysql://clickescuela.ccmmeszml0xl.us-east-2.rds.amazonaws.com:3306/clickescuela -Dspring.datasource.username=root -Dspring.datasource.password=secret123 \
    teacher.core-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null & echo $! > teacher-service.pid