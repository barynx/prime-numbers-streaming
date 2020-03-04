# prime number streaming
The solution consists of the following components:
* ProxyService - A basic Spring WebFlux application which defines a streaming endpoint. I have defined a REST endpoint 
here as GET /prime/{number} (e.g. localhost:8080/prime/18). After hitting this endpoint in the browser you can observe the 
continuous streaming of prime numbers up to a requested number. ProxyService instantiates a client that requests a 
continuous generation of prime numbers from a PrimeNumberServer and reads from the provided stream. 
Spring WebFlux was selected for ease of use and quick bootstrapping of the service.
* PrimeNumberServer - gRPC server hosting a PrimeNumberGenerationService. The service is streaming continuously prime 
numbers up to a given number when this is requested by the ProxyService. I have deliberately slowed down the service 
by calling Thread.sleep(1000) before outputting each number, in order to demonstrate that ProxyService is reading from 
the response stream whenever a new response object is available and emits it to the requester (e.g. browser).
gRPC was chosen for this task as easier for me to set up as I had no previous exposure to Finagle-Thrift whatsoever 
(though happy to explore it in more detail as articles in Internet about its performance are promising)

The solution is by no means a production code, just an example of communication between services using gRPC and of streaming response.
The following items are missing and would need to be added for the solution to be complete:
1. Proper exception handling
2. Logging
3. Unit Tests
4. Proper comments in the code
5. Remove hardcoded config of ip and port for PrimeNumberServer
I am completely aware of them and they were ommited deliberatly due to time available for implementing this exercise. 
Happy to discuss any of the mentioned points.



