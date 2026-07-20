# Understand the Primary Mechanism

Fully test the website's primary authentication functions. This should identify how accounts are issued, created or changed and how passwords are recovered, reset, or changed. Additionally knowledge of any elevated privilege authentication and authentication protection measures should be known. These precursors are necessary to be able to compare with any alternative channels.

# Identify Other Channels

Other channels can be found by using the following methods:

- Reading site content, especially the home page, contact us, help pages, support articles and FAQs, T&Cs, privacy notices, the robots.txt file and any sitemap.xml files.
- Searching HTTP proxy logs, recorded during previous information gathering and testing, for strings such as "mobile", "android", blackberry", "ipad", "iphone", "mobile app", "e-reader", "wireless", "auth", "sso", "single sign on" in URL paths and body content.
- Use search engines to find different websites from the same organization, or using the same domain name, that have similar home page content or which also have authentication mechanisms.

For each possible channel confirm whether user accounts are shared across these, or provide access to the same or similar functionality.

# Enumerate Authentication Functionality

For each alternative channel where user accounts or functionality are shared, identify if all the authentication functions of the primary channel are available, and if anything extra exists. It may be useful to create a grid like the one below:

  | Primary | Mobile  |  Call Center | Partner Website |
  |---------|---------|--------------|-----------------|
  | Register| Yes     |     -        |       -         |
  | Log in  | Yes     |    Yes       |    Yes(SSO)     |
  | Log out |   -     |     -        |       -         |
  |Password reset |   Yes  |   Yes   |       -         |
  | -       | Change password |   -  |       -         |

In this example, mobile has an extra function "change password" but does not offer "log out". A limited number of tasks are also possible by phoning the call center. Call centers can be interesting, because their identity confirmation checks might be weaker than the website's, allowing this channel to be used to aid an attack against a user's account.

While enumerating these it is worth taking note of how session management is undertaken, in case there is overlap across any channels (e.g. cookies scoped to the same parent domain name, concurrent sessions allowed across channels, but not on the same channel).

# Review and Test

Alternative channels should be mentioned in the testing report, even if they are marked as "information only" or "out of scope". In some cases the test scope might include the alternative channel (e.g. because it is just another path on the target host name), or may be added to the scope after discussion with the owners of all the channels. If testing is permitted and authorized, all the other authentication tests in this guide should then be performed, and compared against the primary channel.

