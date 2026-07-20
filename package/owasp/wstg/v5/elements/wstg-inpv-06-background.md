The Lightweight Directory Access Protocol (LDAP) is used to store information about users, hosts, and many other objects. [LDAP injection](https://wiki.owasp.org/index.php/LDAP_injection) is a server-side attack, which could allow sensitive information about users and hosts represented in an LDAP structure to be disclosed, modified, or inserted. This is done by manipulating input parameters afterwards passed to internal search, add, and modify functions.

A web application could use LDAP in order to let users authenticate or search other users' information inside a corporate structure. The goal of LDAP injection attacks is to inject LDAP search filters metacharacters in a query which will be executed by the application.

[Rfc2254](https://www.ietf.org/rfc/rfc2254.txt) defines a grammar on how to build a search filter on LDAPv3 and extends [Rfc1960](https://www.ietf.org/rfc/rfc1960.txt) (LDAPv2).

An LDAP search filter is constructed in Polish notation, also known as [Polish notation prefix notation](https://en.wikipedia.org/wiki/Polish_notation).

This means that a pseudo code condition on a search filter like this:

`find("cn=John & userPassword=mypass")`

will be represented as:

`find("(&(cn=John)(userPassword=mypass))")`

Boolean conditions and group aggregations on an LDAP search filter could be applied by using the following metacharacters:

| Metachar |  Meaning              |
|----------|-----------------------|
| &        |  Boolean AND          |
| \|       |  Boolean OR           |
| !        |  Boolean NOT          |
| =        |  Equals               |
| ~=       |  Approx               |
| >=       |  Greater than         |
| <=       |  Less than            |
| *        |  Any character        |
| ()       |  Grouping parenthesis |

More complete examples on how to build a search filter can be found in the related RFC.

A successful exploitation of an LDAP injection vulnerability could allow the tester to:

- Access unauthorized content
- Evade application restrictions
- Gather unauthorized information
- Add or modify Objects inside LDAP tree structure

