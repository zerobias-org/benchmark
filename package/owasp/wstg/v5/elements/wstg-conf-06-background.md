- Enumerate supported HTTP methods.
- Test for access control bypass.
- Test HTTP method overriding techniques.

HTTP offers a number of methods (or verbs) that can be used to perform actions on the web server. While GET and POST are by far the most common methods that are used to access information provided by a web server, there are a variety of other methods that may also be supported, and can sometimes be exploited by attackers.

[RFC 7231](https://datatracker.ietf.org/doc/html/rfc7231) defines the main valid HTTP request methods (or verbs), although additional methods have been added in other RFCs, such as [RFC 5789](https://datatracker.ietf.org/doc/html/rfc5789). Several of these verbs have been re-used for different purposes in [RESTful](https://en.wikipedia.org/wiki/Representational_state_transfer) applications, listed in the table below.

| Method | Original Purpose | RESTful Purpose |
|--------|------------------|-----------------|
| [`GET`](https://datatracker.ietf.org/doc/html/rfc7231#section-4.3.1) | Request a file. | Request an object.|
| [`HEAD`](https://datatracker.ietf.org/doc/html/rfc7231#section-4.3.2) | Request a file, but only return the HTTP headers. | |
| [`POST`](https://datatracker.ietf.org/doc/html/rfc7231#section-4.3.3) | Submit data. | |
| [`PUT`](https://datatracker.ietf.org/doc/html/rfc7231#section-4.3.4) | Upload a file. | Create an object. |
| [`DELETE`](https://datatracker.ietf.org/doc/html/rfc7231#section-4.3.5) | Delete a file | Delete an object. |
| [`CONNECT`](https://datatracker.ietf.org/doc/html/rfc7231#section-4.3.6) | Establish a connection to another system. | |
| [`OPTIONS`](https://datatracker.ietf.org/doc/html/rfc7231#section-4.3.7) | List supported HTTP methods. | Perform a [CORS Preflight](https://developer.mozilla.org/en-US/docs/Glossary/Preflight_request) request. |
| [`TRACE`](https://datatracker.ietf.org/doc/html/rfc7231#section-4.3.8) | Echo the HTTP request for debug purposes. | |
| [`PATCH`](https://datatracker.ietf.org/doc/html/rfc5789#section-2) |  | Modify an object. |

