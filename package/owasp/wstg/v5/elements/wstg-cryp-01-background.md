- Validate the service configuration.
- Review the digital certificate's cryptographic strength and validity.
- Ensure that the TLS security is not bypassable and is properly implemented across the application.

When information is sent between the client and the server, it must be encrypted and protected in order to prevent an attacker from being able to read or modify it. This is most commonly done using HTTPS, which uses the [Transport Layer Security (TLS)](https://en.wikipedia.org/wiki/Transport_Layer_Security) protocol, a replacement for the older Secure Socket Layer (SSL) protocol. TLS also provides a way for the server to demonstrate to the client that they have connected to the correct server, by presenting a trusted digital certificate.

Over the years there have been a large number of cryptographic weaknesses identified in the SSL and TLS protocols, as well as in the ciphers that they use. Additionally, many of the implementations of these protocols have also had serious vulnerabilities. As such, it is important to test that sites are not only implementing TLS, but that they are doing so in a secure manner.

