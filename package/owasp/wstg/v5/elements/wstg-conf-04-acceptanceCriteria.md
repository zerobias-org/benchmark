# Black-Box Testing

Testing for unreferenced files uses both automated and manual techniques, and typically involves a combination of the following:

## Inference from the Naming Scheme Used for Published Content

Enumerate all of the application’s pages and functionality. This can be done manually using a browser, or using an application spidering tool. Most applications use a recognizable naming scheme, and organize resources into pages and directories using words that describe their function. It is often possible to infer the name and location of unreferenced pages from the naming scheme used for published content. For example, if a page titled viewuser.asp is found, one should also look for edituser.asp, adduser.asp, and deleteuser.asp. Similarly, if a directory /app/user is discovered, one should also search for /app/admin and /app/manager.

## Other Clues in Published Content

Many web applications leave clues in published content that can lead to the discovery of hidden pages and functionality. These clues can often be found in the source code of HTML and JavaScript files. The source code for all published content should be manually reviewed to identify clues about other pages and functionality. For example:

Programmers' comments and commented-out sections of source code may refer to hidden content:

```html
<!-- <A HREF="uploadfile.jsp">Upload a document to the server</A> -->
<!-- Link removed while bugs in uploadfile.jsp are fixed          -->
```

JavaScript may contain page links that are only rendered within the user’s GUI under certain circumstances:

```javascript
var adminUser=false;
if (adminUser) menu.add (new menuItem ("Maintain users", "/admin/useradmin.jsp"));
```

HTML pages may contain FORMs that have been hidden by disabling the SUBMIT element:

```html
<form action="forgotPassword.jsp" method="post">
    <input type="hidden" name="userID" value="123">
    <!-- <input type="submit" value="Forgot Password"> -->
</form>
```

Another source of clues about unreferenced directories is the `/robots.txt` file used to provide instructions to web robots:

```html
User-agent: *
Disallow: /Admin
Disallow: /uploads
Disallow: /backup
Disallow: /~jbloggs
Disallow: /include
```

## Blind Guessing

In its simplest form, this involves running a list of common filenames through a request engine in an attempt to guess files and directories that exist on the server. The following netcat wrapper script will read a wordlist from stdin and perform a basic guessing attack:

```bash
#!/bin/bash

server=example.org
port=80

while read url
do
echo -ne "$url\t"
echo -e "GET /$url HTTP/1.0\nHost: $server\n" | netcat $server $port | head -1
done | tee outputfile
```

Depending upon the server, GET may be replaced with HEAD for faster results. The output file specified can be grepped for "interesting" response codes. The response code 200 (OK) usually indicates that a valid resource has been found (provided the server does not deliver a custom "not found" page using the 200 code). But also look out for 301 (Moved), 302 (Found), 401 (Unauthorized), 403 (Forbidden) and 500 (Internal error), which may also indicate resources or directories that are worthy of further investigation.

The basic guessing attack should be run against the webroot, and also against all directories that have been identified through other enumeration techniques. More advanced/effective guessing attacks can be performed as follows:

- Identify the file extensions in use within known areas of the application (e.g. JSP, ASPX, HTML), and use a basic wordlist appended with each of these extensions (or use a longer list of common extensions if resources permit).
- For each file identified through other enumeration techniques, create a custom wordlist derived from that filename. Get a list of common file extensions (including ~, bak, txt, src, dev, old, inc, orig, copy, tmp, swp, etc.) and use each extension before, after, and instead of, the extension of the actual filename.

Note: Windows file copying operations generate filenames prefixed with "Copy of " or localized versions of this phrase, hence they do not change file extensions. While "Copy of " files typically do not disclose source code when accessed, they might yield valuable information in case they cause errors when invoked.

## Information Obtained Through Server Vulnerabilities and Misconfiguration

The most obvious way in which a misconfigured server may disclose unreferenced pages is through directory listing. Request all enumerated directories to identify any which provide a directory listing.

Numerous vulnerabilities have been found in individual web servers which allow an attacker to enumerate unreferenced content, for example:

- Apache ?M=D directory listing vulnerability.
- Various IIS script source disclosure vulnerabilities.
- IIS WebDAV directory listing vulnerabilities.

## Use of Publicly Available Information

Pages and functionality in internet-facing web applications that are not referenced from within the application itself may be referenced from other public domain sources. There are various sources of these references:

- Pages that used to be referenced may still appear in the archives of internet search engines. For example, `1998results.asp` may no longer be linked from a company’s site, but may remain on the server and in search engine databases. This old script may contain vulnerabilities that could be used to compromise the entire site. The `site:` Google search operator may be used to run a query only against the domain of choice, such as in: `site:www.example.com`. Using search engines in this way has led to a broad array of techniques which you may find useful, and are described in the `Google Hacking` section of this Guide. Check it to hone your testing skills via Google. Backup files are not likely to be referenced by any other files and therefore may have not been indexed by Google, but if they lie in browsable directories the search engine might know about them.
- In addition, Google and Yahoo keep cached versions of pages found by their robots. Even if `1998results.asp` has been removed from the target server, a version of its output may still be stored by these search engines. The cached version may contain references to, or clues about, additional hidden content that still remains on the server.
- Content that is not referenced from within a target application may be linked to by third-party sites. For example, an application which processes online payments on behalf of third-party traders may contain a variety of bespoke functionality which can (normally) only be found by following links within the sites of its customers.

## Filename Filter Bypass

Because deny list filters are based on regular expressions, one can sometimes take advantage of obscure OS filename expansion features which work in ways the developer didn't expect. The tester can sometimes exploit differences in ways that filenames are parsed by the application, web server, and underlying OS and it's filename conventions.

Example: Windows 8.3 filename expansion `c:\\program files` becomes `C:\\PROGRA\~1`

- Remove incompatible characters
- Convert spaces to underscores
- Take the first six characters of the basename
- Add `~<digit>` which is used to distinguish files with names using the same six initial characters
- This convention changes after the first 3 name collisions
- Truncate file extension to three characters
- Make all the characters uppercase

# Gray-Box Testing

Performing gray-box testing against old and backup files necessitates the examination of files within directories that belong to the set of web directories served by the web server(s) comprising the web application infrastructure. Theoretically the examination should be performed by hand to be thorough. However, since in most cases copies of files or backup files tend to be created by using the same naming conventions, the search can be easily scripted. For example, editors leave behind backup copies by naming them with a recognizable extension or ending and humans tend to leave behind files with a `.old` or similar predictable extensions. A useful strategy would be to periodically schedule a background job to check for files with extensions that are likely to be identified as copies or backup files, while also performing manual checks on a longer time basis.

