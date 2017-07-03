# talktome
Talk to me is a real time chat application from peer to peer. It authenticates by user and communicates to each other.

## Requirement
Java 8 to execute application.

## Running Application
mvn install "please refer to Maven documentation for setting up mvn on your computer" http://maven.apache.org/install.html<br/>
mvn spring-boot:run Starts the application

## Access
* Swagger Editor Access : http://localhost:8080/swagger-ui.html <br/>
* Chat UI Access : http://localhost:8080/index.html <br/>
* Swagger JSON Access : http://localhost:8080/v2/api-docs <br/>

## Security
There is a simple security implementation. The security is managed via default username "1" and password "1".

## Build Status

[![Build Status](https://travis-ci.org/ibrahimbayer/talktome.svg?branch=master)](https://travis-ci.org/ibrahimbayer/talktome)