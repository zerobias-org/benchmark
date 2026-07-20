To test for misconfigurations in CSPs, look for insecure configurations by examining the `Content-Security-Policy` HTTP response header or CSP `meta` element in a proxy tool:

- `unsafe-inline` directive enables inline scripts or styles, making the applications susceptible to [XSS](../07-Input_Validation_Testing/01-Testing_for_Reflected_Cross_Site_Scripting.md) attacks.
- `unsafe-eval` directive allows `eval()` to be used in the application and is susceptible to common bypass techniques such as data URL injection.
- `unsafe-hashes` directive allows use of inline scripts/styles, assuming they match the specified hashes.
- Resources such as scripts can be allowed to be loaded from any origin by the use wildcard (`*`) source.
    - Also consider wildcards based on partial matches, such as: `https://*` or `*.cdn.com`.
    - Consider whether allow listed sources provide JSONP endpoints which might be used to bypass CSP or same-origin-policy.
- Framing can be enabled for all origins by the use of the wildcard (`*`) source for the `frame-ancestors` directive. If the `frame-ancestors` directive is not defined in the Content-Security-Policy header it may make applications vulnerable to [clickjacking](../11-Client-side_Testing/09-Testing_for_Clickjacking.md) attacks.
- Business critical applications should require to use a strict policy.

