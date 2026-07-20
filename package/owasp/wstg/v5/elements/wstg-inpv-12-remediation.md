# Sanitization

The URL and form data needs to be sanitized for invalid characters. A deny list of characters is an option but it may be difficult to think of all of the characters to validate against. Also there may be some that were not discovered as of yet. An allow list containing only allowable characters or command list should be created to validate the user input. Characters that were missed, as well as undiscovered threats, should be eliminated by this list.

General deny list to be included for command injection can be `|` `;` `&` `$` `>` `<` `'` `\` `!` `>>` `#`

Escape or filter special characters for windows,   `(` `)` `<` `>` `&` `*` `‘` `|` `=` `?` `;` `[` `]` `^` `~` `!` `.` `"` `%` `@` `/` `\` `:` `+` `,`  ``` ` ```
Escape or filter special characters for Linux, `{` `}` `(` `)` `>` `<` `&` `*` `‘` `|` `=` `?` `;` `[` `]` `$` `–` `#` `~` `!` `.` `"` `%`  `/` `\` `:` `+` `,` ``` ` ```

# Permissions

The web application and its components should be running under strict permissions that do not allow operating system command execution. Try to verify all this information to test from a gray-box testing point of view.

