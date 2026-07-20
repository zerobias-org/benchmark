# Testing for Deprecated Grant Types

Deprecated grant types were obsoleted for security and functionality reasons. Identifying if they're being used allows us to quickly review if they're susceptible to any of the threats pertaining to their usage. Some might be out of scope to the attacker, such as the way a client might be using the users' credentials. This should be documented and raised to the internal engineering teams.

For public clients, it is generally possible to identify the grant type in the request to the `/token` endpoint. It is indicated in the token exchange with the parameter `grant_type`.

The following example shows the Authorization Code grant with PKCE.

```http
POST /oauth/token HTTP/1.1
Host: as.example.com
[...]

{
  "client_id":"example-client",
  "code_verifier":"example",
  "grant_type":"authorization_code",
  "code":"example",
  "redirect_uri":"http://client.example.com"
}
```

The values for the `grant_type` parameter and the grant type they indicate are:

- `password`: Indicates the ROPC grant.
- `client_credentials`: Indicates the Client Credential grant.
- `authorization_code`: Indicates the Authorization Code grant.

The Implicit Flow type is not indicated by the `grant_type` parameter since the token is presented in the response to the `/authorization` endpoint request, and instead can be identified through the `response_type`. Below is an example.

```http
GET /authorize
  ?client_id=<some_client_id>
  &response_type=token 
  &redirect_uri=https%3A%2F%2Fclient.example.com%2F
  &scope=openid%20profile%20email
  &state=<random_state>
```

The following URL parameters indicate the OAuth flow being used:

- `response_type=token`: Indicates Implicit Flow, as the client is directly requesting from the authorization server to return a token.
- `response_type=code`: Indicates Authorization Code flow, as the client is requesting from the authorization server to return a code, that will be exchanged afterwards with a token.
- `code_challenge=sha256(xyz)`: Indicates the PKCE extension, as no other flow uses this parameter.

The following is an example authorization request for Authorization Code flow with PKCE:

```http
GET /authorize
    ?redirect_uri=https%3A%2F%2Fclient.example.com%2F
    &client_id=<some_client_id>
    &scope=openid%20profile%20email
    &response_type=code
    &response_mode=query
    &state=<random_state>
    &nonce=<random_nonce>
    &code_challenge=<random_code_challenge>
    &code_challenge_method=S256 HTTP/1.1
Host: as.example.com
[...]
```

## Public Clients

The Authorization Code grant with PKCE extension is recommended for public clients. An authorization request for Authorization Code flow with PKCE should contain `response_type=code` and `code_challenge=sha256(xyz)`.

The token exchange should contain the grant type `authorization_code` and a `code_verifier`.

Improper grant types for public clients are:

- Authorization Code grant without the PKCE extension
- Client Credentials
- Implicit Flow
- ROPC

## Confidential Clients

The Authorization Code grant is recommended for confidential clients. The PKCE extension may be used as well.

Improper grant types for confidential clients are:

- Client Credentials (Except for machine-to-machine -- see below)
- Implicit Flow
- ROPC

### Machine-to-Machine

In situations where no user interaction occurs and the clients are only confidential clients, the Client Credentials grant may be used.

If you know the `client_id` and `client_secret`, it is possible to obtain a token by passing the `client_credentials` grant type.

```bash
$ curl --request POST \
  --url https://as.example.com/oauth/token \
  --header 'content-type: application/json' \
  --data '{"client_id":"<some_client_id>","client_secret":"<some_client_secret>","grant_type":"client_credentials"}' --proxy http://localhost:8080/ -k
```

# Credential Leakage

Depending on the flow, OAuth transports several types of credentials in as URL parameters.

The following tokens can be considered to be leaked credentials:

- access token
- refresh token
- authorization code
- PKCE code challenge / code verifier

Due to how OAuth works, the authorization `code` as well as the `code_challenge`, and `code_verifier` may be part of the URL. The implicit flow transports the authorization token as part of the URL if the `response_mode` is not set to [`form_post`](https://openid.net/specs/oauth-v2-form-post-response-mode-1_0.html). This may lead to leakage of the requested token or code in the referrer header, in log files, and proxies due to these parameters being passed either in the query or the fragment.

The risk that's carried by the implicit flow leaking the tokens is far higher than leaking the `code` or any other `code_*` parameters, as they are bound to specific clients and are harder to abuse in case of leakage.

In order to test this scenario, make use of an HTTP intercepting proxy such as ZAP and intercept the OAuth traffic.

- Step through the authorization process and identify any credentials present in the URL.
- If any external resources are included in a page involved with the OAuth flow, analyze the request made to them. Credentials could be leaked in the referrer header.

After stepping through the OAuth flow and using the application, a few requests are captured in the request history of an HTTP intercepting proxy. Search for the HTTP referrer header (e.g. `Referer: https://idp.example.com/`) containing the authorization server and client URL in the request history.

Reviewing the HTML meta tags (although this tag is [not supported](https://caniuse.com/mdn-html_elements_meta_name_referrer) on all browsers), or the [Referrer-Policy](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Referrer-Policy) could help assess if any credential leakage is happening through the referrer header.

