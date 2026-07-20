- When implementing OAuth, always consider the technology used and whether the application is a server-side application that can avoid revealing secrets, or a client-side application that cannot.
- In almost any case, use the Authorization Code flow with PKCE. One exception may be machine-to-machine flows.
- Use POST parameters or header values to transport secrets.
- When no other possibilities exists (for example, in legacy applications that can not be migrated), implement additional security headers such as a `Referrer-Policy`.

