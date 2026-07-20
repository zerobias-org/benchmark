# Roles Identification

The tester should start by identifying the application roles being tested through any of the following methods:

- Application documentation.
- Guidance by the developers or administrators of the application.
- Application comments.
- Fuzz possible roles:
    - cookie variable (*e.g.* `role=admin`, `isAdmin=True`)
    - account variable (*e.g.* `Role: manager`)
    - hidden directories or files (*e.g.* `/admin`, `/mod`, `/backups`)
    - switching to well known users (*e.g.* `admin`, `backups`, etc.)

# Switching to Available Roles

After identifying possible attack vectors, the tester needs to test and validate that they can access the available roles.

> Some applications define the roles of the user on creation, through rigorous checks and policies, or by ensuring that the user's role is properly protected through a signature created by the backend. Finding that roles exist doesn't mean that they're a vulnerability.
# Review Roles Permissions

After gaining access to the roles on the system, the tester must understand the permissions provided to each role.

A support engineer shouldn't be able to conduct administrative functionalities, manage the backups, or conduct any transactions in the place of a user.

An administrator shouldn't have full powers on the system. Sensitive admin functionality should leverage a maker-checker principle, or use MFA to ensure that the administrator is conducting the transaction. A clear example on this was the [Twitter incident in 2020](https://blog.twitter.com/en_us/topics/company/2020/an-update-on-our-security-incident.html).

