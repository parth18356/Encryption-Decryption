# Encryption-Decryption


In Project 0 the character set is {A, B, C}. In projects Project 1 and Project 2, the character set
consists of lower case English letters, viz. {a, b, ..., z}. In all cases the plaintext you will work with
should be “recognizable”. To make the text recognizable, the plaintext p should satisfy some
property, π, that can be checked by an algorithm or a program. This property π can take
different forms. The simplest form where plaintext == original_text ||original_text, where || is
the concatenation operator, will not work. Therefore, you should identify or construct a good
hash function Hash(.) that you may use to construct a plaintext, p = (string, Hash(string)), where
“string” is the original_text. The Hash function should be such that the received or decrypted
string can be recognized by an algorithm or program.
