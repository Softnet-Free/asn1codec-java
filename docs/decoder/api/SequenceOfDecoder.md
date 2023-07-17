---
layout: default
title: SequenceOfDecoder
parent: Decoder Specification
---

## Interface SequenceOfDecoder

This interface provides methods for decoding elements from an encoded SEQUENCE OF &lt;ElementType&gt; collection, where ElementType is a uniform ASN.1 type. An application specifies this type when it creates a decoder. On creation, a SEQUENCE OF decoder determines the total size of encoded elements enclosed in the SEQUENCE OF and sets the current element pointer to the first encoded element. Each decode method of the interface decodes an element at the current position as being of the type implied by the method. Note that implicitly or explicitly tagged elements are not allowed.  

### <span class="subsection">Interface declaration:</span>
```java
public interface SequenceOfDecoder {
    int count() throws FormatAsnException;
    boolean hasNext();
    void skip() throws AsnException;
    boolean isNull() throws AsnException;
	
    SequenceDecoder Sequence() throws AsnException;
    SequenceOfDecoder SequenceOf(UType uType) throws AsnException;
    int Int32() throws AsnException;
    int Int32(int minValue, int maxValue) throws AsnException;
    long Int64() throws AsnException;
    boolean Boolean() throws AsnException;
    float Real32() throws AsnException;
    float Real32(boolean checkForUnderflow) throws AsnException;
    double Real64() throws AsnException;
    double Real64(boolean checkForUnderflow) throws AsnException;
    String UTF8String() throws AsnException;
    String UTF8String(int requiredLength) throws AsnException;
    String UTF8String(int minLength, int maxLength) throws AsnException;
    String BMPString() throws AsnException;
    String BMPString(int requiredLength) throws AsnException;
    String BMPString(int minLength, int maxLength) throws AsnException;
    String IA5String() throws AsnException;
    String IA5String(int requiredLength) throws AsnException;
    String IA5String(int minLength, int maxLength) throws AsnException;
    String PrintableString() throws AsnException;
    java.util.GregorianCalendar GndTimeToGC() throws AsnException;
    byte[] OctetString() throws AsnException;
    byte[] OctetString(int requiredLength) throws AsnException;
    byte[] OctetString(int minLength, int maxLength) throws AsnException;
}
```

### <span class="subsection">Interface method descriptions:</span>

- ### <span class="method">count</span>  
```java
    int count() throws FormatAsnException;
```
Returns the number of elements in the encoding of a SEQUENCE OF.  
*Exceptions*: <span class="exception">FormatAsnException</span>;

- ### <span class="method">hasNext</span>  
```java
    boolean hasNext();
```
Checks if the SEQUENCE OF decoder has another element to decode.  
*Exceptions*: no exceptions;

- ### <span class="method">skip</span>  
```java
    void skip() throws AsnException;
```
Skips the current element of the encoded SEQUENCE OF and moves the pointer to the next element.  
*Exceptions*: <span class="exception">FormatAsnException</span>, <span class="exception">EndOfContainerAsnException</span>;

- ### <span class="method">isNull</span>  
```java
    boolean isNull() throws EndOfContainerAsnException;
```
Checks if the current element in the SEQUENCE OF encoding is ASN.1 NULL. Handling NULL elements is described in the [Handling ASN.1 NULL]({{ site.baseurl }}{% link docs/decoder/api/SequenceDecoder.md %}#handling-asn1-null) subsection of "Interface SequenceDecoder".  
*Exceptions*: <span class="exception">FormatAsnException</span>, <span class="exception">EndOfContainerAsnException</span>;

- ### <span class="method">Sequence</span>  
```java
    SequenceDecoder Sequence() throws AsnException;
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; decoder was created by specifying <span class="datatype">UType.Sequence</span> as a uniform type of the elements to decode a SEQUENCE OF SEQUENCE collection. The method creates a decoder of type <span class="datatype">SequenceDecoder</span> that allows an application to decode a nested SEQUENCE from the current position of the encoded collection of sequences and returns the decoder to the application.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);

- ### <span class="method">SequenceOf</span>  
```java
	SequenceOfDecoder SequenceOf(UType uType)
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; decoder was created by specifying <span class="datatype">UType.Sequence</span> as a uniform type of the elements to decode a SEQUENCE OF SEQUENCE collection. The method creates a decoder for a nested SEQUENCE OF uType collection, where uType is an ASN.1 type provided as an argument on the method call.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);

