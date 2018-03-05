@echo off

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongod --replSet rs0 --shardsvr --port 27017 --bind_ip localhost --dbpath C:\data\db"

TIMEOUT /T 20 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongod --replSet rs0 --shardsvr --port 27018 --bind_ip localhost --dbpath C:\data\n1"

TIMEOUT /T 20 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongod --replSet rs0 --shardsvr --port 27019 --bind_ip localhost --dbpath C:\data\n2"

TIMEOUT /T 20 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongod --configsvr --replSet rs1 --bind_ip localhost --port 25000 --dbpath C:\data\con1"

TIMEOUT /T 20 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongod --configsvr --replSet rs1 --bind_ip localhost --port 25001 --dbpath C:\data\con2"

TIMEOUT /T 20 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongod --configsvr --replSet rs1 --bind_ip localhost --port 25002 --dbpath C:\data\con3"

TIMEOUT /T 20 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongod --replSet rs2 --shardsvr --port 27020 --bind_ip localhost --dbpath C:\data\n5"

TIMEOUT /T 20 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongod --replSet rs2 --shardsvr --port 27021 --bind_ip localhost --dbpath C:\data\n6"

TIMEOUT /T 20 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongod --replSet rs2  --shardsvr --port 27022 --bind_ip localhost --dbpath C:\data\n7"

TIMEOUT /T 20 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongo --host localhost --port 27017 &&

rs.initiate(
  {
    _id: "rs0",
    members: [
      { _id : 0, host : "localhost:27017" },
      { _id : 1, host : "localhost:27018" },
      { _id : 2, host : "localhost:27019" }
    ]
  }
)

TIMEOUT /T 20 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongo --host localhost --port 27020 &&

rs.initiate(
  {
    _id: "rs1",
    configsvr: true,
    members: [
      { _id : 0, host : "localhost:27020" },
      { _id : 1, host : "localhost:27021" },
      { _id : 2, host : "localhost:27022" }
    ]
  }
)

TIMEOUT /T 20 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongo --host localhost --port 25000 &&

rs.initiate(
  {
    _id: "rs2",
    configsvr: true,
    members: [
      { _id : 0, host : "localhost:25000" },
      { _id : 1, host : "localhost:25001" },
      { _id : 2, host : "localhost:25002" }
    ]
  }
)

TIMEOUT /T 20 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongos --configdb rs1/localhost:27020,localhost:27021,localhost:27022 --bind_ip localhost

TIMEOUT /T 20 START

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongo localhost:27017/admin

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongo sh.addShard( "rs0/localhost:27017,localhost:27018,localhost:27019" )

sh.enableSharding( "ChallengeDb" )

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongo use ChallengeDb && 

db.wordsCollection.createIndex( { word : 1 } )

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongo use ChallengeDb && 
sh.shardCollection( "ChallengeDb.wordsCollection", { "word" : 1 } )

START "runas /user:administrator" cmd /K "cd C:\Program Files\MongoDB\Server\3.6\bin & mongo use ChallengeDb && 
db.stats() &&
db.printShardingStatus()
