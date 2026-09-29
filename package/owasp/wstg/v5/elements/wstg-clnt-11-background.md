- Assess the security of the message's origin.
- Validate that it's using safe methods and validating its input.

Web Messaging (also known as [Cross Document Messaging](https://html.spec.whatwg.org/multipage/web-messaging.html#web-messaging)) allows applications running on different domains to communicate in a secure manner. Before the introduction of web messaging, the communication of different origins (between iframes, tabs and windows) was restricted by the same origin policy and enforced by the browser. Developers used multiple hacks in order to accomplish these tasks, and most of them were mainly insecure.

This restriction within the browser is in place to prevent a malicious website from reading confidential data from other iframes, tabs, etc; however, there are some legitimate cases where two trusted websites need to exchange data with each other. To meet this need, Cross Document Messaging was introduced in the [WHATWG HTML5](https://html.spec.whatwg.org/multipage/) draft specification and was implemented in all major browsers. It enables secure communications between multiple origins across iframes, tabs and windows.

The messaging API introduced the [`postMessage()` method](https://developer.mozilla.org/en-US/docs/Web/API/Window/postMessage), with which plain-text messages can be sent cross-origin. It consists of two parameters: message, and domain.

There are some security concerns when using `*` as the domain that we discuss below. In order to receive messages, the receiving website needs to add a new event handler, which has the following attributes:

- Data, the content of the incoming message;
- Origin of the sender document; and
- Source, the source window.

Here is an example of the messaging API in use. To send a message:

```js
iframe1.contentWindow.postMessage("Hello world","http://www.example.com");
```

To receive a message:

```js
window.addEventListener("message", handler, true);
function handler(event) {
    if(event.origin === 'chat.example.com') {
        /* process message (event.data) */
    } else {
        /* ignore messages from untrusted domains */
    }
}
```

# Origin Security

The origin is made up of a scheme, host name, and port. It uniquely identifies the domain sending or receiving the message, and does not include the path or the fragment part of the URL. For instance, `https://example.com` will be considered different from `http://example.com` because the schema of the former is `https`, while the latter is `http`. This also applies to web servers running in the same domain but on different ports.

