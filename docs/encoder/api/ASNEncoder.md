---
layout: default
title: ASNEncoder
parent: Encoder Specification
---

## Class ASNEncoder

The process of encoding a data structure instance begins with instantiation of the <span class="datatype">ASNEncoder</span> class. It implements the following methods:

- ### <span class="method">Sequence</span>  
```java
public SequenceEncoder Sequence()
```
The method returns an encoder of type SequenceEncoder for the outermost SEQUENCE. This sequence will contain all data structure elements to be encoded, including nested structures;

- ### <span class="method">getEncoding</span>  
```java
public byte[] getEncoding()
public byte[] getEncoding(int prefixSize)
```
The method returns the ASN.1 DER encoding of the outermost sequence containing the data structure elements, including any nested structures. The second overload does the same, but adds the number of bytes specified by the prefixSize parameter to the beginning of the encoding;

- ### <span class="method">ASNEncoder</span>  
```java
public ASNEncoder()
```
This is a constructor without parameters.

### A use case for the ASNEncoder class:

Let's examine a scenario of utilizing the class. Assume we have a simple data structure in ASN.1 that represents a book in the library:
```
Book ::= SEQUENCE {
	name		UTF8String,
	author		UTF8String,
	pages		INTEGER,
	isbn		PrintableString
}
```
Here’s the Java code that implements the Book instance encoding:

```java
public class Book {
   String	name;
   String	author;
   int		pages;
   String	isbn;
}
public byte[] encodeBook(Book book) {
	ASNEncoder asnEncoder = new ASNEncoder();
	SequenceEncoder bookEncoder = asnEncoder.Sequence();		
	bookEncoder.UTF8String(book.name);
	bookEncoder.UTF8String(book.author);
	bookEncoder.Int32(book.pages);
	bookEncoder.PrintableString(book.isbn);		
	return asnEncoder.getEncoding();
}
```