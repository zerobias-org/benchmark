This section describes how to check for client-side URL redirection, also known as open redirection. It is an input validation flaw that exists when an application accepts user-controlled input that specifies a link which leads to an external URL that could be malicious. This kind of vulnerability could be used to accomplish a phishing attack or redirect a victim to an infection page.

This vulnerability occurs when an application accepts untrusted input that contains a URL value and does not sanitize it. This URL value could cause the web application to redirect the user to another page, such as a malicious page controlled by the attacker.

This vulnerability may enable an attacker to successfully launch a phishing scam and steal user credentials. Since the redirection is originated by the real application, the phishing attempts may have a more trustworthy appearance.

Here is an example of a phishing attack URL.

```text
http://www.target.site?#redirect=www.fake-target.site
```

The victim that visits this URL will be automatically redirected to `fake-target.site`, where an attacker could place a fake page that resembles the intended site, in order to steal the victim's credentials.

Open redirection could also be used to craft a URL that would bypass the application’s access control checks and forward the attacker to privileged functions that they would normally not be able to access.

