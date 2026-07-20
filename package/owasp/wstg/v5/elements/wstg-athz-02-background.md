This kind of test focuses on verifying how the authorization schema has been implemented for each role or privilege to get access to reserved functions and resources.

For every specific role the tester holds during the assessment and for every function and request that the application executes during the post-authentication phase, it is necessary to verify:

- Is it possible to access that resource even if the user is not authenticated?
- Is it possible to access that resource after the log-out?
- Is it possible to access functions and resources that should be accessible to a user that holds a different role or privilege?

Try to access the application as an administrative user and track all the administrative functions.

- Is it possible to access administrative functions if the tester is logged in as a non-admin user?
- Is it possible to use these administrative functions as a user with a different role and for whom that action should be denied?