- ### <span class="method">Int32</span>  
```java
    int Int32() throws AsnException;
    int Int32(int minValue, int maxValue) throws AsnException;
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; decoder was created by specifying <span class="datatype">UType.Integer</span> as a uniform type of the elements to decode a SEQUENCE OF INTEGER collection. The method decodes the current element of the encoded collection as being of ASN.1 INTEGER and returns it to the application as a 32-bit Java integer. If the decoded value doesn’t fit to 32-bit integer, an exception of type <span class="exception">OverflowAsnException</span> is thrown. For example, this can happen if the value encoded in the current element was originally a 64-bit integer. As for the overload of Int32 with minValue and maxValue parameters, it allows an application to apply constraints on the value being decoded. If it doesn’t fit to the range [minValue, maxValue] inclusive, an exception of type <span class="exception">ConstraintAsnException</span> is thrown.  
*Exceptions*:
    - [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);
    - <span class="exception">OverflowAsnException</span>;
    - <span class="exception">ConstraintAsnException</span> can be thrown only by the <span class="method">Int32</span> overloads with minValue and maxValue constraint parameters;

- ### <span class="method">Int64</span>  
```java
    long Int64() throws AsnException;
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; decoder was created by specifying <span class="datatype">UType.Integer</span> as a uniform type of the elements to decode a SEQUENCE OF INTEGER collection. The method decodes the current element of the encoded collection as being of ASN.1 INTEGER and returns a 64-bit Java integer value to the application. If the decoded value doesn’t fit to 64-bit Java integer, an exception of type <span class="exception">OverflowAsnException</span> is thrown.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions), <span class="exception">OverflowAsnException</span>;

- ### <span class="method">Boolean</span>  
```java
    boolean Boolean() throws AsnException;
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; decoder was created by specifying <span class="datatype">UType.Boolean</span> as a uniform type of the elements to decode a SEQUENCE OF BOOLEAN collection. The method decodes the current element of the encoded collection as being of ASN.1 BOOLEAN and returns a Java boolean value to the application.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);

- ### <span class="method">Real32</span>  
```java
    float Real32() throws AsnException;
    float Real32(boolean checkForUnderflow) throws AsnException;
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; decoder was created by specifying <span class="datatype">UType.Real</span> as a uniform type of the elements to decode a SEQUENCE OF REAL collection. The method decodes the current element of the encoded collection as being of ASN.1 REAL and returns a 32-bit Java float value to the application. If the decoded value is out of range [Float.MAX_VALUE, Float.MIN_VALUE], an <span class="exception">OverflowAsnException</span> is thrown. Such can happen if the value encoded in this ASN.1 REAL element was originally a 64-bit floating point value. The other edge case is when an ASN.1 REAL element cannot be decoded as a 32-bit Java float without losing precision. If you want to detect loss of precision with throwing <span class="exception">UnderflowAsnException</span>, call the Real32 overload with true for the checkForUnderflow parameter. Losing precision can happen if the value encoded in ASN.1 REAL element was originally a 64-bit floating point value.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions), <span class="exception">OverflowAsnException</span>, <span class="exception">UnderflowAsnException</span>;

- ### <span class="method">Real64</span>  
```java
    double Real64() throws AsnException;
    double Real64(boolean checkForUnderflow) throws AsnException;
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; decoder was created by specifying <span class="datatype">UType.Real</span> as a uniform type of the elements to decode a SEQUENCE OF REAL collection. The method decodes the current element of the encoded collection as being of ASN.1 REAL and returns a 64-bit Java double to the application. If the decoded value is out of range [Double.MAX_VALUE, Double.MIN_VALUE], an <span class="exception">OverflowAsnException</span> is thrown. The other edge case is when an ASN.1 REAL element cannot be decoded as a 64-bit Java double without losing precision. If you want to detect loss of precision with throwing <span class="exception">UnderflowAsnException</span>, call the Real64 overload with true for the checkForUnderflow parameter.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions), <span class="exception">OverflowAsnException</span>, <span class="exception">UnderflowAsnException</span>;

- ### <span class="method">UTF8String</span>  
```java
    String UTF8String() throws AsnException;
    String UTF8String(int requiredLength) throws AsnException;
    String UTF8String(int minLength, int maxLength) throws AsnException;
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; decoder was created by specifying <span class="datatype">UType.UTF8String</span> as a uniform type of the elements to decode a SEQUENCE OF UTF8String collection. The method decodes the current element of the encoded collection as being of ASN.1 UTF8String and returns a Java String value to the application. If an application expects a string of a specific length, it can call the method overload with the requiredLength parameter. If the length is limited to a certain range, it can call the method overload with the minLength and maxLength parameters. If the decoded value does not conform to the constraints, an exception of type <span class="exception">ConstraintAsnException</span> is thrown.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions), <span class="exception">ConstraintAsnException</span>;

