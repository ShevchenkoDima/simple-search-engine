# simple-search-engine

Task took about 4-6 hours during 2 working days.

Project contains server and client modules:
conductor-sse-server-boot
conductor-sse-cli

# Server
Spring Boot application with swagger UI.

Build:
`mvn clean install`

Run:
`java -jar conductor-sse-server-boot/target/conductor-sse-server-boot-0.0.1-SNAPSHOT.jar`

Verify:
http://localhost:8080/swagger-ui.html

# Client
Client application sends single request to server using program arguments. 

usage: Please select one option to use simple search engine
 -get,--getDocument <arg>          Get document by unique key.
 -put,--putDocument <arg>          Put document(list of words) into the
                                   search engine by key. First argument is
                                   a key. Any other arguments are words to
                                   add into document.
 -search,--searchDocuments <arg>   Search on a string of tokens to return
                                   keys of all documents that contain all
                                   tokens.

Build:
`mvn clean install`

Run:
1) `java -jar conductor-sse-cli/target/conductor-sse-cli-0.0.1-SNAPSHOT-jar-with-dependencies.jar -put document1 My name is John`
2) `java -jar conductor-sse-cli/target/conductor-sse-cli-0.0.1-SNAPSHOT-jar-with-dependencies.jar -search John`
3) `java -jar conductor-sse-cli/target/conductor-sse-cli-0.0.1-SNAPSHOT-jar-with-dependencies.jar -get document1`

Verify:

1)Sending 'POST' request to URL : http://localhost:8080/documents/

Response Code : 200

document1

2)Sending 'GET' request to URL : http://localhost:8080/documents/search/John

Response Code : 200

["document1"]

3)Sending 'GET' request to URL : http://localhost:8080/documents/document1

Response Code : 200

{"key":"document1","text":"My name is John"}
