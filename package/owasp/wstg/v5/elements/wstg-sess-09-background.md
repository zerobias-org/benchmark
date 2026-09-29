- Identify vulnerable session cookies.
- Hijack vulnerable cookies and assess the risk level.

An attacker who gets access to user session cookies can impersonate them by presenting such cookies. This attack is known as session hijacking. When considering network attackers, i.e., attackers who control the network used by the victim, session cookies can be unduly exposed to the attacker over HTTP. To prevent this, session cookies should be marked with the `Secure` attribute so that they are only communicated over HTTPS.

Note that the `Secure` attribute should also be used when the web application is entirely deployed over HTTPS, otherwise the following cookie theft attack is possible. Assume that `example.com` is entirely deployed over HTTPS, but does not mark its session cookies as `Secure`. The following attack steps are possible:

1. The victim sends a request to `http://another-site.com`.
2. The attacker corrupts the corresponding response so that it triggers a request to `http://example.com`.
3. The browser now tries to access `http://example.com`.
4. Though the request fails, the session cookies are leaked in the clear over HTTP.

Alternatively, session hijacking can be prevented by banning use of HTTP using [HSTS](https://en.wikipedia.org/wiki/HTTP_Strict_Transport_Security). Note that there is a subtlety here related to cookie scoping. In particular, full HSTS adoption is required when session cookies are issued with the `Domain` attribute set.

Full HSTS adoption is described in a paper called *Testing for Integrity Flaws in Web Sessions* by Stefano Calzavara, Alvise Rabitti, Alessio Ragazzo, and Michele Bugliesi. Full HSTS adoption occurs when a host activates HSTS for itself and all its sub-domains. Partial HSTS adoption is when a host activates HSTS just for itself.

With the `Domain` attribute set, session cookies can be shared across sub-domains. Use of HTTP with sub-domains should be avoided to prevent the disclosure of unencrypted cookies sent over HTTP. To exemplify this security flaw, assume that the site `example.com` activates HSTS without the `includeSubDomains` option. The site issues session cookies with the `Domain` attribute set to `example.com`. The following attack is possible:

1. The victim sends a request to `http://another-site.com`.
2. The attacker corrupts the corresponding response so that it triggers a request to `http://fake.example.com`.
3. The browser now tries to access `http://fake.example.com`, which is permitted by the HSTS configuration.
4. Since the request is sent to a sub-domain of `example.com` with the `Domain` attribute set, it includes the session cookies, which are leaked in the clear over HTTP.

Full HSTS should be activated on the apex domain to prevent this attack.

