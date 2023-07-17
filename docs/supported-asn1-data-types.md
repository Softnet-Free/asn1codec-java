---
layout: default
title: Supported ASN.1 Data Types
nav_order: 4
---
# Supported ASN.1 Data Types

The codec supports a set of ASN.1 data types which are enumerated in UType:
```java
public enum UType {
	Boolean(1),
	Integer(2),
	OctetString(4),
	Null(5),
	Real(9),
	UTF8String(12),
	Sequence(16),
	PrintableString(19),
	IA5String(22),
	GeneralizedTime(24),
	BMPString(30);

	public final int tag;
}
```
The following tag class enumeration is used in encode/decode operations on implicitly or explicitly tagged elements:
```java
public enum TagClass {
	ContextSpecific,
	Application,
	Private
}
```
Here’s the description of supported ASN.1 data types:
- ### <span class="datatype">Boolean</span>
The BOOLEAN type represents a logical value. It can have two possible values: TRUE or FALSE;
- ### <span class="datatype">Integer<span>
The INTEGER type represents integer values. The current implementation of the codec supports signed integers up to 64 bits;
- ### <span class="datatype">OctetString<span>
This ASN.1 type is used to represent an ordered sequence of octets (eight-bit bytes). In Java, OctetString is typically represented using the byte[] (byte array) data type;
- ### <span class="datatype">Null<span>
ASN.1 NULL is used when it is necessary to explicitly represent the absence of a value for an optional element, rather than omitting it from the encoding altogether. An element for which the encoding process specifies ASN.1 NULL must be defined as OPTIONAL;
- ### <span class="datatype">Real<span>
ASN.1 REAL is a data type used to represent floating-point numbers. The current implementation of the codec supports signed floating-point numbers up to 64 bits;
- ### <span class="datatype">UTF8String<span>
ASN.1 UTF8String is a string type that can represent any character in the Unicode character set, including those beyond the Basic Multilingual Plane (BMP) that are not representable in BMPString;
- ### <span class="datatype">Sequence<span>
ASN.1 SEQUENCE is an ordered collection of elements, where each element is identified by a unique name and a data type. It is the equivalent of a data structure in programming languages such as Java and C.
The codec also supports SEQUENCE OF &lt;ElementType&gt; as an extension of SEQUENCE, which is an ordered collection of elements of type ElementType, where ElementType is a uniform ASN.1 type. It is the equivalent of an array or list in programming languages such as Java and C;
- ### <span class="datatype">PrintableString<span>
In ASN.1, PrintableString is a character string type that allows only a subset of printable ASCII characters. Specifically, it allows the following characters: A-Z a-z 0-9 space ' ( ) + , - . / : = ?
- ### <span class="datatype">IA5String<span>
ASN.1 IA5String is a string data type that represents a character string consisting of IA5 (International Alphabet No. 5) characters. IA5 characters include the 7-bit ASCII characters in the range of 0 to 127, inclusive.
- ### <span class="datatype">GeneralizedTime<span>
ASN.1 GeneralizedTime is a data type that represents a date and time value with up to microsecond precision and expressed in Coordinated Universal Time (UTC).
- ### <span class="datatype">BMPString<span>
ASN.1 BMPString is a string data type that contains 16-bit characters from the Unicode Basic Multilingual Plane (BMP), which includes characters in the range U+0000 to U+FFFF.

