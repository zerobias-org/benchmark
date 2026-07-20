The testing strategy is targeted at network attackers, hence it only needs to be applied to sites without full HSTS adoption (sites with full HSTS adoption are secure, since their cookies are not communicated over HTTP). We assume to have two testing accounts on the site under test, one to act as the victim and one to act as the attacker. We simulate a scenario where the attacker steals all the cookies which are not protected against disclosure over HTTP, and presents them to the site to access the victim's account. If these cookies are enough to act on the victim's behalf, session hijacking is possible.

Here are the steps for executing this test:

1. Login to the site as the victim and reach any page offering a secure function requiring authentication.
2. Delete from the cookie jar all the cookies which satisfy any of the following conditions.
    - in case there is no HSTS adoption: the `Secure` attribute is set.
    - in case there is partial HSTS adoption: the `Secure` attribute is set or the `Domain` attribute is not set.
3. Save a snapshot of the cookie jar.
4. Trigger the secure function identified at step 1.
5. Observe whether the operation at step 4 has been performed successfully. If so, the attack was successful.
6. Clear the cookie jar, login as the attacker and reach the page at step 1.
7. Write in the cookie jar, one by one, the cookies saved at step 3.
8. Trigger again the secure function identified at step 1.
9. Clear the cookie jar and login again as the victim.
10. Observe whether the operation at step 8 has been performed successfully in the victim's account. If so, the attack was successful; otherwise, the site is secure against session hijacking.

We recommend using two different machines or browsers for the victim and the attacker. This allows you to decrease the number of false positives if the web application does fingerprinting to verify access enabled from a given cookie. A shorter but less precise variant of the testing strategy only requires one testing account. It follows the same pattern, but it halts at step 5 (note that this makes step 3 useless).

