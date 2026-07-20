# Testing for Log Out User Interface

Verify the appearance and visibility of the log out functionality in the user interface. For this purpose, view each page from the perspective of a user who has the intention to log out from the web application.

> There are some properties which indicate a good log out user interface:
>
> - A log out button is present on all pages of the web application.
> - The log out button should be identified quickly by a user who wants to log out from the web application.
> - After loading a page the log out button should be visible without scrolling.
> - Ideally the log out button is placed in an area of the page that is fixed in the view port of the browser and not affected by scrolling of the content.
# Testing for Server-Side Session Termination

First, store the values of cookies that are used to identify a session. Invoke the log out function and observe the behavior of the application, especially regarding session cookies. Try to navigate to a page that is only visible in an authenticated session, e.g. by usage of the back button of the browser. If a cached version of the page is displayed, use the reload button to refresh the page from the server. If the log out function causes session cookies to be set to a new value, restore the old value of the session cookies and reload a page from the authenticated area of the application. If these test don't show any vulnerabilities on a particular page, try at least some further pages of the application that are considered as security-critical, to ensure that session termination is recognized properly by these areas of the application.

> No data that should be visible only by authenticated users should be visible on the examined pages while performing the tests. Ideally the application redirects to a public area or a log in form while accessing authenticated areas after termination of the session. It should be not necessary for the security of the application, but setting session cookies to new values after log out is generally considered as good practice.
# Testing for Session Timeout

Try to determine a session timeout by performing requests to a page in the authenticated area of the web application with increasing delays. If the log out behavior appears, the used delay matches approximately the session timeout value.

> The same results as for server-side session termination testing described before are excepted by a log out caused by an inactivity timeout.
>
> The proper value for the session timeout depends on the purpose of the application and should be a balance of security and usability. In a banking applications it makes no sense to keep an inactive session more than 15 minutes. On the other side a short timeout in a wiki or forum could annoy users which are typing lengthy articles with unnecessary log in requests. There timeouts of an hour and more can be acceptable.
# Testing for Session Termination in Single Sign-On Environments (Single Sign-Off)

Perform a log out in the tested application. Verify if there is a central portal or application directory which allows the user to log back in to the application without authentication. Test if the application requests the user to authenticate, if the URL of an entry point to the application is requested. While logged in in the tested application, perform a log out in the SSO system. Then try to access an authenticated area of the tested application.

> It is expected that the invocation of a log out function in a web application connected to a SSO system or in the SSO system itself causes global termination of all sessions. An authentication of the user should be required to gain access to the application after log out in the SSO system and connected application.
