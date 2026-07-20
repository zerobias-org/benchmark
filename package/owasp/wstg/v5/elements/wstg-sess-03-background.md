Session fixation is enabled by the insecure practice of preserving the same value of the session cookies before and after authentication. This typically happens when session cookies are used to store state information even before login, e.g., to add items to a shopping cart before authenticating for payment.

In the generic exploit of session fixation vulnerabilities, an attacker can obtain a set of session cookies from the target site without first authenticating. The attacker can then force these cookies into the victim's browser using different techniques. If the victim later authenticates at the target site and the cookies are not refreshed upon login, the victim will be identified by the session cookies chosen by the attacker. The attacker is then able to impersonate the victim with these known cookies.

This issue can be fixed by refreshing the session cookies after the authentication process. Alternatively, the attack can be prevented by ensuring the integrity of session cookies. When considering network attackers, i.e., attackers who control the network used by the victim, use full [HSTS](https://en.wikipedia.org/wiki/HTTP_Strict_Transport_Security) or add the`__Host-` / `__Secure-` prefix to the cookie name.

Full HSTS adoption occurs when a host activates HSTS for itself and all its sub-domains. This is described in a paper called *Testing for Integrity Flaws in Web Sessions* by Stefano Calzavara, Alvise Rabitti, Alessio Ragazzo, and Michele Bugliesi.

