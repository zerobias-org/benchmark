Verify that the identity requirements for user registration are aligned with business and security requirements:

1. Can anyone register for access?
2. Are registrations vetted by a human prior to provisioning, or are they automatically granted if the criteria are met?
3. Can the same person or identity register multiple times?
4. Can users register for different roles or permissions?
5. What proof of identity is required for a registration to be successful?
6. Are registered identities verified?

Validate the registration process:

1. Can identity information be easily forged or faked?
2. Can the exchange of identity information be manipulated during registration?

# Example

In the WordPress example below, the only identification requirement is an email address that is accessible to the registrant.

![WordPress Registration Page](images/Wordpress_registration_page.jpg)\
*Figure 4.3.2-1: WordPress Registration Page*

In contrast, in the Google example below the identification requirements include name, date of birth, country, mobile phone number, email address and CAPTCHA response. While only two of these can be verified (email address and mobile number), the identification requirements are stricter than WordPress.

![Google Registration Page](images/Google_registration_page.jpg)\
*Figure 4.3.2-2: Google Registration Page*

