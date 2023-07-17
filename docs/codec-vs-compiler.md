---
layout: default
title: ASN.1 Codec vs Compiler
nav_order: 3
---
# ASN.1 Codec vs Compiler

An ASN.1 compiler takes ASN.1 syntax as input and generates source code in a programming language (such as C or Java) that can be used to manipulate data structures defined using ASN.1. The generated code can be used to encode and decode data in a format that conforms to the ASN.1 specification. An ASN.1 compiler could obviously be useful as a development tool.  

Softnet ASN.1 Codec provides functions for encoding and decoding data in ASN.1 DER format. It does not generate code; rather, it provides pre-written functions that can be used to manipulate data structures defined using ASN.1 syntax.  

The codec is divided into two modules -
[Encoder]({{ site.baseurl }}{% link docs/encoder/encoder.md %})
and
[Decoder]({{ site.baseurl }}{% link docs/decoder/decoder.md %}). Both modules defined in the 'softnet.asn' package. So, to use them in your application, include the codec 'jar' file in your project and import the 'softnet.asn' package.