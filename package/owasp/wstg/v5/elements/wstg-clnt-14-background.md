[Reverse Tabnabbing](https://owasp.org/www-community/attacks/Reverse_Tabnabbing) is an attack which can be used to redirect users to phishing pages. This usually becomes possible due to the `target` attribute of the `<a>` tag being set to `_blank` which causes the link to be opened in a new tab. When the attribute `rel='noopener noreferrer'` is not used in the same `<a>` tag, the newly opened page can influence the original page and redirect it to a domain controlled by the attacker.

Since the user was on the original domain when the new tab opened, they are less likely to notice that the page has changed, especially if the phishing page is identical to the original domain. Any credentials entered on the attacker-controlled domain will thus end up in the attacker's possession.

Links opened via the `window.open` JavaScript function are also vulnerable to this attack.

_NOTE: This is a legacy issue that does not affect [modern browsers](https://caniuse.com/mdn-html_elements_a_implicit_noopener). Older versions of popular browsers (For example, versions prior to Google Chrome 88) as well as Internet Explorer are vulnerable to this attack._

# Example

Imagine a web application where users are allowed to insert a URL in their profile. If the application is vulnerable to reverse tabnabbing, a malicious user will be able to provide a link to a page that has the following code:

```html
<html>
 <body>
  <script>
    window.opener.location = "https://example.org";
  </script>
<b>Error loading...</b>
 </body>
</html>
```

Clicking on the link will open up a new tab while the original tab will redirect to "example.org". Suppose "example.org" looks similar to the vulnerable web application, the user is less likely to notice the change and is more likely to enter sensitive information on the page.

