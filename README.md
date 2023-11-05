# Softnet ASN.1 Codec (Java)

This is the Java source code for Softnet ASN.1 Codec. It was developed as part of the Softnet Free project, but can also be used on its own. The product does not support ASN.1 compiler features. The encoder takes data represented using Java data types as input and converts it into an ASN.1 DER encoded format. The decoder, in turn, is designed to handle input data that adheres to the ASN.1 DER format. [The Developer Guide to Softnet ASN.1 Codec (Java)](https://softnet-free.github.io/asn1codec-java/) explains how to use the codec in applications.  

Softnet ASN.1 Codec (Java) utilizes data types and packages from Java SE 1.7. If your application is assumed to use the Java 1.7 or Java 1.8 runtime, you can also target this library to that runtime. In this case, to compile this library you need to comment out the code in 'src/module-info.java'. Otherwise, starting with Java 9, Java applications use Java Platform Module System (JPMS), and this code is required to declare module-related information and dependencies. You can also download executables compiled in Java 1.7 and java 9 from the [releases](https://github.com/Softnet-Free/asn1codec-java/releases) section of the repository.

Softnet ASN.1 Codec (Java) is free software. You can redistribute and/or modify it under the terms of the Apache License, Version 2.0.
