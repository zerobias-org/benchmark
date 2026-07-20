# Black-Box Testing

This vulnerability can be detected and exploited by enumerating all of the session variables used by the application and in which context they are valid. In particular this is possible by accessing a sequence of entry points and then examining exit points. In case of black-box testing this procedure is difficult and requires some luck since every different sequence could lead to a different result.

## Examples

A very simple example could be the password reset functionality that, in the entry point, could request the user to provide some identifying information such as the username or the email address. This page might then populate the session with these identifying values, which are received directly from the client-side, or obtained from queries or calculations based on the received input. At this point there may be some pages in the application that show private data based on this session object. In this manner the attacker could bypass the authentication process.

# Gray-Box Testing

The most effective way to detect these vulnerabilities is via a source code review.

