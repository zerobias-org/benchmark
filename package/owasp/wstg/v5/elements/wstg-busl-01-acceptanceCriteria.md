# Generic Test Method

- Review the project documentation and use exploratory testing looking for data entry points or hand off points between systems or software.
- Once found try to insert logically invalid data into the application/system.
Specific Testing Method:
- Perform frontend GUI Functional Valid testing on the application to ensure that the only "valid" values are accepted.
- Using an intercepting proxy observe the HTTP POST/GET looking for places that variables such as cost and quality are passed. Specifically, look for "hand-offs" between application/systems that may be possible injection or tamper points.
- Once variables are found start interrogating the field with logically "invalid" data, such as social security numbers or unique identifiers that do not exist or that do not fit the business logic. This testing verifies that the server functions properly and does not accept logically invalid data.

