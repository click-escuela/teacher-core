#!/bin/bash
chmod +x /home/ec2-user/server/teacher-core/logs
chmod +x /home/ec2-user/server/teacher-core/logs/error.log
chmod +x /home/ec2-user/server/teacher-core/logs/debug.log
var="$(cat /home/ec2-user/server/teacher-core/teacher-service.pid)"
sudo kill $var
sudo rm -rf /home/ec2-user/server/teacher-core/teacher-service.pid