- ### <span class="method">BMPString</span>  
```java
    String BMPString() throws AsnException;
    String BMPString(int requiredLength) throws AsnException;
    String BMPString(int minLength, int maxLength) throws AsnException;
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; decoder was created by specifying <span class="datatype">UType.BMPString</span> as a uniform type of the elements to decode a SEQUENCE OF BMPString collection. The method decodes the current element of the encoded collection as being of ASN.1 BMPString and returns a Java String value to the application. If an application expects a string of a specific length, it can call the method overload with the requiredLength parameter. If the length is limited to a certain range, it can call the method overload with the minLength and maxLength parameters. If the decoded value does not conform to the constraints, an exception of type <span class="exception">ConstraintAsnException</span> is thrown.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions), <span class="exception">ConstraintAsnException</span>;

- ### <span class="method">IA5String</span>  
```java
    String IA5String() throws AsnException;
    String IA5String(int requiredLength) throws AsnException;
    String IA5String(int minLength, int maxLength) throws AsnException;
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; decoder was created by specifying <span class="datatype">UType.IA5String</span> as a uniform type of the elements to decode a SEQUENCE OF IA5String collection. The method decodes the current element of the encoded collection as being of ASN.1 IA5String and returns a Java String value to the application. If an application expects a string of a specific length, it can call the method overload with the requiredLength parameter. If the length is limited to a certain range, it can call the method overload with the minLength and maxLength parameters. If the decoded value does not conform to the constraints, an exception of type <span class="exception">ConstraintAsnException</span> is thrown.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions), <span class="exception">ConstraintAsnException</span>;

- ### <span class="method">PrintableString</span>  
```java
    String PrintableString() throws AsnException;
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; decoder was created by specifying <span class="datatype">UType.PrintableString</span> as a uniform type of the elements to decode a SEQUENCE OF PrintableString collection. The method decodes the current element of the encoded collection as being of ASN.1 PrintableString and returns a Java String value to the application.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);

- ### <span class="method">GndTimeToGC</span>  
```java
    java.util.GregorianCalendar GndTimeToGC() throws AsnException;
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; decoder was created by specifying <span class="datatype">UType.GeneralizedTime</span> as a uniform type of the elements to decode a SEQUENCE OF GeneralizedTime collection. The method decodes the current element of the encoded collection as being of ASN.1 GeneralizedTime and returns a <span class="datatype">GregorianCalendar</span> instance to the application.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);

- ### <span class="method">OctetString</span>  
```java
    byte[] OctetString() throws AsnException;
    byte[] OctetString(int requiredLength) throws AsnException;
    byte[] OctetString(int minLength, int maxLength) throws AsnException;
```
This method is allowed to call if the SEQUENCE OF &lt;ElementType&gt; decoder was created by specifying <span class="datatype">UType.OctetString</span> as a uniform type of the elements to decode a SEQUENCE OF OctetString collection. The method decodes the current element of the encoded collection as being of ASN.1 OctetString and returns a Java byte array to the application. If an application expects a byte array of a specific length, it can call the method overload with the requiredLength parameter. If the length is limited to a certain range, it can call the method overload with the minLength and maxLength parameters. If the decoded byte array does not conform to the constraints, an exception of type <span class="exception">ConstraintAsnException</span> is thrown.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions), <span class="exception">ConstraintAsnException</span>.

### <span class="subsection">A use case for the SequenceOfDecoder interface:</span>

This is a continuation of the [example]({{ site.baseurl }}{% link docs/encoder/api/SequenceOfEncoder.md %}#a-use-case-for-the-sequenceofencoder-interface) from "Interface SequenceOfEncoder ". That example demonstrates the process of encoding a list of integers using a SEQUENCE OF encoder. This example demonstrates the reverse process of decoding an encoded list of integers using a SEQUENCE OF decoder. The process is implemented in the decodeList method, which accepts the input data through the encoding parameter. It performs the following operations:

1. Calls the static <span class="method">Sequence</span> method of the base class <span class="datatype">ASNDecoder</span> to create a decoder for the outermost SEQUENCE. It’s named asnStructure;
2. Calls the <span class="method">SequenceOf</span> method of asnStructure with <span class="datatype">UType.Integer</span> as an argument to create a decoder for the SEQUENCE OF INTEGER collection. The decoder is named asnCollection;
3. In the "for" circle, fills the list of integers with decoded elements;
4. Returns the list of integers.

Here’s the ASN.1 data structure syntax:
```
DataStructure ::= SEQUENCE {
    integerCollection   SEQUENCE OF INTEGER
}
```
And here’s the decoding process:
```java
public ArrayList<Integer> decodeList(byte[] encoding) throws AsnException
{
    SequenceDecoder asnStructure = ASNDecoder.Sequence(encoding);
    SequenceOfDecoder asnCollection = asnStructure.SequenceOf(UType.Integer);
        
    ArrayList<Integer> integerList = new ArrayList<Integer>(); 
    while(asnCollection.hasNext()) {
        integerList.add(asnCollection.Int32());
    }		
    return integerList;
}
```