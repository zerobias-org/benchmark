Consider the following: [DOM XSS exercise](http://www.domxss.com/domxss/01_Basics/04_eval.html)

The page contains the following script:

```html
<script>
function loadObj(){
    var cc=eval('('+aMess+')');
    document.getElementById('mess').textContent=cc.message;
}

if(window.location.hash.indexOf('message')==-1) {
    var aMess='({"message":"Hello User!"})';
} else {
    var aMess=location.hash.substr(window.location.hash.indexOf('message=')+8)
}
</script>
```

The above code contains a source `location.hash` that is controlled by the attacker that can inject directly in the `message` value a JavaScript Code to take the control of the user browser.