#!/usr/bin/env bash
./mvnw package -Pprod -DskipTests
heroku deploy:jar --jar target/*.war


