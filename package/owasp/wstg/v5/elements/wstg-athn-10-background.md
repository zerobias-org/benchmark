- Identify alternative authentication channels.
- Assess the security measures used and if any bypasses exists on the alternative channels.

Even if the primary authentication mechanisms do not include any vulnerabilities, it may be that vulnerabilities exist in alternative legitimate authentication user channels for the same user accounts. Tests should be undertaken to identify alternative channels and, subject to test scoping, identify vulnerabilities.

The alternative user interaction channels could be utilized to circumvent the primary channel, or expose information that can then be used to assist an attack against the primary channel. Some of these channels may themselves be separate web applications using different hostnames or paths. For example:

- Standard website
- Mobile, or specific device, optimized website
- Accessibility optimized website
- Alternative country and language websites
- Parallel websites that utilize the same user accounts (e.g. another website offering different functionally of the same organization, a partner website with which user accounts are shared)
- Development, test, UAT and staging versions of the standard website

But they could also be other types of application or business processes:

- Mobile device app
- Desktop application
- Call center operators
- Interactive voice response or phone tree systems

Note that the focus of this test is on alternative channels; some authentication alternatives might appear as different content delivered via the same website and would almost certainly be in scope for testing. These are not discussed further here, and should have been identified during information gathering and primary authentication testing. For example:

- Progressive enrichment and graceful degradation that change functionality
- Site use without cookies
- Site use without JavaScript
- Site use without plugins such as for Flash and Java

Even if the scope of the test does not allow the alternative channels to be tested, their existence should be documented. These may undermine the degree of assurance in the authentication mechanisms and may be a precursor to additional testing.

