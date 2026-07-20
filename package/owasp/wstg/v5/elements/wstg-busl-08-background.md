Many applications' business processes allow for the upload and manipulation of data that is submitted via files. But the business process must check the files and only allow certain "approved" file types. Deciding what files are "approved" is determined by the business logic and is application/system specific. The risk is that by allowing users to upload files, attackers may submit an unexpected file type that could be executed and adversely impact the application or system through attacks that may deface the site, perform remote commands, browse the system files, browse the local resources, attack other servers, or exploit the local vulnerabilities, just to name a few.

Vulnerabilities related to the upload of unexpected file types is unique in that the upload should quickly reject a file if it does not have a specific extension. Additionally, this is different from uploading malicious files in that in most cases an incorrect file format may not by it self be inherently "malicious" but may be detrimental to the saved data. For example if an application accepts Windows Excel files, if a similar database file is uploaded it may be read but data extracted my be moved to incorrect locations.

The application may be expecting only certain file types to be uploaded for processing, such as `.csv` or `.txt` files. The application may not validate the uploaded file by extension (for low assurance file validation) or content (high assurance file validation). This may result in unexpected system or database results within the application/system or give attackers additional methods to exploit the application/system.

# Example

Suppose a picture sharing application allows users to upload a `.gif` or `.jpg` graphic file to the site. What if an attacker is able to upload an HTML file with a `<script>` tag in it or PHP file? The system may move the file from a temporary location to the final location where the PHP code can now be executed against the application or system.

