# Specific Testing Method

- Study the applications logical requirements.
- Prepare a library of files that are "not approved" for upload that may contain files such as: jsp, exe, or HTML files containing script.
- In the application navigate to the file submission or upload mechanism.
- Submit the "not approved" file for upload and verify that they are properly prevented from uploading
- Check if the site only does file type checks in client-side JavaScript
- Check if the site only checks the file type by "Content-Type" in HTTP request.
- Check if the site only checks the file type by the file extension.
- Check if other uploaded files can be accessed directly by specified URL.
- Check if the uploaded file can include code or script injection.
- Check if there is any file path checking for uploaded files. Especially, hackers may compress files with specified path in ZIP so that the extracted files can be uploaded to intended path after uploading and unzipping.

