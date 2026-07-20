# Black-Box Testing

1. Identify that the application is using WebSockets.
   - Inspect the client-side source code for the `ws://` or `wss://` URI scheme.
   - Use Google Chrome's Developer Tools to view the Network WebSocket communication.
   - Use [ZAP's](https://www.zaproxy.org) WebSocket tab.
2. Origin.
   - Using a WebSocket client (one can be found in the Tools section below) attempt to connect to the remote WebSocket server. If a connection is established the server may not be checking the origin header of the WebSocket handshake.
3. Confidentiality and Integrity.
   - Check that the WebSocket connection is using TLS to transport sensitive information `wss://`.
   - Check the HTTPS Implementation for security issues (Valid Certificate, BEAST, CRIME, RC4, etc). Refer to the [Testing for Weak Transport Layer Security](../09-Testing_for_Weak_Cryptography/01-Testing_for_Weak_Transport_Layer_Security.md) section of this guide.
4. Authentication.
   - WebSockets do not handle authentication, normal black-box authentication tests should be carried out. Refer to the [Authentication Testing](../04-Authentication_Testing/README.md) sections of this guide.
5. Authorization.
   - WebSockets do not handle authorization, normal black-box authorization tests should be carried out. Refer to the [Authorization Testing](../05-Authorization_Testing/README.md) sections of this guide.
6. Input Sanitization.
   - Use [ZAP's](https://www.zaproxy.org) WebSocket tab to replay and fuzz WebSocket request and responses. Refer to the [Testing for Data Validation](../07-Input_Validation_Testing/README.md) sections of this guide.

## Example 1

Once we have identified that the application is using WebSockets (as described above) we can use the [Zed Attack Proxy (ZAP)](https://www.zaproxy.org) to intercept the WebSocket request and responses. ZAP can then be used to replay and fuzz the WebSocket request/responses.

![ZAP WebSockets](images/OWASP_ZAP_WebSockets.png)\
*Figure 4.11.10-1: ZAP WebSockets*

## Example 2

Using a WebSocket client (one can be found in the Tools section below) attempt to connect to the remote WebSocket server. If the connection is allowed the WebSocket server may not be checking the WebSocket handshake's origin header. Attempt to replay requests previously intercepted to verify that cross-domain WebSocket communication is possible.

![WebSocket Client](images/WebSocket_Client.png)\
*Figure 4.11.10-2: WebSocket Client*

# Gray-Box Testing

Gray-box testing is similar to black-box testing. In gray-box testing, the pen-tester has partial knowledge of the application. The only difference here is that you may have API documentation for the application being tested which includes the expected WebSocket request and responses.

