@echo off

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongod --replSet rs0 --port 27017 --bind_ip localhost --dbpath C:\data\db"

TIMEOUT /T 10 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongod --replSet rs0 --port 27018 --bind_ip localhost --dbpath C:\data\n1"

TIMEOUT /T 10 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongod --replSet rs0 --port 27019 --bind_ip localhost --dbpath C:\data\n2"


