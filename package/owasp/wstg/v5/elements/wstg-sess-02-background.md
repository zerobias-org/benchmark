Web Cookies (herein referred to as cookies) are often a key attack vector for malicious users (typically targeting other users) and the application should always take due diligence to protect cookies.

HTTP is a stateless protocol, meaning that it doesn't hold any reference to requests being sent by the same user. In order to fix this issue, sessions were created and appended to HTTP requests. Browsers, as discussed in [testing browser storage](../11-Client-side_Testing/12-Testing_Browser_Storage.md), contain a multitude of storage mechanisms. In that section of the guide, each is discussed thoroughly.

The most used session storage mechanism in browsers is cookie storage. Cookies can be set by the server, by including a [`Set-Cookie`](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Set-Cookie) header in the HTTP response or via JavaScript. Cookies can be used for a multitude of reasons, such as:

- session management
- personalization
- tracking

In order to secure cookie data, the industry has developed means to help lock down these cookies and limit their attack surface. Over time cookies have become a preferred storage mechanism for web applications, as they allow great flexibility in use and protection.

The means to protect the cookies are:

- [Cookie Attributes](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#Creating_cookies)
- [Cookie Prefixes](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#Cookie_prefixes)

