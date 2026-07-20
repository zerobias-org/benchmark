To test for exploitable SSI, inject SSI directives as user input. If SSI are enabled and user input validation has not been properly implemented, the server will execute the directive. This is very similar to a classical scripting language injection vulnerability in that it occurs when user input is not properly validated and sanitized.

First determine if the web server supports SSI directives. Often, the answer is yes, as SSI support is quite common. To determine if SSI directives are supported, discover the type of web server that the target is running using information gathering techniques (see [Fingerprint Web Server](../01-Information_Gathering/02-Fingerprint_Web_Server.md)). If you have access to the code, determine if SSI directives are used by searching through the webserver configuration files for specific keywords.

Another way of verifying that SSI directives are enabled is by checking for pages with the `.shtml` extension, which is associated with SSI directives. The use of the `.shtml` extension is not mandatory, so not having found any `.shtml` files doesn't necessarily mean that the target is not vulnerable to SSI injection attacks.

The next step is determining all the possible user input vectors and testing to see if the SSI injection is exploitable.

First find all the pages where user input is allowed. Possible input vectors may also include headers and cookies. Determine how the input is stored and used, i.e if the input is returned as an error message or page element and if it was modified in some way. Access to the source code can help you to more easily determine where the input vectors are and how input is handled.

Once you have a list of potential injection points, you may determine if the input is correctly validated. Ensure it is possible to inject characters used in SSI directives such as `<!#=/."->` and `[a-zA-Z0-9]`

The below example returns the value of the variable. The [references](#references) section has helpful links with server-specific documentation to help you better assess a particular system.

```html
<!--#echo var="VAR" -->
```

When using the `include` directive, if the supplied file is a CGI script, this directive will include the output of the CGI script. This directive may also be used to include the content of a file or list files in a directory:

```html
<!--#include virtual="FILENAME" -->
```

To return the output of a system command:

```html
<!--#exec cmd="OS_COMMAND" -->
```

If the application is vulnerable, the directive is injected and it would be interpreted by the server the next time the page is served.

The SSI directives can also be injected in the HTTP headers, if the web application is using that data to build a dynamically generated page:

```text
GET / HTTP/1.1
Host: www.example.com
Referer: <!--#exec cmd="/bin/ps ax"-->
User-Agent: <!--#include virtual="/proc/version"-->
```

