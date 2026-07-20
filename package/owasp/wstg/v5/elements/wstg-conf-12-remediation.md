Configure a strong content security policy which reduces the attack surface of the application. Developers can verify the strength of content security policy using online tools such as [Google CSP Evaluator](https://csp-evaluator.withgoogle.com/).

# Strict Policy

A strict policy is a policy which provides protection against classical stored, reflected, and some of the DOM XSS attacks and should be the optimal goal of any team trying to implement CSP.

Google went ahead and set up a guide to adopt a strict CSP based on nonces. Based on a presentation at [LocoMocoSec](https://speakerdeck.com/lweichselbaum/csp-a-successful-mess-between-hardening-and-mitigation?slide=55), the following two policies can be used to apply a strict policy:

Moderate Strict Policy:

```HTTP
script-src 'nonce-r4nd0m' 'strict-dynamic';
object-src 'none'; base-uri 'none';
```

Locked down Strict Policy:

```HTTP
script-src 'nonce-r4nd0m';
object-src 'none'; base-uri 'none';
```

- `script-src` directive is used to restrict the sources from which scripts can be loaded and executed.
- `object-src` directive is used to restrict the sources from which objects can be loaded and executed.
- `base-uri` directive specifies the base URL for resolving relative URLs in the page. Without this directive, the page becomes vulnerable to HTML base tag injection attacks.

