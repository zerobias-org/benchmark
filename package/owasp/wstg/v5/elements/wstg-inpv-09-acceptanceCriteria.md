The [XPath attack pattern was first published by Amit Klein](http://dl.packetstormsecurity.net/papers/bypass/Blind_XPath_Injection_20040518.pdf) and is very similar to the usual SQL Injection. In order to get a first grasp of the problem, let's imagine a login page that manages the authentication to an application in which the user must enter their username and password. Let's assume that our database is represented by the following XML file:

```xml
<?xml version="1.0" encoding="ISO-8859-1"?>
<users>
    <user>
        <username>gandalf</username>
        <password>!c3</password>
        <account>admin</account>
    </user>
    <user>
        <username>Stefan0</username>
        <password>w1s3c</password>
        <account>guest</account>
    </user>
    <user>
        <username>tony</username>
        <password>Un6R34kb!e</password>
        <account>guest</account>
    </user>
</users>
```

An XPath query that returns the account whose username is `gandalf` and the password is `!c3` would be the following:

`string(//user[username/text()='gandalf' and password/text()='!c3']/account/text())`

If the application does not properly filter user input, the tester will be able to inject XPath code and interfere with the query result. For instance, the tester could input the following values:

```text
Username: ' or '1' = '1
Password: ' or '1' = '1
```

Looks quite familiar, doesn't it? Using these parameters, the query becomes:

`string(//user[username/text()='' or '1' = '1' and password/text()='' or '1' = '1']/account/text())`

As in a common SQL Injection attack, we have created a query that always evaluates to true, which means that the application will authenticate the user even if a username or a password have not been provided. And as in a common SQL Injection attack, with XPath injection, the first step is to insert a single quote (`'`) in the field to be tested, introducing a syntax error in the query, and to check whether the application returns an error message.

If there is no knowledge about the XML data internal details and if the application does not provide useful error messages that help us reconstruct its internal logic, it is possible to perform a [Blind XPath Injection](https://owasp.org/www-community/attacks/Blind_XPath_Injection) attack, whose goal is to reconstruct the whole data structure. The technique is similar to inference based SQL Injection, as the approach is to inject code that creates a query that returns one bit of information. [Blind XPath Injection](https://owasp.org/www-community/attacks/Blind_XPath_Injection) is explained in more detail by Amit Klein in the referenced paper.

