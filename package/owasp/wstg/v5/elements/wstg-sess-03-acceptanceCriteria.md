In this section we give an explanation of the testing strategy that will be shown in the next section.

The first step is to make a request to the site to be tested (*e.g.* `www.example.com`). If the tester requests the following:

```http
GET / HTTP/1.1
Host: www.example.com
```

They will obtain the following response:

```html
HTTP/1.1 200 OK
Date: Wed, 14 Aug 2008 08:45:11 GMT
Server: IBM_HTTP_Server
Set-Cookie: JSESSIONID=0000d8eyYq3L0z2fgq10m4v-rt4:-1; Path=/; secure
Cache-Control: no-cache="set-cookie,set-cookie2"
Expires: Thu, 01 Dec 1994 16:00:00 GMT
Keep-Alive: timeout=5, max=100
Connection: Keep-Alive
Content-Type: text/html;charset=Cp1254
Content-Language: en-US
```

The application sets a new session identifier, `JSESSIONID=0000d8eyYq3L0z2fgq10m4v-rt4:-1`, for the client.

Next, if the tester successfully authenticates to the application with the following POST to `https://www.example.com/authentication.php`:

```http
POST /authentication.php HTTP/1.1
Host: www.example.com
[...]
Referer: http://www.example.com
Cookie: JSESSIONID=0000d8eyYq3L0z2fgq10m4v-rt4:-1
Content-Type: application/x-www-form-urlencoded
Content-length: 57

Name=Meucci&wpPassword=secret!&wpLoginattempt=Log+in
```

The tester observes the following response from the server:

```http
HTTP/1.1 200 OK
Date: Thu, 14 Aug 2008 14:52:58 GMT
Server: Apache/2.2.2 (Fedora)
X-Powered-By: PHP/5.1.6
Content-language: en
Cache-Control: private, must-revalidate, max-age=0
X-Content-Encoding: gzip
Content-length: 4090
Connection: close
Content-Type: text/html; charset=UTF-8
...
HTML data
...
```

As no new cookie has been issued upon a successful authentication, the tester knows that it is possible to perform session hijacking unless the integrity of the session cookie is ensured.

The tester can send a valid session identifier to a user (possibly using a social engineering trick), wait for them to authenticate, and subsequently verify that privileges have been assigned to this cookie.

# Test with Forced Cookies

This testing strategy is targeted at network attackers, hence it only needs to be applied to sites without full HSTS adoption (sites with full HSTS adoption are secure, since all their cookies have integrity). We assume to have two testing accounts on the site under test, one to act as the victim and one to act as the attacker. We simulate a scenario where the attacker forces in the victim's browser all the cookies which are not freshly issued after login and do not have integrity. After the victim's login, the attacker presents the forced cookies to the site to access the victim's account: if they are enough to act on the victim's behalf, session fixation is possible.

Here are the steps for executing this test:

1. Reach the login page of the site.
2. Save a snapshot of the cookie jar before logging in, excluding cookies which contain the `__Host-` or `__Secure-` prefix in their name.
3. Login to the site as the victim and reach any page offering a secure function requiring authentication.
4. Set the cookie jar to the snapshot taken at step 2.
5. Trigger the secure function identified at step 3.
6. Observe whether the operation at step 5 has been performed successfully. If so, the attack was successful.
7. Clear the cookie jar, login as the attacker and reach the page at step 3.
8. Write in the cookie jar, one by one, the cookies saved at step 2.
9. Trigger again the secure function identified at step 3.
10. Clear the cookie jar and login again as the victim.
11. Observe whether the operation at step 9 has been performed successfully in the victim's account. If so, the attack was successful; otherwise, the site is secure against session fixation.

We recommend using two different machines or browsers for the victim and the attacker. This allows you to decrease the number of false positives if the web application does fingerprinting to verify access enabled from a given cookie. A shorter but less precise variant of the testing strategy only requires one testing account. It follows the same steps, but it halts at step 6.

