- Identify SSI injection points.
- Assess the severity of the injection.

Web servers usually give developers the ability to add small pieces of dynamic code inside static HTML pages, without having to deal with full-fledged server-side or client-side languages. This feature is provided by [Server-Side Includes](https://owasp.org/www-community/attacks/Server-Side_Includes_%28SSI%29_Injection)(SSI).

Server-Side Includes are directives that the web server parses before serving the page to the user. They represent an alternative to writing CGI programs or embedding code using server-side scripting languages, when there's only need to perform very simple tasks. Common SSI implementations provide directives (commands) to include external files, to set and print web server CGI environment variables, or to execute external CGI scripts or system commands.

SSI can lead to a Remote Command Execution (RCE), however most webservers have the `exec` directive disabled by default.

This is a vulnerability very similar to a classical scripting language injection vulnerability. One mitigation is that the web server needs to be configured to allow SSI. On the other hand, SSI injection vulnerabilities are often simpler to exploit, since SSI directives are easy to understand and, at the same time, quite powerful, e.g., they can output the content of files and execute system commands.

