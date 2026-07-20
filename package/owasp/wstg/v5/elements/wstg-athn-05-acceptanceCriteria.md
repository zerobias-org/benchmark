As these methods provide a better user experience and allow the user to forget all about their credentials, they increase the attack surface area. Some applications:

- Store the credentials in an encoded fashion in the browser's storage mechanisms, which can be verified by following the [web storage testing scenario](../11-Client-side_Testing/12-Testing_Browser_Storage.md) and going through the [session analysis](../06-Session_Management_Testing/01-Testing_for_Session_Management_Schema.md#session-analysis) scenarios. Credentials shouldn't be stored in any way in the client-side application, and should be substituted by tokens generated server-side.
- Automatically inject the user's credentials that can be abused by:
    - [ClickJacking](../11-Client-side_Testing/09-Testing_for_Clickjacking.md) attacks.
    - [CSRF](../06-Session_Management_Testing/05-Testing_for_Cross_Site_Request_Forgery.md) attacks.
- Tokens should be analyzed in terms of token-lifetime, where some tokens never expire and put the users in danger if those tokens ever get stolen. Make sure to follow the [session timeout](../06-Session_Management_Testing/07-Testing_Session_Timeout.md) testing scenario.

