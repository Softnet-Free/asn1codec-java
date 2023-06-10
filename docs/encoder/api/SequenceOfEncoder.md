---
layout: default
title: SequenceOfEncoder
parent: Encoder Specification
---

## Interface SequenceOfEncoder

This section provides the description of the SEQUENCE OF encoder interface. The encoder implements methods that allow an application to add Java data type instances to an ordered collection of elements of a uniform ASN.1 type. An application specifies the SEQUENCE OF element’s type when it creates the encoder. The SEQUENCE OF container does not allow tagged elements as the type must be uniform. As you can see below, the interface does not contain methods for tagged elements.

### <span class="subsection">Interface declaration:</span>
```java
public interface SequenceOfEncoder {
	int count();
	int getSize();
	SequenceEncoder Sequence();
	SequenceOfEncoder SequenceOf(UType uType);
	void Int32(int value);
	void Int64(long value);
	void Boolean(boolean value);
	void Real32(float value);
	void Real64(double value);
	void UTF8String(String value);
	void BMPString(String value);
	void IA5String(String value);
	void PrintableString(String value);
	void GndTime(java.util.GregorianCalendar value);
	void OctetString(byte[] buffer);
	void OctetString(byte[] buffer, int offset, int length);
	void Null();
}
```

### <span class="subsection">Interface method descriptions:</span>

- ### <span class="method">count</span>  
```java
    int count()
```
Returns the number of elements in the SEQUENCE OF container. If the uniform type of the container elements is SEQUENCE, that is the encoder is created for the SEQUENCE OF SEQUENCE construction (by calling the method <span class="method">SequenceOf</span> with <span class="datatype">UType.Sequence</span> as an argument), then the count method excludes the number of elements in nested containers;

- ### <span class="method">getSize</span>  
```java
    int getSize()
```
Returns the encoding size in bytes it will have when the encoding is created. If the uniform type of the container elements is SEQUENCE, the data within all nested containers is also counted;

- ### <span class="method">Sequence</span>  
```java
    SequenceEncoder Sequence()
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; encoder was created by specifying <span class="datatype">UType.Sequence</span> as a uniform type of the elements to encode the SEQUENCE OF SEQUENCE collection. The method creates a SEQUENCE encoder of type <span class="datatype">SequenceEncoder</span> that adds a nested SEQUENCE to the current position of the enclosing SEQUENCE OF SEQUENCE and returns the encoder to the application;

- ### <span class="method">SequenceOf</span>  
```java
	SequenceOfEncoder SequenceOf(UType uType)
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; encoder was created by specifying <span class="datatype">UType.Sequence</span> as a uniform type of the elements to encode the SEQUENCE OF SEQUENCE collection. The method creates a SEQUENCE OF encoder of type <span class="datatype">SequenceOfEncoder</span> that adds a nested SEQUENCE OF to the current position of the enclosing SEQUENCE OF SEQUENCE and returns the encoder to the application;

- ### <span class="method">Int32</span>  
```java
	void Int32(int value)
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; encoder was created by specifying <span class="datatype">UType.Integer</span> as a uniform type of the elements to encode the SEQUENCE OF INTEGER collection. The method adds a 32-bit integer value to the current position of the collection as an ASN.1 INTEGER element;

- ### <span class="method">Int64</span>  
```java
	void Int64(int value)
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; encoder was created by specifying <span class="datatype">UType.Integer</span> as a uniform type of the elements to encode the SEQUENCE OF INTEGER collection. The method adds a 64-bit integer value to the current position of the collection as an ASN.1 INTEGER element;

- ### <span class="method">Boolean</span>  
```java
	void Boolean(boolean value)
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; encoder was created by specifying <span class="datatype">UType.Boolean</span> as a uniform type of the elements to encode the SEQUENCE OF BOOLEAN collection. The method adds a Java boolean value to the current position of the collection as an ASN.1 BOOLEAN element;

- ### <span class="method">Real32</span>  
```java
	void Real32(float value)
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; encoder was created by specifying <span class="datatype">UType.Real</span> as a uniform type of the elements to encode the SEQUENCE OF REAL collection. The method adds a 32-bit real value to the current position of the collection as an ASN.1 REAL element;

- ### <span class="method">Real64</span>  
```java
	void Real64(double value)
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; encoder was created by specifying <span class="datatype">UType.Real</span> as a uniform type of the elements to encode the SEQUENCE OF REAL collection. The method adds a 64-bit real value to the current position of the collection as an ASN.1 REAL element;

- ### <span class="method">UTF8String</span>  
```java
	void UTF8String(String value)
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; encoder was created by specifying <span class="datatype">UType.UTF8String</span> as a uniform type of the elements to encode the SEQUENCE OF UTF8String collection. The method adds a Java string to the current position of the collection as an ASN.1 UTF8String element;

- ### <span class="method">BMPString</span>  
```java
	void BMPString(String value)
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; encoder was created by specifying <span class="datatype">UType.BMPString</span> as a uniform type of the elements to encode the SEQUENCE OF BMPString collection. The method adds a Java string to the current position of the collection as an ASN.1 BMPString element;

- ### <span class="method">IA5String</span>  
```java
	void IA5String(String value)
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; encoder was created by specifying <span class="datatype">UType.IA5String</span> as a uniform type of the elements to encode the SEQUENCE OF IA5String collection. The method adds a Java string to the current position of the collection as an ASN.1 IA5String element;

