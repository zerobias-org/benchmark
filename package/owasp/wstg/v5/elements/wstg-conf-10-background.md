- Enumerate all possible domains (previous and current).
- Identify any forgotten or misconfigured domains.

A successful exploitation of this kind of vulnerability allows an adversary to claim and take control of the victim's subdomain. This attack relies on the following:

1. The victim's external DNS server subdomain record is configured to point to a non-existing or non-active resource/external service/endpoint. The proliferation of XaaS (Anything as a Service) products and public cloud services offer a lot of potential targets to consider.
2. The service provider hosting the resource/external service/endpoint does not handle subdomain ownership verification properly.

If the subdomain takeover is successful, a wide variety of attacks are possible (serving malicious content, phishing, stealing user session cookies, credentials, etc.). This vulnerability could be exploited for a wide variety of DNS resource records including: `A`, `CNAME`, `MX`, `NS`, `TXT` etc. In terms of the attack severity, an `NS` subdomain takeover (although less likely) has the highest impact, because a successful attack could result in full control over the whole DNS zone and the victim's domain.

# GitHub

1. The victim (victim.com) uses GitHub for development and configured a DNS record (`coderepo.victim.com`) to access it.
2. The victim decides to migrate their code repository from GitHub to a commercial platform and does not remove `coderepo.victim.com` from their DNS server.
3. An adversary discovers that `coderepo.victim.com` is hosted on GitHub and claims it using GitHub Pages and their own GitHub account.

# Expired Domain

1. The victim (victim.com) owns another domain (victimotherdomain.com) and uses a CNAME record (www) to reference the other domain (`www.victim.com` --> `victimotherdomain.com`)
2. At some point, victimotherdomain.com expires, becoming available for registration by anyone. Since the CNAME record is not deleted from the victim.com DNS zone, anyone who registers `victimotherdomain.com` has full control over `www.victim.com` until the DNS record is removed or updated.

