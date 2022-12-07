# Description


This project makes it possible to create grouped shipments of pranks. The  user can define a list of victim emails as well as prank messages to  send. Subsequently, the program will take care of creating groups and  randomly send one of the messages to each group.

# Prerequisite

* Java SDK 17 to run the program
* Docker installed on your machne to run the mock SMTP server
* 
# How to run a prank


To successfully prank people, there is a few steps you need to follow. Firstly clone this repo on your machine.

## Edit config files


In order to make the program works, you need to configure it with the three configuration files available in ./config folder.

### config.properties

This config file manage the server address and port and the number of group you want to create for your prank. This file must contain 3 properties:

- serverAddress : The address of the mock server.
- serverPort: port on which the mock SMTP server is listenning.
- groups: The number of groups you want to create. If you enter 1, then a random mail will be sent to all email addresses located in the victims.txt file. If you enter a value > 1, the program will create unique groups of victims based on the number of email addresses stored in the victims.txt file.

Example :
```
serverAddress=localhost
serverPort=25
groups=5
```

### victims.txt

This is the list of your victims. they must be valid email addresses that follows the RFC 5322 (more info  [here](https://datatracker.ietf.org/doc/html/rfc5322) and [here](https://stackoverflow.com/questions/13992403/regex-validation-of-email-addresses-according-to-rfc5321-rfc5322)). If at least one mail is not valid, the program will throw an exception and exit. Each line must contain a single email address, without any space between them.

If you plan to send pranks to 5 groups, you will need a minimum of 15 email addresses, because the minimum size of a group is 3 (1 sender and 2 recipients).

Example :

```
myfirstemail@gmail.com
mysecondemail@hotmail.ch
mythirdemail@protonmail.ch
```
### messages.txt

This is the list of messages that is going to be sent in your mails. Each message is divided in 3 parts, subject and body and END of the message. 

- the subject starts with "**s:**"

- the body starts with "**m:**"

- and each message must end with a line containing END as the first 3 character. The body of the email can contain HTML and support UTF-8.

Example : 

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

MockMock is a mock cross-platform SMTP server built on Java. You can find the repo [here](https://github.com/DominiqueComte/MockMock). MockMock is a  simulates a SMTP server locally. It will retrieve all the emails sent from your machine and display them  to it's web interface. This way, it's easy to test the app without sending emails to real people.

by default, the web interface is running on the port **8282** and the SMTP port is **25**. The mock server will run inside a docker container on our local machine, so we first need to build an image capable of running a Java program:

go inside the ./docker folder, the Dockerfile inside is already configured.


_If you want to run MockMock on different ports, change the CMD command in the DockerFile_


inside ./docker, run the following command.

````cmd
docker build -t mockmock .
````
Once the docker image has been created we can create a container based on this image.

Run the following command : 

````
docker run --name mock_server -p 8282:8282 -p 25:25 mockmock
````
--name gives a name to the container

-p link a host port to a port inside the container

8282 is the http port and 25 is the SMTP port
"mockmock" is the name of the image you created above.

_If you changed the ports used by MockMock in the Dockerfile change the port accordingly in the command above_

### Send an Email

When the Docker container is running, you can finally run your pranks !
go to target/ and run : 

```
java -jar DAI-2022-SMTP-1.0-SNAPSHOT.jar
```

# Program structure

## Class diagram

![UML](/figures/UML.png)

## Classes description

The architecture is fairly simple : in one hand, we must connect to a SMTP server with a client and send some emails. In the other hand we must retrieve messages, email addresses and manipulate them to form an email usable by the SMTP client.

**Config loader**

This class is used to retrieve all the information contained in the files in the config directory. In addition, this class will check if the email addresses comply with the RFC 5322 "Internet Message Format". If any file in the config directory has an error, the program will exit.

**Client**

The client manages connection and communication with the SMTP server. It will ensure that the server returns a success response after receiving the various commands from the client. The client allows to send HTML content and supports UTF-8 encoding.

**Group**

The group contains the recipients and the sender of the mail. More importantly, it offers the method generate() that will  generate a mail object with random recipients, a random sender and a randomly selected message. The minimum size of a group is 3, 1 sender and 2 recipients.

If the user did not put enough email addresses in the victims.txt file, the programm will show an error and exit. The number of email addresses must be at least 3 times greater than the number of group.

**Mail**

The mail class contains all information needed for the client so send a mail:

- A mail subject
- A mail body/content
- The recipients email addresses
- The sender email address

**Message**

A simple class that contains the basic implementation of a message i.e a subject and a body/content