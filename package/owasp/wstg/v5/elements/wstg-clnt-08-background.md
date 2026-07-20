ActionScript, based on ECMAScript, is the language used by Flash applications when dealing with interactive needs. There are three versions of the ActionScript language. ActionScript 1.0 and ActionScript 2.0 are very similar with ActionScript 2.0 being an extension of ActionScript 1.0. ActionScript 3.0, introduced with Flash Player 9, is a rewrite of the language to support object orientated design.

ActionScript, like every other language, has some implementation patterns which could lead to security issues. In particular, since Flash applications are often embedded in browsers, vulnerabilities like DOM-based Cross Site Scripting (DOM XSS) could be present in flawed Flash applications.

Cross-Site Flashing (XSF) is a vulnerability that has a similar impact to XSS.

XSF occurs when the following scenarios are initiated from different domains:

- One movie loads another movie with `loadMovie*` functions (or other hacks) and has access to the same sandbox, or part of it.
- An HTML page uses JavaScript to command an Adobe Flash movie, for example, by calling:
    - `GetVariable` to access Flash public and static objects from JavaScript as a string.
    - `SetVariable` to set a static or public Flash object to a new string value with JavaScript.
- Unexpected communications between the browser and SWF application, which could result in stealing data from the SWF application.

XSF may be performed by forcing a flawed SWF to load an external evil Flash file. This attack could result in XSS or in the modification of the GUI in order to fool a user to insert credentials on a fake Flash form. XSF could be used in the presence of Flash HTML Injection or external SWF files when `loadMovie*` methods are used.

# Open Redirectors

SWFs have the capability to navigate the browser. If the SWF takes the destination in as a FlashVar, then the SWF may be used as an open redirector. An open redirector is any piece of website functionality on a trusted website that an attacker can use to redirect the end user to a malicious website. These are frequently used within phishing attacks. Similar to cross-site scripting, the attack involves a user clicking on a malicious link.

In the Flash case, the malicious URL might look like:

```text
http://trusted.example.org/trusted.swf?getURLValue=http://www.evil-spoofing-website.org/phishEndUsers.html
```

In the above example, an end user might see that the URL begins with their favorite trusted website and click on it. The link would load the trusted SWF which takes the `getURLValue` and provides it to an ActionScript browser navigation call:

```actionscript
getURL(_root.getURLValue,"_self");
```

This would navigate the browser to the malicious URL provided by the attacker. At this point, the phisher has successfully leveraged the trust the user has in trusted.example.org to trick the user into visiting their malicious website. From there, they could launch a 0-day, conduct spoofing of the original website, or any other type of attack. SWFs may unintentionally be acting as an open-redirector on the website.

Developers should avoid taking full URLs as FlashVars. If they only plan to navigate within their own website, then they should use relative URLs or verify that the URL begins with a trusted domain and protocol.

# Attacks and Flash Player Version

Since May 2007, three new versions of Flash Player were released by Adobe. Every new version restricts some of the attacks previously described.

| Player Version | `asfunction` | ExternalInterface | GetURL | HTML Injection |
|----------------|--------------|-------------------|--------|----------------|
| v9.0 r47/48    |  Yes         |   Yes             | Yes    |     Yes        |
| v9.0 r115      |  No          |   Yes             | Yes    |     Yes        |
| v9.0 r124      |  No          |   Yes             | Yes    |     Partially  |

