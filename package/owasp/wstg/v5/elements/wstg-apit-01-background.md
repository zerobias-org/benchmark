Reconnaissance is an important step in any pentesting engagement. This includes API pentesting. Reconnaissance significantly enhances the effectiveness of the testing process by gathering information about the API and developing an understanding of the target. This phase not only increases the likelihood of discovering critical security issues but also ensures a comprehensive evaluation of the APIs' security posture.

This guide has a section on [Information Gathering](../01-Information_Gathering/README.md) which can apply when auditing APIs. However, there are some differences. As security researchers, we often focus on specific areas and searching this guide for the sections that apply can be time consuming. To ensure the researcher has a single location to focus on APIs this section concentrates on those items that apply to APIs and provides references to supporting content elsewhere in the guide.

# API Types

APIs can be public or private.

## Public APIs

Public APIs typically have their details published in a Swagger/OpenAPI document. Gaining access to this document is important to understand the attack surface. Equally important is finding older versions of this document that might show depricated but still functional code that may have security vulnerabilities.

Keep in mind that this document, however well intentioned, may not be accurate, and also may not dislose the complete API.

Public APIs may also be documented on shared libraries or directories of APIs.

## Private APIs

The visibility of private APIs depends on who the intended consumer is. An API can be private, but only accessible to subscribed clients (also known as `partners`) or only accessible to internal clients, such as other departments within the same company. Finding private APIs using reconnaissance techniques is also important. These APIs can be discovered using a number of techniques which we will discuss below.

