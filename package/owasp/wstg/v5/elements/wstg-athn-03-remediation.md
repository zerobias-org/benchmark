Apply account unlock mechanisms depending on the risk level. In order from lowest to highest assurance:

1. Time-based lockout and unlock.
2. Self-service unlock (sends unlock email to registered email address).
3. Manual administrator unlock.
4. Manual administrator unlock with positive user identification.

Factors to consider when implementing an account lockout mechanism:

1. What is the risk of brute force password guessing against the application?
2. Is a CAPTCHA sufficient to mitigate this risk?
3. Is a client-side lockout mechanism being used (e.g., JavaScript)? (If so, disable the client-side code to test.)
4. Number of unsuccessful log in attempts before lockout. If the lockout threshold is too low then valid users may be locked out too often. If the lockout threshold is too high then the more attempts an attacker can make to brute force the account before it will be locked. Depending on the application's purpose, a range of 5 to 10 unsuccessful attempts is a typical lockout threshold.
5. How will accounts be unlocked?
    1. Manually by an administrator: this is the most secure lockout method, but may cause inconvenience to users and take up the administrator's "valuable" time.
        1. Note that the administrator should also have a recovery method in case his account gets locked.
        2. This unlock mechanism may lead to a denial-of-service attack if an attacker's goal is to lock the accounts of all users of the web application.
    2. After a period of time: What is the lockout duration? Is this sufficient for the application being protected? E.g. a 5 to 30 minute lockout duration may be a good compromise between mitigating brute force attacks and inconveniencing valid users.
    3. Via a self-service mechanism: As stated before, this self-service mechanism must be secure enough to avoid that the attacker can unlock accounts himself.

