To manually check for this type of vulnerability, we must identify whether the application employs inputs without correctly validating them. If so, these inputs are under the control of the user and could be used to specify external resources. Since there are many resources that could be included in the application (such as images, video, objects, css, and iframes), the client-side scripts that handle the associated URLs should be investigated for potential issues.

The following table shows possible injection points (sink) that should be checked:

| Resource Type   | Tag/Method                                | Sink   |
| --------------- | ----------------------------------------- | ------ |
| Frame           | iframe                                    | src    |
| Link            | a                                         | href   |
| AJAX Request    | `xhr.open(method, [url], true);` | URL    |
| CSS             | link                                      | href   |
| Image           | img                                       | src    |
| Object          | object                                    | data   |
| Script          | script                                    | src    |

The most interesting ones are those that allow to an attacker to include client-side code (for example JavaScript) that could lead to XSS vulnerabilities.