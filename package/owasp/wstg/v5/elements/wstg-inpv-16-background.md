This section describes how to monitor all incoming/outgoing HTTP requests on both client-side or server-side. The purpose of this testing is to verify if there is unnecessary or suspicious HTTP request sending in the background.

Most of Web security testing tools (i.e. AppScan, BurpSuite, ZAP) act as HTTP Proxy. This will require changes of proxy on client-side application or browser. The testing techniques listed below is primary focused on how we can monitor HTTP requests without changes of client-side which will be more close to production usage scenario.

