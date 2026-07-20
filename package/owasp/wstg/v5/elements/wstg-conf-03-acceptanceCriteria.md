# Forced Browsing

Submit requests with different file extensions and verify how they are handled. The verification should be on a per web directory basis. Verify directories that allow script execution. Web server directories can be identified by scanning tools which look for the presence of well-known directories. Additionally, mirroring the site structure helps testers reconstruct the directory tree served by the application.

If the web application architecture is load-balanced, it is important to assess all of the web servers. The ease of this task depends on the configuration of the balancing infrastructure. In an infrastructure with redundant components, there may be slight variations in the configuration of individual web or application servers. This may happen if the web architecture employs heterogeneous technologies (think of a set of IIS and Apache web servers in a load-balancing configuration, which may introduce slight asymmetric behavior between them, and possibly different vulnerabilities).

## Example

The tester has identified the existence of a file named `connection.inc`. Trying to access it directly gives back its contents, which are:

```php
<?
    mysql_connect("127.0.0.1", "root", "password")
        or die("Could not connect");
?>
```

The tester determines the existence of a MySQL DBMS backend and the weak credentials used by the web application to access it.

The following file extensions should never be returned by a web server, as they pertain to files that could contain sensitive information or files that have no valid reason to be served.

- `.asa`
- `.inc`
- `.config`

The following file extensions are related to files which, when accessed, are either displayed or downloaded by the browser. Therefore, files with these extensions must be checked to verify that they are indeed supposed to be served (and are not leftovers), and that they do not contain sensitive information.

- `.zip`, `.tar`, `.gz`, `.tgz`, `.rar`, etc.: (Compressed) archive files
- `.java`: No reason to provide access to Java source files
- `.txt`: Text files
- `.pdf`: PDF documents
- `.docx`, `.rtf`, `.xlsx`, `.pptx`, etc.: Office documents
- `.bak`, `.old` and other extensions indicative of backup files (for example: `~` for Emacs backup files)

The list given above details only a few examples, since file extensions are too many to be comprehensively treated here. Refer to [FILExt](https://filext.com/) for a more thorough database of extensions.

To identify files with a given extension, a mix of techniques can be employed. These techniques can include using vulnerability scanners, spidering and mirroring tools, and querying search engines (see [Testing: Spidering and googling](../01-Information_Gathering/01-Conduct_Search_Engine_Discovery_Reconnaissance_for_Information_Leakage.md)). Manual inspection of the application can also be beneficial, as it overcomes limitations in automatic spidering. See also [Testing for Old, Backup and Unreferenced Files](04-Review_Old_Backup_and_Unreferenced_Files_for_Sensitive_Information.md) which deals with the security issues related to "forgotten" files.

# File Upload

Windows 8.3 legacy file handling can sometimes be used to defeat file upload filters.

Usage examples:

1. `file.phtml` gets processed as PHP code.
2. `FILE~1.PHT` is served, but not processed by the PHP ISAPI handler.
3. `shell.phPWND` can be uploaded.
4. `SHELL~1.PHP` will be expanded and returned by the OS shell, then processed by the PHP ISAPI handler.

# Gray-Box Testing

White-box testing of file extension handling involves checking the server configurations in the web application architecture and verifying the rules for serving different file extensions.

If the web application relies on a load-balanced, heterogeneous infrastructure, determine whether this may introduce different behavior.

