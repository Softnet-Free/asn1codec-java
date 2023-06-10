---
layout: default
title: Data Interchange Formats
nav_order: 2
---
# Data Interchange Formats

XML and JSON are two commonly used text-based formats to interchange structured information. JSON is especially popular in web API development. In Softnet, you can use whatever format you want. As an option, Softnet offers you to use the same format that the platform uses to interchange native messages, i.e., ASN.1. It is a well-established binary format that is extensively used in infrastructures that demand reliable communication. It provides precise specifications for how data should be represented and encoded. Particularly, ASN.1 offers the following advantages:
- Serialized data is typically much smaller and faster to process compared to a text-based format;
- It is a strongly-typed format, meaning that the data types are explicitly defined and enforced. This can help prevent errors and ensure that the data being transferred is consistent and valid. For example, JSON is a loosely-typed format, which means that it is more flexible but can also be more error-prone;
- It supports a rich data model, including support for complex data structures such as sequences, context-specific types, and choice types. This can make it easier to represent and transfer complex data in a structured way.  

If your application utilizes RPC and/or Application Events mechanisms of Softnet, you have the option to represent data using ASN.1 as offered by the platform. Alternatively, you can choose any other format and transmit serialized data as elements of type ASN.1 UTF8String or OctetString. The application then deserializes the data back to its original format on the receiving end.  

Finally, it is worth clarifying why Softnet uses the DER subset of the more general ASN.1 format. DER ensures unique encodings for the same value. Consequently, it ensures unique encodings for the same complex structured data, which guarantees that a hash function like SHA-1 will also produce a unique result from such encodings. Softnet leverages this feature to perform state synchronization between endpoints and the Softnet server. By checking the hash from the encoded data of remote dynamic structures, Softnet endpoints can detect any changes to their content.