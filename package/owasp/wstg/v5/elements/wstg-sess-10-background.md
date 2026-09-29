- Determine whether the JWTs expose sensitive information.
- Determine whether the JWTs can be tampered with or modified.

JSON Web Tokens (JWTs) are cryptographically signed JSON tokens, intended to share claims between systems. They are frequently used as authentication or session tokens, particularly on REST APIs.

JWTs are a common source of vulnerabilities, both in how they are in implemented in applications, and in the underlying libraries. As they are used for authentication, a vulnerability can easily result in a complete compromise of the application.

