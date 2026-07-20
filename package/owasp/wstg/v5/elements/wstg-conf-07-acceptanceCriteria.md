- Confirm the presence of the HSTS header by examining the server's response through an intercepting proxy.
- Use curl as follows:

```bash
$ curl -s -D- https://owasp.org | grep -i strict-transport-security:
Strict-Transport-Security: max-age=31536000
```

