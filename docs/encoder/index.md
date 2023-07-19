---
layout: default
title: Encoder Specification
nav_order: 5
has_children: true
---

# Encoder Specification

<p>The Encoder module provides a base encoder class and a set of interfaces with their implementations to encode structured data. The base encoder has two functions: creating the encoder for the outermost container of a data structure, and, after the container is filled with elements, creating the ASN.1 DER encoding. To add the elements to the container, the module provides a set of ASN.1 type encoders available through the API. Typically, a data structure defined in ASN.1 syntax consists of the outermost SEQUENCE container and the structure elements enclosed within the container, including nested containers.</p>
Here's short descriptions of the Encoder’s base class and interfaces, and the following sections provide their specifications:
- <span class="datatype">ASNEncoder</span> is a base encoder class, when instantiated, provides an application with an encoder of type SequenceEncoder that represents the outermost SEQUENCE of a data structure. After the sequence is filled with elements, the base encoder can create an ASN.1 DER encoding;
- <span class="datatype">SequenceEncoder</span> is a SEQUENCE encoder interface. SEQUENCE is an ordered collection of elements, where each element is identified by a unique name and a data type. The SEQUENCE encoder has a set of methods for encoding data represented in the basic Java types as well as for creating nested containers;
- <span class="datatype">SequenceOfEncoder</span> is a SEQUENCE OF &lt;ElementType&gt; encoder interface. SEQUENCE OF &lt;ElementType&gt; is an ordered collection of elements of a uniform ASN.1 type. This encoder is used to encode Java arrays and lists. It implements a set of methods for encoding data represented in the basic Java types as well as for creating nested containers. However, the allowed Java types in the collection are only those that correspond to ElementType;
- <span class="datatype">TexpEncoder</span> is an encoder interface for encoding an explicitly tagged element. This encoder must contain one and only one element to encode.  

To use the Encoder module in your application, include the codec ‘jar’ file in your project and import the 'softnet.asn' package.
