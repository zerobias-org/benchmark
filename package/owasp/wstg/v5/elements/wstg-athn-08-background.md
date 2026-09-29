- Determine the complexity and how straight-forward the questions are.
- Assess possible user answers and brute force capabilities.

Often called "secret" questions and answers, security questions and answers are often used to recover forgotten passwords (see [Testing for weak password change or reset functionalities](09-Testing_for_Weak_Password_Change_or_Reset_Functionalities.md), or as extra security on top of the password.

They are typically generated upon account creation and require the user to select from some pre-generated questions and supply an appropriate answer. They may allow the user to generate their own question and answer pairs. Both methods are prone to insecurities. Ideally, security questions should generate answers that are only known by the user, and not guessable or discoverable by anybody else. This is harder than it sounds.
Security questions and answers rely on the secrecy of the answer. Questions and answers should be chosen so that the answers are only known by the account holder. However, although a lot of answers may not be publicly known, most of the questions that sites implement promote answers that are pseudo-private.

# Pre-generated Questions

The majority of pre-generated questions are fairly simplistic in nature and can lead to insecure answers. For example:

- The answers may be known to family members or close friends of the user, e.g. "What is your mother's maiden name?", "What is your date of birth?"
- The answers may be easily guessable, e.g. "What is your favorite color?", "What is your favorite baseball team?"
- The answers may be brute forcible, e.g. "What is the first name of your favorite high school teacher?" - the answer is probably on some easily downloadable lists of popular first names, and therefore a simple brute force attack can be scripted.
- The answers may be publicly discoverable, e.g. "What is your favorite movie?" - the answer may easily be found on the user's social media profile page.

# Self-generated Questions

The problem with having users to generate their own questions is that it allows them to generate very insecure questions, or even bypass the whole point of having a security question in the first place. Here are some real world examples that illustrate this point:

- "What is 1+1?"
- "What is your username?"
- "My password is S3curIty!"

