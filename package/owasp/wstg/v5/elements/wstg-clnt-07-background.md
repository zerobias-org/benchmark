- Identify endpoints that implement CORS.
- Ensure that the CORS configuration is secure or harmless.

[Cross Origin Resource Sharing](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing) (CORS) is a mechanism that enables a web browser to perform cross-domain requests using the XMLHttpRequest (XHR) Level 2 (L2) API in a controlled manner. In the past, the XHR L1 API only allowed requests to be sent within the same origin as it was restricted by the [Same Origin Policy](https://developer.mozilla.org/en-US/docs/Web/Security/Same-origin_policy) (SOP).

Cross-origin requests have an `Origin` header that identifies the domain initiating the request and is always sent to the server. CORS defines the protocol to use between a web browser and a server to determine whether a cross-origin request is allowed. HTTP [headers](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing#Headers) are used to accomplish this.

The [W3C CORS specification](https://www.w3.org/TR/cors/) mandates that for non simple requests, such as requests other than GET or POST or requests that uses credentials, a pre-flight OPTIONS request must be sent in advance to check if the type of request will have a bad impact on the data. The pre-flight request checks the methods and headers allowed by the server, and if credentials are permitted. Based on the result of the OPTIONS request, the browser decides whether the request is allowed or not.

# Origin & Access-Control-Allow-Origin

The `Origin` request header is always sent by the browser in a CORS request and indicates the origin of the request. The Origin header cannot be changed from JavaScript as [the browser (the user-agent) blocks its modification](https://developer.mozilla.org/en-US/docs/Glossary/Forbidden_header_name); however, relying on this header for Access Control checks is not a good idea as it may be spoofed outside the browser, for example by using a proxy, so you still need to check that application-level protocols are used to protect sensitive data.

`Access-Control-Allow-Origin` is a response header used by a server to indicate which domains are allowed to read the response. Based on the CORS W3 Specification it is up to the client to determine and enforce the restriction of whether the client has access to the response data based on this header.

From a security testing perspective you should look for insecure configurations as for example using a `*` wildcard as value of the `Access-Control-Allow-Origin` header that means all domains are allowed. Another insecure example is when the server returns back the origin header without any additional checks, which can lead to access of sensitive data. Note that the configuration of allowing cross-origin requests is very insecure and is not acceptable in general terms, except in the case of a public API that is intended to be accessible by everyone.

# Access-Control-Request-Method & Access-Control-Allow-Method

The `Access-Control-Request-Method` header is used when a browser performs a preflight OPTIONS request and lets the client indicate the request method of the final request. On the other hand, the `Access-Control-Allow-Method` is a response header used by the server to describe the methods the clients are allowed to use.

# Access-Control-Request-Headers & Access-Control-Allow-Headers

These two headers are used between the browser and the server to determine which headers can be used to perform a cross-origin request.

# Access-Control-Allow-Credentials

This response header allows browsers to read the response when credentials are passed. When the header is sent, the web application must set an origin to the value of the `Access-Control-Allow-Origin` header. The `Access-Control-Allow-Credentials` header cannot be used along with the `Access-Control-Allow-Origin` header whose value is the `*` wildcard like the following:

```http
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
```

# Input Validation

XHR L2 introduces the possibility of creating a cross-domain request using the XHR API for backwards compatibility. This can introduce security vulnerabilities that in XHR L1 were not present. Interesting points of the code to exploit would be URLs that are passed to XMLHttpRequest without validation, specially if absolute URLs are allowed because that could lead to code injection. Likewise, other part of the application that can be exploited is if the response data is not escaped and we can control it by providing user-supplied input.

# Other Headers

There are other headers involved like `Access-Control-Max-Age` that determines the time a preflight request can be cached in the browser, or `Access-Control-Expose-Headers` that indicates which headers are safe to expose to the API of a CORS API specification.

To review CORS headers, refer to the [CORS MDN document](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS#The_HTTP_response_headers).

