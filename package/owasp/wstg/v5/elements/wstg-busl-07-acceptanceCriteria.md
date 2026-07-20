This test is unusual in that the result can be drawn from all the other tests performed against the web application. While performing all the other tests, take note of measures that might indicate the application has in-built self-defense:

- Changed responses
- Blocked requests
- Actions that log a user out or lock their account

These may only be localized. Common localized (per function) defenses are:

- Rejecting input containing certain characters
- Locking out an account temporarily after a number of authentication failures

Localized security controls are not sufficient. There are often no defenses against general mis-use such as:

- Forced browsing
- Bypassing presentation layer input validation
- Multiple access control errors
- Additional, duplicated or missing parameter names
- Multiple input validation or business logic verification failures with values that cannot be the result of user mistakes or typos
- Structured data (e.g. JSON, XML) of an invalid format is received
- Blatant cross-site scripting or SQL injection payloads are received
- Utilizing the application faster than would be possible without automation tools
- Change in continental geo-location of a user
- Change of user agent
- Accessing a multi-stage business process in the wrong order
- Large number of, or high rate of use of, application-specific functionality (e.g. voucher code submission, failed credit card payments, file uploads, file downloads, log outs, etc).

These defenses work best in authenticated parts of the application, although rate of creation of new accounts or accessing content (e.g. to scrape information) can be of use in public areas.

Not all the above need to be monitored by the application, but there is a problem if none of them are. By testing the web application, doing the above type of actions, was any response taken against the tester? If not, the tester should report that the application appears to have no application-wide active defenses against misuse. Note it is sometimes possible that all responses to attack detection are silent to the user (e.g. logging changes, increased monitoring, alerts to administrators and and request proxying), so confidence in this finding cannot be guaranteed. In practice, very few applications (or related infrastructure such as a web application firewall) are detecting these types of misuse.

