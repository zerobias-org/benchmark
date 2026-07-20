Modern web applications are very often based on frameworks. Many of these web application frameworks allow automatic binding of user input (in the form of HTTP request parameters) to internal objects. This is often called autobinding.
This feature can be sometimes exploited to access fields that were never intended to be modified from outside leading to privilege escalation, data tampering, bypass of security mechanisms, and more.
In this case there is a Mass Assignment vulnerability.

Examples of sensitive properties:

- **Permission-related properties**: should only be set by privileged users (e.g. `is_admin`, `role`, `approved`).
- **Process-dependent properties**: should only be set internally, after a process is completed (e.g. `balance`, `status`, `email_verified`)
- **Internal properties**: should only be set internally by the application (e.g. `created_at`, `updated_at`)

