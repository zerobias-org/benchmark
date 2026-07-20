[OAuth2.0](https://oauth.net/2/) (hereinafter referred to as OAuth) is an authorization framework that allows a client to access resources on the behalf of its user.

In order to achieve this, OAuth heavily relies on tokens to communicate between the different entities, each entity having a different [role](https://datatracker.ietf.org/doc/html/rfc6749#section-1.1):

- **Resource Owner:** The entity who grants access to a resource, the owner, and in most cases is the user themselves
- **Client:** The application that is requesting access to a resource on behalf of the Resource Owner. These clients come in two [types](https://oauth.net/2/client-types/):
    - **Public:** clients that can't protect a secret (*e.g.* frontend focused applications, such as SPAs, mobile applications, etc.)
    - **Confidential:** clients that are able to securely authenticate with the authorization server by keeping their registered secrets safe (*e.g.* backend services)
- **Authorization Server:** The server that holds authorization information and grants the access
- **Resource Server:** The application that serves the content accessed by the client

Since OAuth's responsibility is to delegate access rights by the owner to the client, this is a very attractive target for attackers, and bad implementations lead to unauthorized access to the users' resources and information.

In order to provide access to a client application, OAuth relies on several [authorization grant types](https://oauth.net/2/grant-types/) to generate an access token:

- [Authorization Code](https://oauth.net/2/grant-types/authorization-code/): used by both confidential and public clients to exchange an authorization code for an access token, but recommended only for confidential clients
- [Proof Key for Code Exchange (PKCE)](https://oauth.net/2/pkce/): PKCE builds on top of the Authorization Code grant, providing stronger security for it to be used by public clients, and improving the posture of confidential ones
- [Client Credentials](https://oauth.net/2/grant-types/client-credentials/): used for machine to machine communication, where the "user" here is the machine requesting access to its own resources from the Resource Server
- [Device Code](https://oauth.net/2/grant-types/device-code/): used for devices with limited input capabilities.
- [Refresh Token](https://oauth.net/2/grant-types/refresh-token/): tokens provided by the authorization server to allow clients to refresh users' access tokens once they become invalid or expire. This grant type is used in conjunction with one other grant type.

Two flows will be deprecated in the release of [OAuth2.1](https://oauth.net/2.1/), and their usage is not recommended:

- [Implicit Flow*](https://oauth.net/2/grant-types/implicit/): PKCE's secure implementation renders this flow obsolete. Prior to PKCE, the implicit flow was used by client-side applications such as [single page applications](https://en.wikipedia.org/wiki/Single-page_application) since [CORS](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS) relaxed the [same-origin policy](https://developer.mozilla.org/en-US/docs/Web/Security/Same-origin_policy) for sites to inter-communicate. For more information on why the implicit grant is not recommended, review this [section](https://datatracker.ietf.org/doc/html/draft-ietf-oauth-security-topics#section-2.1.2).
- [Resource Owner Password Credentials](https://oauth.net/2/grant-types/password/):used to exchange users' credentials directly with the client, which then sends them to the authorization to exchange them for an access token. For information on why this flow is not recommended, review this [section](https://datatracker.ietf.org/doc/html/draft-ietf-oauth-security-topics#section-2.4).

*: The implicit flow in OAuth only is deprecated, yet is still a viable solution within Open ID Connect (OIDC) to retrieve `id_tokens`. Be careful to understand how the implicit flow is being used, which can be identified if only the `/authorization` endpoint is being used to gain an access token, without relying on `/token` endpoint in any way. An example on this can be found [here](https://auth0.com/docs/get-started/authentication-and-authorization-flow/implicit-flow-with-form-post).

*Please note that OAuth flows are a complex topic, and the above includes only a summary of the key areas. The inline references contain further information about the specific flows.*