- ### <span class="method">PrintableString</span>  
```java
	void PrintableString(String value)
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; encoder was created by specifying <span class="datatype">UType.PrintableString</span> as a uniform type of the elements to encode the SEQUENCE OF PrintableString collection. The method adds a Java string to the current position of the collection as an ASN.1 PrintableString element;

- ### <span class="method">GndTime</span>  
```java
	void GndTime(java.util.GregorianCalendar value)
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; encoder was created by specifying <span class="datatype">UType.GeneralizedTime</span> as a uniform type of the elements to encode the SEQUENCE OF GeneralizedTime collection. The method adds a GregorianCalendar value to the current position of the collection as an ASN.1 GeneralizedTime element;

- ### <span class="method">OctetString</span>  
```java
	void OctetString(byte[] buffer)
	void OctetString(byte[] buffer, int offset, int length)
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; encoder was created by specifying <span class="datatype">UType.OctetString</span> as a uniform type of the elements to encode the SEQUENCE OF OctetString collection. The method adds a Java byte array to the current position of the collection an ASN.1 OctetString element. The first overload adds an entire byte array while the second overload allows an application to specify the offset and length;

- ### <span class="method">Null</span>  
```java
	void Null()
```
If the ASN.1 syntax allows ASN.1 NULL as a collection element, and the value to be specified is a Java null or the corresponding data is missing, an application can omit the element or specify ASN.1 NULL by calling the Null method.

### <span class="subsection">Explanation 1:</span>

If the element type of a SEQUENCE OF &lt;ElementType&gt; collection is SEQUENCE, that is, you have a SEQUENCE OF SEQUENCE collection, then that collection can contain both SEQUENCE and SEQUENCE OF elements.  

The following example shows the process of encoding a 2x2 matrix. The matrix element is defined as a SEQUENCE structure that has a single element, rows, of the SEQUENCE OF SEQUENCE collection type. The first raw of the matrix, raw1, is created as a SEQUENCE OF INTEGER collection, while the second element, raw2, is created as a SEQUENCE structure, though it should also have been a SEQUENCE OF INTEGER collection:

```java
    ASNEncoder asnEncoder = new ASNEncoder();

    SequenceEncoder matrix = asnEncoder.Sequence();
    SequenceOfEncoder rows = matrix.SequenceOf(UType.Sequence);

    SequenceOfEncoder row1 = rows.SequenceOf(UType.Integer);  
    row1.Int32(1);
    row1.Int32(2);

    SequenceEncoder row2 = columns.Sequence();  
    row2.Int32(3);
    row2.Int32(4);

    byte[] encoding = asnEncoder.getEncoding();
```
The appropriate ASN.1 syntax:
```
    Matrix ::= SEQUENCE {
        raws :: = SEQUENCE OF Row (SIZE(2))
    }
    Raw ::= SEQUENCE OF INTEGER (SIZE(2))
```
The matrix instance encoded in the example:
```
matrix Matrix ::= {
    rows { { 1, 2 }, { 3, 4 } }
}
```

### <span class="subsection">Explanation 2:</span>

If the element type of a SEQUENCE OF <ElementType> collection is INTEGER, that is, you have a SEQUENCE OF INTEGER collection, then an application can add to the collection both 32-bit and 64-bit integers like in this example:
```java
    SequenceOfEncoder integerNumbers = someSequence.SequenceOf(UType.Integer);
    integerNumbers.Int32(Integer.MAX_VALUE);
    integerNumbers.Int64(Long.MAX_VALUE);
```
The developer should be aware that a value encoded as a 64-bit integer may cause an overflow error if it is decoded as a 32-bit integer;

### <span class="subsection">Explanation 3:</span>

If the element type of a SEQUENCE OF <ElementType> collection is REAL, that is, you have a SEQUENCE OF REAL collection, then an application can add to the collection both 32-bit and 64-bit floating point values like in this example:
```java
    SequenceOfEncoder realNumbers = someSequence.SequenceOf(UType.Real);
    realNumbers.Real32(Float.MAX_VALUE);
    realNumbers.Real64(Double.MIN_VALUE);
```
The developer should be aware that a Java double encoded as a 64-bit real may cause an overflow or underflow error if it is decoded as a 32-bit real;

### <span class="subsection">A use case for the SequenceOfEncoder interface:</span>

This example encodes a list of integer elements using the SEQUENCE OF encoder. The process is implemented in the encodeList method, which accepts the input data through the integerList parameter. It then performs the following operations:
1. Instantiates a base encoder class <span class="datatype">ASNEncoder</span> as asnEncoder. By the way, this encoder creates the outermost SEQUENCE encoder. It’s named asnStructure;
2. Calls the <span class="method">SequenceOf</span> method of asnStructure with <span class="datatype">UType.Integer</span> as an argument to create an encoder for the SEQUENCE OF INTEGER. The encoder is named asnCollection;
3. In the "for" circle, fills the collection with the integer list items;
4. Encodes the data structure and returns the encoding.

Here’s the ASN.1 data structure syntax:
```
    DataStructure ::= SEQUENCE {
        integerCollection	SEQUENCE OF INTEGER
    }
```
And here’s the encoding process:
```java
    public byte[] encodeList(ArrayList<Integer> integerList)
    {
        ASNEncoder asnEncoder = new ASNEncoder();
        SequenceEncoder asnDataStructure = asnEncoder.Sequence();
        SequenceOfEncoder asnCollection = asnDataStructure.SequenceOf(UType.Integer);
        for(int item: integerList) {
            asnCollection.Int32(item);
        }	        
        return asnEncoder.getEncoding();
    }
```