- Avoid storing, transmitting or processing card details wherever possible.
    - Use a redirect or IFRAME for the payment gateway.
- Review payment gateway documentation and use all available security features (such as encryption and secure hashes).
- Handle all pricing related information on server-side:
    - The only things included in client-side requests should be item IDs and quantities.
- Implement appropriate input validation and business logic constraints (such as checking for negative item numbers or values).
- Ensure that application payment flow is robust and that steps can't be performed out of sequence.

