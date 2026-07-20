Code should be analyzed to determine if a user is permitted to inject content in the CSS context. Particularly, the way in which the website returns CSS rules on the basis of the inputs should be inspected.

The following is a basic example:

```html
<a id="a1">Click me</a>
<b>Hi</b>
<script>
    $("a").click(function(){
        $("b").attr("style","color: " + location.hash.slice(1));
    });
</script>
```

The above code contains a source `location.hash`, controlled by the attacker, that can inject directly in the `style` attribute of an HTML element. As mentioned above, this may lead to different results depending on the browser in use and the supplied payload.

The following pages provide examples of CSS injection vulnerabilities:

- [Password "cracker" via CSS and HTML5](http://html5sec.org/invalid/?length=25)
- [CSS attribute reading](http://eaea.sirdarckcat.net/cssar/v2/)
- [JavaScript based attacks using `CSSStyleDeclaration` with unescaped input](https://github.com/wisec/domxsswiki/wiki/CSS-Text-sink)

For further OWASP resources on preventing CSS injection, see the [Securing Cascading Style Sheets Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Securing_Cascading_Style_Sheets_Cheat_Sheet.html).