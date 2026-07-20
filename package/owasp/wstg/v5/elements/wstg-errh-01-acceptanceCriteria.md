Errors are usually seen as benign as they provide diagnostics data and messages that could help the user understand the problem at hand, or for the developer to debug that error.

By trying to send unexpected data, or forcing the system into certain edge cases and scenarios, the system or application will, most of the time, give out a bit on what's happening internally, unless the developers turned off all possible errors and return a certain custom message.

# Web Servers

All web apps run on a web server, whether it was an integrated one or a fully fledged one. Web apps must handle and parse HTTP requests, and for that a web server is always part of the stack. Some of the most famous web servers are Nginx, Apache, and IIS.

Web servers have known error messages and formats. If one is not familiar with how they look, searching online for them would provide examples. Another way would be to look into their documentation, or simply setup a server locally and discover the errors by going through the pages that the web server uses.

In order to trigger error messages, a tester must:

- Search for random files and folders that will not be found (404s).
- Try to request folders that exist and see the server behavior (403s, blank page, or directory listing).
- Try sending a request that breaks the [HTTP RFC](https://tools.ietf.org/html/rfc7231). One example would be to send a very large path, break the headers format, or change the HTTP version.
    - Even if errors are handled on the application level, breaking the HTTP RFC may make the integrated web server show itself since it has to handle the request, and developers forget to override these errors.

# Applications

Applications are the most susceptible to let out a wide variety of error messages, which include: stack traces, memory dumps, mishandled exceptions, and generic errors. This happens due to the fact that applications are custom built most of the time and the developers need to observe and handle all possible error cases (or have a global error catching mechanism), and these errors can appear from integrations with other services.

In order to make an application throw these errors, a tester must:

1. Identify possible input points where the application is expecting data.
2. Analyse the expected input type (strings, integers, JSON, XML, etc.).
3. Fuzz every input point based on the previous steps to have a more focused test scenario.
   - Fuzzing every input with all possible injections is not the best solution unless you have unlimited testing time and the application can handle that much input.
   - If fuzzing isn't an option, handpick viable inputs that have the highest chance to break a certain parser (*e.g.* a closing bracket for a JSON body, a large text where only a couple of characters are expected, CLRF injection with parameters that might be parsed by servers and input validation controls, special characters that aren't applicable for filenames, etc.).
   - Fuzzing with jargon data should be ran for every type as sometimes the interpreters will break outside of the developer's exception handling.
4. Understand the service responding with the error message and try to make a more refined fuzz list to bring out more information or error details from that service (it could be a database, a standalone service, etc.).

Error messages are sometimes the main weakness in mapping out systems, especially under a microservice architecture. If services are not properly set to handle errors in a generic and uniform manner, error messages would let a tester identify which service handles which requests, and allows for a more focused attack per service.

> The tester needs to keep a vigilant eye for the response type. Sometimes errors are returned as success with an error body, hide the error in a 302, or simply by having a custom way of representing that error.
