---
layout: default
title: ASNDecoder
parent: Decoder Specification
---

## Class ASNDecoder

The class has two overloaded static methods <span class="method">Sequence</span>:
```java
    Public static SequenceDecoder Sequence(byte[] encoding) throws FormatAsnException
    public static SequenceDecoder Sequence(byte[] encoding, int offset) throws FormatAsnException
```
These methods take a byte buffer containing an ASN.1 DER encoding and returns the outermost SEQUENCE decoder of type <span class="datatype">SequenceDecoder</span> corresponding to a data structure to be decoded. This decoder allows an application to decode the structure elements from the encoding, which may also include nested container elements.  

The first overload of the method assumes that the ASN.1 encoding starts at byte index 0. It extracts the length that the encoding should have from the <tag, length, value> triple of the outermost SEQUENCE, which is the first element in the encoding.  

The second overload of the <span class="method">Sequence</span> method works in exactly the same way, but the input buffer has an offset.  

*Exceptions*: <span class="exception">FormatAsnException</span>, <span class="exception">TypeMismatchAsnException</span>.

### <span class="subsection">A use case for the ASNDecoder class:</span>

In the end of section "Class ASNEncoder", [A use case for the ASNEncoder class]({{ site.baseurl }}{% link docs/encoder/api/ASNEncoder.md %}#a-use-case-for-the-asnencoder-class) shows the process of encoding an instance of a data structure that represents a book in the library. To demonstrate the use case for the ASNDecoder class, we provide an example of decoding the ASN.1 encoding created in that example.  

Let's recall the ASN.1 structure that represents a book in the library:
```
    Book ::= SEQUENCE {
        name	  UTF8String,
        author	  UTF8String,
        pages	  INTEGER,
        isbn	  PrintableString
    }
```
Here’s the Java code that decodes an ASN.1 DER encoded instance of the Book structure:
```java
    public class Book {
        String	  name;
        String	  author;
        int	  pages;
        String	  isbn;
    }

    public Book decodeBook(byte[] encoding) throws AsnException {
        Book book = new Book();
        SequenceDecoder asnBook = ASNDecoder.Sequence(encoding);
        book.name = asnBook.UTF8String();
        book.author = asnBook.UTF8String();
        book.pages = asnBook.Int32();
        book.isbn = asnBook.PrintableString();		
        return book;
    }	
```