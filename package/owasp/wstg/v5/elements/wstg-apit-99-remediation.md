- Restrict access to introspection queries.
- Implement input validation.
    - GraphQL does not have a native way to validate input, however, there is an open source project called ["graphql-constraint-directive"](https://github.com/confuser/graphql-constraint-directive) which allows for input validation as part of the schema definition.
    - Input validation alone is helpful, but it is not a complete solution and additional measures should be taken to mitigate injection attacks.
- Implement security measures to prevent abusive queries.
    - Timeouts: restrict the amount of time that a query is permitted to run.
    - Maximum query depth: limit the depth of allowed queries, which may prevent queries that are too deep from abusing resources.
    - Set maximum query complexity: limit the complexity of queries to mitigate the abuse of GraphQL resources.
    - Use server-time-based throttling: limit the amount of server time a user can consume.
    - Use query-complexity-based throttling: limit the total complexity of queries a user can consume.
- Send generic error messages: use generic error messages that do not reveal details of the deployment.
- Mitigate batching attacks:
    - Add object request rate limiting in code.
    - Prevent batching for sensitive objects.
    - Limit the number of queries that can run at one time.

For more on remediating GraphQL weaknesses, refer to the [GraphQL Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/GraphQL_Cheat_Sheet.html).

