# Security_Negotiation-IP-network-

Implementing a client-server system which negotiates the use of a Caesar cypher, a Vigenere square, and XORencryption to encrypt a message passed between the client and server.

THE PROGRAM PERFORMS BY:

•	Reading the secret.txt file from the current running directory

•	Negotiate the encryption protocol to use, typically something like:

o	S: ALLOW CAESAR VIGNERE:max-length OTHER:Value

o	C: USE CAESAR

o	S: +OK

•	Pass a message from the client to the server using the encryption specified

•	Save the message to disk on the server encrypted and unencrypted

•	Send back the unencrypted message
