# Specific Testing Method 1

- Using a proxy capture HTTP traffic looking for hidden fields.
- If a hidden field is found, see how these fields compare with the GUI application and start interrogating this value through the proxy by submitting different data values trying to circumvent the business process and manipulate values you were not intended to have access to.

# Specific Testing Method 2

- Using a proxy capture HTTP traffic looking for a place to insert information into areas of the application that are non-editable.
- If it is found, see how these fields compare with the GUI application and start interrogating this value through the proxy by submitting different data values trying to circumvent the business process and manipulate values you were not intended to have access to.

# Specific Testing Method 3

- List components of the application or system that could be impacted, for example logs or databases.
- For each component identified, try to read, edit or remove its information. For example log files should be identified and Testers should try to manipulate the data/information being collected.

