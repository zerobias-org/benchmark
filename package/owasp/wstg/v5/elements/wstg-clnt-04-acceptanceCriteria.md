When testers manually check for this type of vulnerability, they first identify if there are client-side redirections implemented in the client-side code. These redirections may be implemented, to give a JavaScript example, using the `window.location` object. This can be used to direct the browser to another page by simply assigning a string to it. This is demonstrated in the following snippet:

```js
var redir = location.hash.substring(1);
if (redir) {
    window.location='http://'+decodeURIComponent(redir);
}
```

In this example, the script does not perform any validation of the variable `redir` which contains the user-supplied input via the query string. Since no form of encoding is applied, this unvalidated input is passed to the `windows.location` object, creating a URL redirection vulnerability.

This implies that an attacker could redirect the victim to a malicious site simply by submitting the following query string:

```text
http://www.victim.site/?#www.malicious.site
```

With a slight modification, the above example snippet can be vulnerable to JavaScript injection.

```js
var redir = location.hash.substring(1);
if (redir) {
    window.location=decodeURIComponent(redir);
}
```

This can be exploited by submitting the following query string:

```text
http://www.victim.site/?#javascript:alert(document.cookie)
```

When testing for this vulnerability, consider that some characters are treated differently by different browsers. For reference, see [DOM-based XSS](https://owasp.org/www-community/attacks/DOM_Based_XSS).