#!/bin/bash
var="$(cat /home/ec2-user/server/teacher-core/teacher-service.pid)"
sudo kill $var
sudo rm -rf /home/ec2-user/server/teacher-core/teacher-service.pid
