# Description

This program runs pranks on a fake SMTP server. We use MockMock
as the fake SMTP server.

# Prerequisite

* Java SDK 17
* Docker
# How to run a prank

To successfully prank people, there is a few steps you need to follow.

## Edit config file

In order to make the program works, you need to configure it
with the three configuration files available in ./config folder.

### config.properties

This config file manage the server address and port and
the number of group you want to create for your prank.

Address need to be a valid address ip or "localhost"

Exemple :
```
serverAddress=192.168.3.4
serverPort=25
groups=5
```

### victims.txt

This is the list of your victims. they need to be valid email address
that follows the RFC 5322 that can be found [here](https://datatracker.ietf.org/doc/html/rfc5322).

Each line must contain a single email address.

Exemple :

```
myfirstemail@gmail.com
mysecondemail@hotmail.ch
mythirdemail@protonmail.ch
```
### messages.txt

This is the list of messages that is going to be sent in your mails.

Each message is divided in 2 parts, subject and body.

the subject starts with "**s:**"
the body starts with "**m:**"
and each message end with the **END** keyword.

the body of the email can contain HTML and support UTF-8.

Exemple : 

```
s:MySubject
m:Body of my Email
it can have multiple
rows
END

s:MyAnotherSubject
m:the second body of my email
END
```

### MockMock

MockMock is a mock cross-platform SMTP server built on Java.
you can find the repo [here](https://github.com/DominiqueComte/MockMock).

by default, the web interface of MockMock is running on the port **8282**
and the SMTP port is **25**.

Because it was asked in the laboratory, we need it to run it
in a docker container.

We first need to build an image capable of running a Java program

go inside the ./docker folder, the Dockerfile inside is already configured.

_if you want to run MockMock on different ports, change the CMD
command in the DockerFile_


inside ./docker, run the following command.

````cmd
docker build -t mockmock .
````
Once the image has been created we can create a container
based on this image. Run the following command : 

````
docker run --name mock_server -p 8282:8282 -p 25:25 mockmock
````
--name gives a name to the container

-p link a host port to a port inside the container

8282 is the http port and 25 is the SMTP port
"mockmock" is the name of the image you created above.

_If you changed the ports used by MockMock in the Dockerfile 
change the port accordingly in the command above_

### Send an Email

When the Docker container is running, you can finally run your pranks !
go to target/ and run : 
```
java -jar DAI-2022-SMTP-1.0-SNAPSHOT.jar
```

## Program structure
### Insert UML here