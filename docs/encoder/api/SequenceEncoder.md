---
layout: default
title: SequenceEncoder
parent: Encoder Specification
---

## Interface SequenceEncoder

This section provides the description of the SEQUENCE encoder interface. The encoder implements methods for placing structure elements into the SEQUENCE. The elements will be in the order in which they are added to the sequence. Each method, except for the first four, has three overloaded variants. The first variant with a "value" parameter, such as <span class="method">Int32(int value)</span>, converts an argument of Java primitive type into a corresponding ASN.1 universal type encoding. The second overloaded method with a "tag" parameter, such as <span class="method">Int32(int tag, int value)</span>, creates an implicitly tagged element’s encoding for the "value" parameter. The tag class in this case is ContextSpecific. The tag value must be between 0 and 30 inclusive. And the third overloaded method with a "tc" parameter of type <span class="datatype">TagClass</span>, such as <span class="method">Int32(int tag, int value, TagClass tc)</span>, creates an implicitly tagged element’s encoding with a tag class specified in the tc parameter. Use this overload if you want to encode an implicitly tagged element with a tag class other than ContextSpecific. You can specify one of the three possible tag classes: ContextSpecific, Application or Private. The described scenario is repeated for each method that has three overloads, so the descriptions for the second and third overloads are omitted to avoid cluttering the manual with repetitive information.  

ASN.1 tagging is used to avoid ambiguity in the decoding process by providing context for elements and ensuring the correct interpretation of their types. A common scenario where ambiguity can arise is when two elements of the same type are adjacent and the first one is optional. In this case, the decoder does not know if the current element is the first optional or the second mandatory. Typically, applications address this issue by using implicit tags for optional elements, as it helps the decoder distinguish between the optional and mandatory elements. Implicitly tagged elements are created using the method overloads described above.  

In most cases, ambiguity can be avoided by using ASN.1 implicit tagging. However, implicit tagging redefines the original context of an element, which in turn can potentially introduce ambiguity. In order to explicitly define the context and ensure unambiguous decoding, explicit tagging is used. It also preserves the original context of the element.  

The <span class="datatype">SequenceEncoder</span> interface declares a <span class="method">Texp</span> method to obtain an encoder of type <span class="datatype">TexpEncoder</span> that allows an application to create an explicitly tagged element.

### <span class="subsection">Interface declaration:</span>
```java
public interface SequenceEncoder {
	int count();
	int getSize();
		
	TexpEncoder Texp();
	
	SequenceEncoder Sequence();
	SequenceEncoder Sequence(int tag);
	SequenceEncoder Sequence(int tag, TagClass tc);
	
	SequenceOfEncoder SequenceOf(UType uType);	
	SequenceOfEncoder SequenceOf(int tag, UType uType);	
	SequenceOfEncoder SequenceOf(int tag, UType uType, TagClass tc);	

	void Int32(int value);
	void Int32(int tag, int value);
	void Int32(int tag, int value, TagClass tc);

	void Int64(long value);
	void Int64(int tag, long value);
	void Int64(int tag, long value, TagClass tc);

	void Boolean(boolean value);
	void Boolean(int tag, boolean value);
	void Boolean(int tag, boolean value, TagClass tc);

	void Real32(float value);
	void Real32(int tag, float value);
	void Real32(int tag, float value, TagClass tc);
	
	void Real64(double value);
	void Real64(int tag, double value);
	void Real64(int tag, double value, TagClass tc);
	
	void UTF8String(String value);
	void UTF8String(int tag, String value);
	void UTF8String(int tag, String value, TagClass tc);

	void BMPString(String value);
	void BMPString(int tag, String value);
	void BMPString(int tag, String value, TagClass tc);
	
	void IA5String(String value);
	void IA5String(int tag, String value);
	void IA5String(int tag, String value, TagClass tc);
	
	void PrintableString(String value);
	void PrintableString(int tag, String value);
	void PrintableString(int tag, String value, TagClass tc);
	
	void GndTime(java.util.GregorianCalendar value);
	void GndTime(int tag, java.util.GregorianCalendar value);
	void GndTime(int tag, java.util.GregorianCalendar value, TagClass tc);
	
	void OctetString(byte[] buffer);
	void OctetString(int tag, byte[] buffer);
	void OctetString(int tag, byte[] buffer, TagClass tc);
	
	void OctetString(byte[] buffer, int offset, int length);
	void OctetString(int tag, byte[] buffer, int offset, int length);
	void OctetString(int tag, byte[] buffer, int offset, int length, TagClass tc);
	
	void OctetString(java.util.UUID uuid);
	void OctetString(int tag, java.util.UUID uuid);
	void OctetString(int tag, java.util.UUID uuid, TagClass tc);
	
	void Null(); 
}
```

### <span class="subsection">Interface method descriptions:</span>

Note that interface methods do not allow null for any of the parameters, otherwise an <span class="datatype">IllegalArgumentException</span> is thrown.

- ### <span class="method">count</span>  
```java
    int count()
```
Returns the number of elements in the SEQUENCE, excluding elements in nested containers;

- ### <span class="method">getSize</span>  
```java
    int getSize()
```
Returns the encoding size in bytes it will have when the encoding is created. The data within all nested containers is also counted. This method is useful if there is a limit for the size of the encoded data;

- ### <span class="method">Texp</span>  
```java
    TexpEncoder Texp()
```
The name "**Texp**" is derived from "**T**ag **exp**licit". This method returns an encoder of type <span class="datatype">TexpEncoder</span> that allows an application to add an explicitly tagged element to the current position of the SEQUENCE. The application must provide one and only one element to the encoder. The <span class="datatype">TexpEncoder</span> interface is described in section [Interface TexpEncoder]({{ site.baseurl }}{% link docs/encoder/api/TexpEncoder.md %});

