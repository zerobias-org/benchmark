For an effective protection strategy, testing should be combined with a security policy that clearly forbids dangerous practices, including:

- Editing files in-place on the web server or application server file systems. This is a particularly bad habit, since it is likely to generate backup or temporary files by the editors. It is amazing to see how often this is done, even in large organizations. If you absolutely need to edit files on a production system, do ensure that you don’t leave behind anything that is not explicitly intended, and keep in mind that you are doing it at your own risk.
- Carefully check any other activity performed on file systems exposed by the web server, such as spot administration activities. For example, if you occasionally need to take a snapshot of a couple of directories (which you should not do on a production system), you may be tempted to zip them first. Be careful not to leave behind such archive files.
- Appropriate configuration management policies should help prevent obsolete and un-referenced files.
- Applications should be designed not to create (or rely on) files stored under the web directory trees served by the web server. Data files, log files, configuration files, etc. should be stored in directories not accessible by the web server to counter the possibility of information disclosure, not to mention the potential for data modification if web directory permissions allow writing.
- File system snapshots should not be accessible via the web if the document root is on a file system using this technology. Configure your web server to deny access to such directories, for example, under Apache, a location directive like this should be used:

```xml
<Location ~ ".snapshot">
    Order deny,allow
    Deny from all
</Location>
```

