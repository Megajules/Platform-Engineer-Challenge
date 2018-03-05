# Platform-Engineer-Challenge
Analysis of MongoDB data within a distributed system
README

1.	Location of remote git repository: https://github.com/Megajules/Platform-Engineer-Challenge
2.	Location of mongo on local machine:  D:\MongoDB\Server\3.6\bin
3.	Log4j log file is located at: D:/log.out
4.	Mongo log file is located at:  D:/data/log/mongod.log
5.	My database is called challengeDb
6.	My collection is called wordsCollection

Prerequisites:
Installation of MongoDB
Create Replica Set: rs0, PRIMARY localhost:27017, SECONDARY localhost:27018, localhost:27019
Place the challenge.jar and the source file into the same directory.
Run the servers.  (I created .bat files to quickly start up the replica set servers.)
Check that the three servers in the replica set are up and running, and have elected a Primary Server before proceeding to run the application.

Command Line:
(Replace args values in the below command line with your own values.)
java -Xmx8192m -jar challenge.jar -source partfile.txt -database challengeDb -collection wordsCollection -host localhost -port 17017

Run Instructions: 
1.	Download the challenge.jar from the github respository
2.	Obtain and place the source datafile into the same directory as the challenge.jar
3.	Go to your ".jar" file directory.
4.	 Open a cmd window.  (in Windows: ctrl + shift + right click, then select Open command window from here) 
5.	Type the commandline at the prompt, (substituting the args values after challenge.jar with your own options)
NOTES:  
1.	Running the analysis menu item will cause an analysis to occur.  The output during the first phase is the extraction of words and their frequencies from the source document. These are then added to a map, and subsequently into documents in the database.  There is one document created for each distinct word extracted from the source document.

Shards:
To process large source files of several gigabytes, it will be necessary to create Shards to distribute the load.

Create a Shard Server:

The committed bat file contains the steps taken to create the shards.


