Cloud storage services allow web applications and services to store and access objects in the storage service. Improper access control configuration, however, may lead to the exposure of sensitive information, data tampering, or unauthorized access.

A known example is where an Amazon S3 bucket is misconfigured, although the other cloud storage services may also be exposed to similar risks. By default, all S3 buckets are private and can be accessed only by users who are explicitly granted access. Users can grant public access not only to the bucket itself but also to individual objects stored within that bucket. This may lead to an unauthorized user being able to upload new files, modify or read stored files.

