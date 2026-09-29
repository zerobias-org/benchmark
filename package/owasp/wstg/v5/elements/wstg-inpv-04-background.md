- Identify the backend and the parsing method used.
- Assess injection points and try bypassing input filters using HPP.

HTTP Parameter Pollution tests the applications response to receiving multiple HTTP parameters with the same name; for example, if the parameter `username` is included in the GET or POST parameters twice.

Supplying multiple HTTP parameters with the same name may cause an application to interpret values in unanticipated ways. By exploiting these effects, an attacker may be able to bypass input validation, trigger application errors or modify internal variables values. As HTTP Parameter Pollution (in short *HPP*) affects a building block of all web technologies, server and client-side attacks exist.

Current HTTP standards do not include guidance on how to interpret multiple input parameters with the same name. For instance, [RFC 3986](https://www.ietf.org/rfc/rfc3986.txt) simply defines the term *Query String* as a series of field-value pairs and [RFC 2396](https://www.ietf.org/rfc/rfc2396.txt) defines classes of reversed and unreserved query string characters. Without a standard in place, web application components handle this edge case in a variety of ways (see the table below for details).

By itself, this is not necessarily an indication of vulnerability. However, if the developer is not aware of the problem, the presence of duplicated parameters may produce an anomalous behavior in the application that can be potentially exploited by an attacker. As often in security, unexpected behaviors are a usual source of weaknesses that could lead to HTTP Parameter Pollution attacks in this case. To better introduce this class of vulnerabilities and the outcome of HPP attacks, it is interesting to analyze some real-life examples that have been discovered in the past.

# Input Validation and Filters Bypass

In 2009, immediately after the publication of the first research on HTTP Parameter Pollution, the technique received attention from the security community as a possible way to bypass web application firewalls.

One of these flaws, affecting *ModSecurity SQL Injection Core Rules*, represents a perfect example of the impedance mismatch between applications and filters. The ModSecurity filter would correctly apply a deny list for the following string: `select 1,2,3 from table`, thus blocking this example URL from being processed by the web server: `/index.aspx?page=select 1,2,3 from table`. However, by exploiting the concatenation of multiple HTTP parameters, an attacker could cause the application server to concatenate the string after the ModSecurity filter already accepted the input. As an example, the URL `/index.aspx?page=select 1&page=2,3` from table would not trigger the ModSecurity filter, yet the application layer would concatenate the input back into the full malicious string.

Another HPP vulnerability turned out to affect *Apple Cups*, the well-known printing system used by many UNIX systems. Exploiting HPP, an attacker could easily trigger a Cross-Site Scripting vulnerability using the following URL: `http://127.0.0.1:631/admin/?kerberos=onmouseover=alert(1)&kerberos`. The application validation checkpoint could be bypassed by adding an extra `kerberos` argument having a valid string (e.g. empty string). As the validation checkpoint would only consider the second occurrence, the first `kerberos` parameter was not properly sanitized before being used to generate dynamic HTML content. Successful exploitation would result in JavaScript code execution under the context of the hosting site.

# Authentication Bypass

An even more critical HPP vulnerability was discovered in *Blogger*, the popular blogging platform. The bug allowed malicious users to take ownership of the victim’s blog by using the following HTTP request (`https://www.blogger.com/add-authors.do`):

```html
POST /add-authors.do HTTP/1.1
[...]

security_token=attackertoken&blogID=attackerblogidvalue&blogID=victimblogidvalue&authorsList=goldshlager19test%40gmail.com(attacker email)&ok=Invite
```

The flaw resided in the authentication mechanism used by the web application, as the security check was performed on the first `blogID` parameter, whereas the actual operation used the second occurrence.

# Expected Behavior by Application Server

The following table illustrates how different web technologies behave in presence of multiple occurrences of the same HTTP parameter.

Given the URL and querystring: `http://example.com/?color=red&color=blue`

  | Web Application Server Backend | Parsing Result | Example |
  |--------------------------------|----------------|--------|
  | ASP.NET / IIS | All occurrences concatenated with a comma |  color=red,blue |
  | ASP / IIS     | All occurrences concatenated with a comma | color=red,blue |
  | .NET Core 3.1 / Kestrel | All occurrences concatenated with a comma | color=red,blue |
  | .NET 5 / Kestrel | All occurrences concatenated with a comma | color=red,blue |
  | PHP / Apache  | Last occurrence only | color=blue |
  | PHP / Zeus | Last occurrence only | color=blue |
  | JSP, Servlet / Apache Tomcat | First occurrence only | color=red |
  | JSP, Servlet / Oracle Application Server 10g | First occurrence only | color=red |
  | JSP, Servlet / Jetty  | First occurrence only | color=red |
  | IBM Lotus Domino | Last occurrence only | color=blue |
  | IBM HTTP Server | First occurrence only | color=red |
  | Node.js / express | First occurrence only | color=red |
  | mod_perl, libapreq2 / Apache | First occurrence only | color=red |
  | Perl CGI / Apache | First occurrence only | color=red |
  | mod_wsgi (Python) / Apache | First occurrence only | color=red |
  | Python / Zope | All occurrences in List data type | color=['red','blue'] |

(source: [Appsec EU 2009 Carettoni & Paola](https://owasp.org/www-pdf-archive/AppsecEU09_CarettoniDiPaola_v0.8.pdf))

