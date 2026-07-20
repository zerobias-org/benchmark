[DOM-based cross-site scripting](https://owasp.org/www-community/attacks/DOM_Based_XSS) is the de-facto name for [XSS](https://owasp.org/www-community/attacks/xss/) bugs that are the result of active browser-side content on a page, typically JavaScript, obtaining user input through a [source](https://github.com/wisec/domxsswiki/wiki/sources) and using it in a [sink](https://github.com/wisec/domxsswiki/wiki/Sinks), leading to the execution of injected code. This document only discusses JavaScript bugs which lead to XSS.

The DOM, or [Document Object Model](https://en.wikipedia.org/wiki/Document_Object_Model), is the structural format used to represent documents in a browser. The DOM enables dynamic scripts such as JavaScript to reference components of the document such as a form field or a session cookie. The DOM is also used by the browser for security - for example to limit scripts on different domains from obtaining session cookies for other domains. A DOM-based XSS vulnerability may occur when active content, such as a JavaScript function, is modified by a specially crafted request such that a DOM element that can be controlled by an attacker.

Not all XSS bugs require the attacker to control the content returned from the server, but can instead abuse poor JavaScript coding practices to achieve the same results. The consequences are the same as a typical XSS flaw, only the means of delivery is different.

In comparison to other types of cross site scripting vulnerabilities ([reflected and stored](https://owasp.org/www-community/attacks/xss/), where an un-sanitized parameter is passed by the server then returned to the user and executed in the context of the user's browser, a DOM-based XSS vulnerability controls the flow of the code by using elements of the Document Object Model (DOM) along with code crafted by the attacker to change the flow.

Due to their nature, DOM-based XSS vulnerabilities can be executed in many instances without the server being able to determine what is actually being executed. This may make many of the general XSS filtering and detection techniques impotent to such attacks.

This hypothetical example uses the following client-side code:

```html
<script>
document.write("Site is at: " + document.location.href + ".");
</script>
```

An attacker may append `#<script>alert('xss')</script>` to the affected page URL which would, when executed, display the alert box. In this instance, the appended code would not be sent to the server as everything after the `#` character is not treated as part of the query by the browser, but as a fragment. In this example, the code is immediately executed and an alert of "xss" is displayed by the page. Unlike the more common types of cross site scripting ([reflected and stored](https://owasp.org/www-community/attacks/xss/) in which the code is sent to the server and then back to the browser, this is executed directly in the user's browser without server contact.

The [consequences](https://owasp.org/www-community/attacks/xss/) of DOM-based XSS flaws are as wide ranging as those seen in more well known forms of XSS, including cookie retrieval, further malicious script injection, etc., and should therefore be treated with the same severity.

