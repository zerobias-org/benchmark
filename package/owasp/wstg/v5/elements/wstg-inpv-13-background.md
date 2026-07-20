A format string is a null-terminated character sequence that also contains conversion specifiers interpreted or converted at runtime. If server-side code [concatenates a user's input with a format string](https://www.netsparker.com/blog/web-security/string-concatenation-format-string-vulnerabilities/), an attacker can append additional conversion specifiers to cause a runtime error, information disclosure, or buffer overflow.

The worst case for format strings vulnerabilities occur in languages that don't check arguments and also include a `%n` specifier that writes to memory. These functions, if exploited by an attacker modifying a format string, could cause [information disclosure and code execution](https://www.veracode.com/security/format-string):

- C and C++ [printf](https://en.cppreference.com/w/c/io/fprintf) and similar methods fprintf, sprintf, snprintf
- Perl [printf](https://perldoc.perl.org/functions/printf.html) and sprintf

These format string functions cannot write to memory, but attackers can still cause information disclosure by changing format strings to output values the developers did not intend to send:

- Python 2.6 and 2.7 [str.format](https://docs.python.org/2/library/string.html) and Python 3 unicode [str.format](https://docs.python.org/3/library/stdtypes.html#str.format) can be modified by injecting strings that can point to [other variables](https://lucumr.pocoo.org/2016/12/29/careful-with-str-format/) in memory

The following format string functions can cause runtime errors if the attacker adds conversion specifiers:

- Java [String.format](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html#format%28java.util.Locale%2Cjava.lang.String%2Cjava.lang.Object...%29) and [PrintStream.format](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/PrintStream.html#format%2528java.util.Locale%252Cjava.lang.String%252Cjava.lang.Object...%2529)
- PHP [printf](https://www.php.net/manual/es/function.printf.php)

The code pattern that causes a format string vulnerability is a call to a string format function that contains unsanitized user input. The following example shows how a debug `printf` could make a program vulnerable:

The example in C:

```c
char *userName = /* input from user controlled field */;

printf("DEBUG Current user: ");
// Vulnerable debugging code
printf(userName);
```

The example in Java:

```java
final String userName = /* input from user controlled field */;

System.out.printf("DEBUG Current user: ");
// Vulnerable code:
System.out.printf(userName);
```

In this particular example, if the attacker set their `userName` to have one or more conversion specifiers, there would be unwanted behavior. The C example would [print out memory contents](https://www.defcon.org/images/defcon-18/dc-18-presentations/Haas/DEFCON-18-Haas-Adv-Format-String-Attacks.pdf) if `userName` contained `%p%p%p%p%p`, and it can corrupt memory contents if there is a `%n` in the string. In the Java example, a `username` containing any specifier that needs an input (including `%x` or `%s`) would cause the program to crash with `IllegalFormatException`. Although the examples are still subject to other problems, the vulnerability can be fixed by printf arguments of `printf("DEBUG Current user: %s", userName)`.

