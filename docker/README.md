# Create Docker image 

Go to ./docker and run :
````cmd
docker build -t mockmock .
````
## Run Docker image

Once image is created, you need to create a container with the image.
run :

````
run --name mock_server -p 8282:8282 -p 25:25 mockmock
````
--name gives a name to the container
-p link the container of the port, 8282 is the http port and 25 is the SMTP port
"mockmock" is the name of the image 

