All types of applications (web apps, web servers, databases, etc.) will generate errors for various reasons. Developers often ignore handling these errors, or push away the idea that a user will ever try to trigger an error purposefully (*e.g.* sending a string where an integer is expected). When the developer only consider the happy path, they forget all other possible user-input the code can receive but can't handle.

Errors sometimes rise as:

- stack traces,
- network timeouts,
- input mismatch,
- and memory dumps.

Improper error handling can allow attackers to:

- Understand the APIs being used internally.
- Map the various services integrating with each other by gaining insight on internal systems and frameworks used, which opens up doors to attack chaining.
- Gather the versions and types of applications being used.
- DoS the system by forcing the system into a deadlock or an unhandled exception that sends a panic signal to the engine running it.
- Controls bypass where a certain exception is not restricted by the logic set around the happy path.

