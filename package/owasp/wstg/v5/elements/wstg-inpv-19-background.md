- Identify SSRF injection points.
- Test if the injection points are exploitable.
- Asses the severity of the vulnerability.

Web applications often interact with internal or external resources. While you may expect that only the intended resource will be handling the data you send, improperly handled data may create a situation where injection attacks are possible. One type of injection attack is called Server-side Request Forgery (SSRF). A successful SSRF attack can grant the attacker access to restricted actions, internal services, or internal files within the application or the organization. In some cases, it can even lead to Remote Code Execution (RCE).

