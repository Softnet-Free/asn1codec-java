---
layout: default
title: Decoder Specification
nav_order: 6
has_children: true
---
# Decoder Specification

The Decoder module provides a base decoder class and a set of interfaces with corresponding implementations to decode an ASN.1 DER encoding. The base decoder implements the entry point where the decoding process starts. It takes an encoding, creates a decoder for the outermost container of a data structure, and returns it to the application. To decode the structure elements, the module provides a set of ASN.1 type decoders available through the API. It is expected that the encoding represents an instance of a data structure whose elements enclosed in an ASN.1 SEQUENCE container.  

Here's short descriptions of the Decoder’s base class and interfaces, and the following sections provide their specifications:

- <span class="datatype">ASNDecoder</span> is a base decoder class with static methods representing the entry point to the decoding process;
- <span class="datatype">SequenceDecoder</span> is a SEQUENCE decoder interface. It declares a set of methods for decoding ASN.1 DER encoded structure elements into basic Java types, as well as for creating decoders for nested containers;
- <span class="datatype">SequenceOfDecoder</span> is a SEQUENCE OF &lt;ElementType&gt; decoder interface. A decoder of this type decodes binary data as a collection of elements of the same ASN.1 type, i.e., ElementType. The decoded elements are usually placed into a Java array or list;
- <span class="datatype">TexpDecoder</span> is a decoder interface for decoding an explicitly tagged element.

To use the Decoder module in your application, include the codec ‘jar’ file in your project and import the 'softnet.asn' package.

There is a set of exceptions derived from <span class="exception">AsnException</span> that the Decoder module supports:
- <span class="exception">FormatAsnException</span> – there is a format error in the ASN.1 DER encoded input data;
- <span class="exception">EndOfContainerAsnException</span> – there was an attempt to perform an operation on the current element while the pointer to the current element of the encoded container has already reached the end;
- <span class="exception">TypeMismatchAsnException</span> – possible reasons: the ASN.1 tag class of the current element in the encoding doesn’t match to the class implied by the called method; the ASN.1 tag class is Universal but the tag value doesn’t match the ASN.1 Universal type implied by the called method, regardless of whether the tag class validation is used;
- <span class="exception">EndNotReachedAsnException</span> can be thrown by the <span class="method">end()</span> method of a container decoder. A decoding process relies on the ASN.1 syntax of the corresponding data structure when decoding the elements of an ASN.1 encoded container. When the last element of the structure is decoded, the process can call the <span class="method">end()</span> method of the interface, which means that if there are more elements in the encoded container, the process consider this as an exception that should be handled;
- <span class="exception">UnderflowAsnException</span> is thrown if loss of precision is detected while decoding ASN.1 REAL value into a Java floating point type and the decoding process specified this event as exceptional by calling Real32 or Real64 method with true for the checkForUnderflow parameter;
- <span class="exception">OverflowAsnException</span> is thrown if a decoded value of type ASN.1 INTEGER or REAL is outside the [min, max] range of values representable by the Java type to which the value is being converted;
- <span class="exception">ConstraintAsnException</span> is thrown if the decoded value does not meet the constraint applied by the application. This can be a limit on the length of a string or an integer value.  

### <span class="subsection">Standard Exceptions:</span>

The first three exceptions described above are exceptions that can be thrown by any of the API methods that decode elements from an encoding. For brevity, these exceptions are refered to as Standard Exceptions:  
- <span class="exception">FormatAsnException</span>;
- <span class="exception">EndOfContainerAsnException</span>;
- <span class="exception">TypeMismatchAsnException</span>.
