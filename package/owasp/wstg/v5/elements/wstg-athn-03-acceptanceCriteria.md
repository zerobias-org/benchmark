# Lockout Mechanism

To test the strength of lockout mechanisms, you will need access to an account that you are willing or can afford to lock. If you have only one account with which you can log on to the web application, perform this test at the end of your test plan to avoid losing testing time by being locked out.

To evaluate the account lockout mechanism's ability to mitigate brute force password guessing, attempt an invalid log in by using the incorrect password a number of times, before using the correct password to verify that the account was locked out. An example test may be as follows:

1. Attempt to log in with an incorrect password 3 times.
2. Successfully log in with the correct password, thereby showing that the lockout mechanism doesn't trigger after 3 incorrect authentication attempts.
3. Attempt to log in with an incorrect password 4 times.
4. Successfully log in with the correct password, thereby showing that the lockout mechanism doesn't trigger after 4 incorrect authentication attempts.
5. Attempt to log in with an incorrect password 5 times.
6. Attempt to log in with the correct password. The application returns "Your account is locked out.", thereby confirming that the account is locked out after 5 incorrect authentication attempts.
7. Attempt to log in with the correct password 5 minutes later. The application returns "Your account is locked out.", thereby showing that the lockout mechanism does not automatically unlock after 5 minutes.
8. Attempt to log in with the correct password 10 minutes later. The application returns "Your account is locked out.", thereby showing that the lockout mechanism does not automatically unlock after 10 minutes.
9. Successfully log in with the correct password 15 minutes later, thereby showing that the lockout mechanism automatically unlocks after a 10 to 15 minute period.

A CAPTCHA may hinder brute force attacks, but they can come with their own set of weaknesses, and should not replace a lockout mechanism. A CAPTCHA mechanism may be bypassed if implemented incorrectly. CAPTCHA flaws include:

1. Easily defeated challenge, such as arithmetic or limited question set.
2. CAPTCHA checks for HTTP response code instead of response success.
3. CAPTCHA server-side logic defaults to a successful solve.
4. CAPTCHA challenge result is never validated server-side.
5. CAPTCHA input field or parameter is manually processed, and is improperly validated or escaped.

To evaluate CAPTCHA effectiveness:

1. Assess CAPTCHA challenges and attempt automating solutions depending on difficulty.
2. Attempt to submit request without solving CAPTCHA via the normal UI mechanism(s).
3. Attempt to submit request with intentional CAPTCHA challenge failure.
4. Attempt to submit request without solving CAPTCHA (assuming some default values may be passed by client-side code, etc) while using a testing proxy (request submitted directly server-side).
5. Attempt to fuzz CAPTCHA data entry points (if present) with common injection payloads or special characters sequences.
6. Check if the solution to the CAPTCHA might be the alt-text of the image(s), filename(s), or a value in an associated hidden field.
7. Attempt to re-submit previously identified known good responses.
8. Check if clearing cookies causes the CAPTCHA to be bypassed (for example if the CAPTCHA is only shown after a number of failures).
9. If the CAPTCHA is part of a multi-step process, attempt to simply access or complete a step beyond the CAPTCHA (for example if CAPTCHA is the first step in a login process, try simply submitting the second step [username and password]).
10. Check for alternative methods that might not have CAPTCHA enforced, such as an API endpoint meant to facilitate mobile app access.

Repeat this process to every possible functionality that could require a lockout mechanism.

# Unlock Mechanism

To evaluate the unlock mechanism's resistance to unauthorized account unlocking, initiate the unlock mechanism and look for weaknesses. Typical unlock mechanisms may involve secret questions or an emailed unlock link. The unlock link should be a unique one-time link, to stop an attacker from guessing or replaying the link and performing brute force attacks in batches.

Note that an unlock mechanism should only be used for unlocking accounts. It is not the same as a password recovery mechanism, yet could follow the same security practices.

