# Black-Box Testing

The same approach seen in the [Testing for logout functionality](06-Testing_for_Logout_Functionality.md) section can be applied when measuring the timeout log out.
The testing methodology is very similar. First, testers have to check whether a timeout exists, for instance, by logging in and waiting for the timeout log out to be triggered. As in the log out function, after the timeout has passed, all session tokens should be destroyed or be unusable.

Then, if the timeout is configured, testers need to understand whether the timeout is enforced by the client or by the server (or both). If the session cookie is non-persistent (or, more in general, the session cookie does not store any data about the time), testers can assume that the timeout is enforced by the server. If the session cookie contains some time related data (e.g., log in time, or last access time, or expiration date for a persistent cookie), then it's possible that the client is involved in the timeout enforcing. In this case, testers could try to modify the cookie (if it's not cryptographically protected) and see what happens to the session. For instance, testers can set the cookie expiration date far in the future and see whether the session can be prolonged.

As a general rule, everything should be checked server-side and it should not be possible, by re-setting the session cookies to previous values, to access the application again.

# Gray-Box Testing

The tester needs to check that:

- The log out function effectively destroys all session token, or at least renders them unusable,
- The server performs proper checks on the session state, disallowing an attacker to replay previously destroyed session identifiers
- A timeout is enforced and it is properly enforced by the server. If the server uses an expiration time that is read from a session token that is sent by the client (but this is not advisable), then the token must be cryptographically protected from tampering.

Note that the most important thing is for the application to invalidate the session on the server-side. Generally this means that the code must invoke the appropriate methods, e.g. `HttpSession.invalidate()` in Java and `Session.abandon()` in .NET. Clearing the cookies from the browser is advisable, but is not strictly necessary, since if the session is properly invalidated on the server, having the cookie in the browser will not help an attacker.

