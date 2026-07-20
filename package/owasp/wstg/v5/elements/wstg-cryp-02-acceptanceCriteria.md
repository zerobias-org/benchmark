# Black-Box Testing

First the possible input points for padding oracles must be identified. Generally the following conditions must be met:

1. The data is encrypted. Good candidates are values which appear to be random.
2. A block cipher is used. The length of the decoded (Base64 is used often) cipher text is a multiple of common cipher block sizes like 8 or 16 bytes. Different cipher texts (e.g. gathered by different sessions or manipulation of session state) share a common divisor in the length.

## Example 2

`Dg6W8OiWMIdVokIDH15T/A==` results after base64 decoding in `0e 0e 96 f0 e8 96 30 87 55 a2 42 03 1f 5e 53 fc`. This seems to be random and 16 byte long.

If such an input value candidate is identified, the behavior of the application to bit-wise tampering of the encrypted value should be verified. Normally this base64 encoded value will include the initialization vector (IV) prepended to the cipher text. Given a plaintext *`p`* and a cipher with a block size *`n`*, the number of blocks will be *`b = ceil( length(p) / n)`*. The length of the encrypted string will be *`y=(b+1)*n`* due to the initialization vector. To verify the presence of the oracle, decode the string, flip the last bit of the second-to-last block *`b-1`* (the least significant bit of the byte at *`y-n-1`*), re-encode and send. Next, decode the original string, flip the last bit of the block *`b-2`* (the least significant bit of the byte at *`y-2*n-1`*), re-encode and send.

If it is known that the encrypted string is a single block (the IV is stored on the server or the application is using a bad practice hardcoded IV), several bit flips must be performed in turn. An alternative approach could be to prepend a random block, and flip bits in order to make the last byte of the added block take all possible values (0 to 255).

The tests and the base value should at least cause three different states while and after decryption:

- Cipher text gets decrypted, resulting data is correct.
- Cipher text gets decrypted, resulting data is garbled and causes some exception or error handling in the application logic.
- Cipher text decryption fails due to padding errors.

Compare the responses carefully. Search especially for exceptions and messages which state that something is wrong with the padding. If such messages appear, the application contains a padding oracle. If the three different states described above are observable implicitly (different error messages, timing side-channels), there is a high probability that there is a padding oracle present at this point. Try to perform the padding oracle attack to ensure this.

### Example 3

- ASP.NET throws `System.Security.Cryptography.CryptographicException: Padding is invalid and cannot be removed.` if padding of a decrypted cipher text is broken.
- In Java a `javax.crypto.BadPaddingException` is thrown in this case.
- Decryption errors or similar can be possible padding oracles.

> A secure implementation will check for integrity and cause only two responses: `ok` and `failed`. There are no side channels which can be used to determine internal error states.
# Gray-Box Testing

Verify that all places where encrypted data from the client, that should only be known by the server, is decrypted. The following conditions should be met by such code:

1. The integrity of the cipher text should be verified by a secure mechanism, like HMAC or authenticated cipher operation modes like GCM or CCM.
2. All error states while decryption and further processing are handled uniformly.

# Example 4

[Visualization of the decryption process](https://erlend.oftedal.no/blog/poet/)