- ### <span class="method">Sequence</span>  
```java
	SequenceEncoder Sequence()
	SequenceEncoder Sequence(int tag)
	SequenceEncoder Sequence(int tag, TagClass tc)
```
Creates a SEQUENCE encoder of type SequenceEncoder that adds a nested SEQUENCE to the current position of the SEQUENCE and returns the encoder to the application. The application can then add elements to the nested sequence through the encoder interface. The second overloaded Sequence method creates an encoder for an implicitly tagged sequence where the tag is of the ContextSpecific class. The third overloaded method allows an application to specify the tag class other than ContextSpecific;

- ### <span class="method">SequenceOf</span>  
```java
	SequenceOfEncoder SequenceOf(UType uType)
	SequenceOfEncoder SequenceOf(int tag, UType uType)
	SequenceOfEncoder SequenceOf(int tag, UType uType, TagClass tc)
```
Creates a SEQUENCE OF encoder of type <span class="datatype">SequenceOfEncoder</span> that adds a SEQUENCE OF element to the current position of the SEQUENCE and returns the encoder to the application. ASN.1 defines a SEQUENCE OF as an ordered collection of elements of the same type. That is, the elements added by the application to the SEQUENCE OF must be of the ASN.1 universal type specified when the encoder has been created. The encoder interface is described in section 
[Interface SequenceOfEncoder]({{ site.baseurl }}{% link docs/encoder/api/SequenceOfEncoder.md %}). The second overloaded <span class="method">SequenceOf</span> method creates an encoder for an implicitly tagged SEQUNCE OF element of ContextSpecific class. And, as usual, the third overload allows an application to specify the tag class other than ContextSpecific;

- ### <span class="method">Int32</span>  
```java
	void Int32(int value)
	void Int32(int tag, int value)
	void Int32(int tag, int value, TagClass tc)
```
Adds a 32-bit integer value to the current position of the SEQUENCE as an ASN.1 INTEGER element;

- ### <span class="method">Int64</span>  
```java
	void Int64(long value)
	void Int64(int tag, long value)
	void Int64(int tag, long value, TagClass tc)
```
Adds a 64-bit integer value to the current position of the SEQUENCE as an ASN.1 INTEGER element;

- ### <span class="method">Boolean</span>  
```java
	void Boolean(boolean value)
	void Boolean(int tag, boolean value)
	void Boolean(int tag, boolean value, TagClass tc)
```
Adds a Boolean value to the current position of the SEQUENCE as an ASN.1 BOOLEAN element;

- ### <span class="method">Real32</span>  
```java
	void Real32(float value)
	void Real32(int tag, float value)
	void Real32(int tag, float value, TagClass tc)
```
Adds a 32-bit real value to the current position of the SEQUENCE as an ASN.1 REAL element;

- ### <span class="method">Real64</span>  
```java
	void Real64(double value)
	void Real64(int tag, double value)
	void Real64(int tag, double value, TagClass tc)
```
Adds a 64-bit real value to the current position of the SEQUENCE as an ASN.1 REAL element;

- ### <span class="method">UTF8String</span>  
```java
	void UTF8String(String value)
	void UTF8String(int tag, String value)
	void UTF8String(int tag, String value, TagClass tc)
```
Adds a Java string to the current position of the SEQUENCE as an ASN.1 UTF8String element;

- ### <span class="method">BMPString</span>  
```java
	void BMPString(String value)
	void BMPString(int tag, String value)
	void BMPString(int tag, String value, TagClass tc)
```
Adds a Java string to the current position of the SEQUENCE as an ASN.1 BMPString element;

- ### <span class="method">IA5String</span>  
```java
	void IA5String(String value)
	void IA5String(int tag, String value)
	void IA5String(int tag, String value, TagClass tc)
```
Adds a Java string to the current position of the SEQUENCE as an ASN.1 IA5String element;

- ### <span class="method">PrintableString</span>  
```java
	void PrintableString(String value)
	void PrintableString(int tag, String value)
	void PrintableString(int tag, String value, TagClass tc)
```
Adds a Java string to the current position of the SEQUENCE as an ASN.1 PrintableString element;

- ### <span class="method">GndTime</span>  
```java
    void GndTime(GregorianCalendar value);
    void GndTime(int tag, GregorianCalendar value);
    void GndTime(int tag, GregorianCalendar value, TagClass tc);
```
Adds a GregorianCalendar value to the current position of the SEQUENCE as an ASN.1 element of type GeneralizedTime;

- ### <span class="method">OctetString</span>  
```java
	void OctetString(byte[] buffer)
	void OctetString(int tag, byte[] buffer)
	void OctetString(int tag, byte[] buffer, TagClass tc)
	
	void OctetString(byte[] buffer, int offset, int length)
	void OctetString(int tag, byte[] buffer, int offset, int length)
	void OctetString(int tag, byte[] buffer, int offset, int length, TagClass tc)
	
    void OctetString(java.util.UUID uuid)
	void OctetString(int tag, java.util.UUID uuid)
	void OctetString(int tag, java.util.UUID uuid, TagClass tc)
```
The first six overloads of the method add a Java byte array to the current position of the SEQUENCE as an ASN.1 OctetString element. The first triple of overloaded methods adds an entire byte array. The second triple of overloads allows an application to specify the offset and length. The last triple of overloads adds a UUID value as an OctetString element of 16 octets;

- ### <span class="method">Null</span>  
```java
	void Null()
```
If the ASN.1 syntax defines a given element as OPTIONAL, and the value to be specified is a Java null or the corresponding data is missing, an application can omit the element or specify ASN.1 NULL by calling the Null method. ASN.1 NULL is used when it is necessary to explicitly represent the absence of a value for an optional element, rather than omitting it from the encoding altogether.

