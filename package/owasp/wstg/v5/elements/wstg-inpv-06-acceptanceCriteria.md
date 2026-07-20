# Example 1: Search Filters

Let's suppose we have a web application using a search filter like the following one:

`searchfilter="(cn="+user+")"`

which is instantiated by an HTTP request like this:

`http://www.example.com/ldapsearch?user=John`

If the value `John` is replaced with a `*`, by sending the request:

`http://www.example.com/ldapsearch?user=*`

the filter will look like:

`searchfilter="(cn=*)"`

which matches every object with a 'cn' attribute equals to anything.

If the application is vulnerable to LDAP injection, it will display some or all of the user's attributes, depending on the application's execution flow and the permissions of the LDAP connected user.

A tester could use a trial-and-error approach, by inserting in the parameter `(`, `|`, `&`, `*` and the other characters, in order to check the application for errors.

# Example 2: Login

If a web application uses LDAP to check user credentials during the login process and it is vulnerable to LDAP injection, it is possible to bypass the authentication check by injecting an always true LDAP query (in a similar way to SQL and XPATH injection ).

Let's suppose a web application uses a filter to match LDAP user/password pair.

`searchlogin= "(&(uid="+user+")(userPassword={MD5}"+base64(pack("H*",md5(pass)))+"))";`

By using the following values:

```txt
user=*)(uid=*))(|(uid=*
pass=password
```

the search filter will results in:

`searchlogin="(&(uid=*)(uid=*))(|(uid=*)(userPassword={MD5}X03MO1qnZdYdgyfeuILPmQ==))";`

which is correct and always true. This way, the tester will gain logged-in status as the first user in LDAP tree.

