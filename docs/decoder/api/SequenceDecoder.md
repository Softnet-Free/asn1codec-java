---
layout: default
title: SequenceDecoder
parent: Decoder Specification
---

## Interface SequenceDecoder

This interface provides methods for decoding elements from the ASN.1 DER encoded SEQUENCE. On creation, the SEQUENCE decoder determines the total size of encoded elements enclosed in the SEQUENCE and sets the current element pointer to the first element. Each decode method of the interface, starting with <span class="method">Sequence</span>, decodes an element at the ASN.1 encoding’s current position as being of the type implied by the method. For example, if an application calls <span class="method">Int32()</span> or <span class="method">Int64()</span>, the element is decoded as being of type ASN.1 INTEGER.  

An encoding of ASN.1 elements consists of <tag, length, value> triples. The "tag" field represents the contextual information required to unabiguously identify the current encoded element and its data type among other elements of the sequence. It consists of three parts: the tag class, the "constructed" flag, and the tag value. Possible tag classes are Universal, ContextSpecific, Application, and Private. The "lenght" field specifies the size of the encoded value in bytes and the "value" field contains the actual data of the element.  

### <span class="subsection">Tag class validation:</span>

ASN.1 elements can be decoded with or without validating the tag class encoded in the triple’s "tag" field. To implement this functionality, the interface provides two overloads of each decode method, starting with <span class="method">Texp</span>, without and with an additional parameter "tc" of type <span class="datatype">TagClass</span>, such as <span class="method">Int32()</span> and <span class="method">Int32(TagClass tc)</span>. if an application does not use the tag class validation, methods without the "tc" parameter can decode elements with any tag class. For example, the <span class="method">Int32()</span> method decodes the current encoded element, regardless of whether the tag class is Universal, ContextSpecific or any other. Othewise, if an application uses the tag class validation, methods with the "tc" parameter allow the application to validate that the tag class of the encoded element is equal to the class specified in the "tc" parameter. One of three classes can be specified: ContextSpecific, Application, or Private. As for the Universal class, if the tag class validation is set to true, the decode methods without the "tc" parameter are used to decode elements that have a Universal tag class and a tag value corresponding to the ASN.1 Universal type implied by the called method. For example, if an application calls the <span class="method">Int32(TagClass tc)</span> method with the <span class="datatype">TagClass.ContextSpecific</span> argument, the method decodes the current element from the encoding as being of type ASN.1 INTEGER, verifying that its tag class is ContextSpecific. Whereas, the <span class="method">Int32()</span> method without the "tc" parameter decodes the element, verifying that its tag class is ASN.1 Universal.  

When the tag class validation is used, if the element’s tag class doesn’t match the expected one, the exception of type <span class="exception">TypeMismatchAsnException</span> is thrown. Also, this exception is thrown if the tag class is Universal and the tag value doesn’t match the ASN.1 Universal type implied by the called method, regardless of whether the tag class validation is used.  

Typically, if the data source is reliable, it is not required to use tag class validation, which allows you to make the source code less verbose. However, if the source of the ASN.1 encoding tends to make some changes to the data structures or is unreliable, then it is recommended to use the tag class validation. The corresponding parameter of the SEQUENCE decoder is set to true or false by calling the <span class="method">validateClass</span> method. The parameter's value is also inherited by child containers at the time of their creation. But the scope of the parameter does not extend to child containers after their creation. At the start of decoding process, you can set the required value of the tag class validation parameter for the outermost SEQUENCE decoder and all nested containers will inherit this value. Initially, when the outermost SEQUENCE decoder is created, this parameter is set to false.

### <span class="subsection">Decoding optional and CHOICE elements:</span>

A common approach to decoding optional and CHOICE elements involves checking the presence of these elements in the encoded data and handling them accordingly during the decoding process. For checking the presence of tagged elements, the interface provides the <span class="method">exists(int tag)</span> method. It returns true, if the element exists. The tag can be of any class except Universal. If the tagged element exists, the tag class can be checked using the <span class="method">isClass(TagClass tc)</span> method if necessary. For checking the presence of Universal type elements, the interface provides the <span class="method">exists(UType type)</span> method.  

Decoding an optional element involves the following steps:
- Check if the optional element is present by examining its tag. Use for this the <span class="method">exists(int tag)</span> or <span class="method">exists(UType type)</span> method;
- If the element is present, decode it based on its type specified by the context;
- If the element is absent, handle it based on the application's requirements, such as using a default value or marking it as null.

Decoding a CHOICE element involves the following steps:
- Determine the specific choice within the CHOICE type by examining the tag of the encoded element. Use for this the <span class="method">exists(int tag)</span> and/or <span class="method">exists(UType type)</span> methods;
- Based on the determined choice, decode the corresponding element using its specified type.
- If none of the choices is determined, the input data is invalid.
In the last subsection there is an example that demonstrate how to decode optional and CHOICE elements.

### <span class="subsection">Handling ASN.1 NULL:</span>

If a given ASN.1 element is defined as OPTIONAL, the encoding process could provide ASN.1 NULL instead of omitting the element. If such an element can be at the encoding’s current position, the decoding process should first check if it is ASN.1 NULL by calling the <span class="method">isNull()</span> method of the interface. If NULL is detected, the decoding process must call the <span class="method">skip()</span> method, then go to the handling NULL, which can be done in one of the following ways:
- If the presence of NULL is expected and it is not relevant to the application logic, it can be simply skipped;
- In some cases, NULL values can be interpreted as the absence of a value or as an indicator that no meaningful data is present;
- If a NULL value carries important information or need to be preserved in the decoded output, the application can include it as part of the decoded data structure.

In the [last subsection]({{ site.baseurl }}{% link docs/decoder/api/SequenceDecoder.md %}#a-use-case-for-the-sequencedecoder-interface) there is an example that demonstrates how a decoding process can handle ASN.1 NULL.

### <span class="subsection">Interface declaration:</span>
```java
public interface SequenceDecoder {
    int count() throws FormatAsnException;
    boolean hasNext();
    void skip() throws AsnException;
    void end() throws EndNotReachedAsnException;

    boolean exists(int tag);
    boolean exists(UType type);

    boolean isUniversal() throws EndOfContainerAsnException;
    boolean isClass(TagClass tc) throws EndOfContainerAsnException;
    boolean isNull() throws EndOfContainerAsnException;

    void validateClass(boolean flag);
	
    TexpDecoder Texp() throws AsnException;
    TexpDecoder Texp(TagClass tc) throws AsnException;
	
    SequenceDecoder Sequence() throws AsnException;
    SequenceDecoder Sequence(TagClass tc) throws AsnException;
	
    SequenceOfDecoder SequenceOf(UType uType) throws AsnException;
    SequenceOfDecoder SequenceOf(UType uType, TagClass tc) throws AsnException;
	
    int Int32() throws AsnException;
    int Int32(TagClass tc) throws AsnException;
	
    int Int32(int minValue, int maxValue) throws AsnException;
    int Int32(int minValue, int maxValue, TagClass tc) throws AsnException;
    
    long Int64() throws AsnException;
    long Int64(TagClass tc) throws AsnException;

    boolean Boolean() throws AsnException;
    boolean Boolean(TagClass tc) throws AsnException;

    float Real32() throws AsnException;
    float Real32(TagClass tc) throws AsnException;

    float Real32(boolean checkForUnderflow) throws AsnException;
    float Real32(boolean checkForUnderflow, TagClass tc) throws AsnException;

    double Real64() throws AsnException;
    double Real64(TagClass tc) throws AsnException;

    double Real64(boolean checkForUnderflow) throws AsnException;
    double Real64(boolean checkForUnderflow, TagClass tc) throws AsnException;

    String UTF8String() throws AsnException;
    String UTF8String(TagClass tc) throws AsnException;

    String UTF8String(int requiredLength) throws AsnException;
    String UTF8String(int requiredLength, TagClass tc) throws AsnException;

    String UTF8String(int minLength, int maxLength) throws AsnException;
    String UTF8String(int minLength, int maxLength, TagClass tc) throws AsnException;

    String BMPString() throws AsnException;
    String BMPString(TagClass tc) throws AsnException;

    String BMPString(int requiredLength) throws AsnException;
    String BMPString(int requiredLength, TagClass tc) throws AsnException;

    String BMPString(int minLength, int maxLength) throws AsnException;
    String BMPString(int minLength, int maxLength, TagClass tc) throws AsnException;

    String IA5String() throws AsnException;
    String IA5String(TagClass tc) throws AsnException;

    String IA5String(int requiredLength) throws AsnException;
    String IA5String(int requiredLength, TagClass tc) throws AsnException;

    String IA5String(int minLength, int maxLength) throws AsnException;
    String IA5String(int minLength, int maxLength, TagClass tc) throws AsnException;

    String PrintableString() throws AsnException;
    String PrintableString(TagClass tc) throws AsnException;

    java.util.GregorianCalendar GndTimeToGC() throws AsnException;
    java.util.GregorianCalendar GndTimeToGC(TagClass tc) throws AsnException;

    byte[] OctetString() throws AsnException;
    byte[] OctetString(TagClass tc) throws AsnException;

    byte[] OctetString(int requiredLength) throws AsnException;
    byte[] OctetString(int requiredLength, TagClass tc) throws AsnException;

    byte[] OctetString(int minLength, int maxLength) throws AsnException;
    byte[] OctetString(int minLength, int maxLength, TagClass tc) throws AsnException;

    java.util.UUID OctetStringToUUID() throws AsnException;
    java.util.UUID OctetStringToUUID(TagClass tc) throws AsnException;
}
```

### <span class="subsection">Interface method descriptions:</span>

Each decode method, starting with <span class="method">Texp</span>, has two overloads: without and with an additional parameter 'tc' of type <span class="datatype">TagClass</span>. The purpose of using methods with the 'tc' parameter is described in "Tag class validation". Therefore, in order not to clutter up the section with repetitive information, descriptions of methods with the 'tc' parameter are omitted.

- ### <span class="method">count</span>  
```java
    int count() throws FormatAsnException;
```
Returns the number of elements in the encoded SEQUENCE.  
*Exceptions*: <span class="exception">FormatAsnException</span>;

- ### <span class="method">hasNext</span>  
```java
    boolean hasNext();
```
Checks if the SEQUENCE decoder has another element to decode.  
*Exceptions*: no exceptions;

- ### <span class="method">skip</span>  
```java
    void skip() throws AsnException;
```
Skips the current element of the encoded SEQUENCE and moves the pointer to the next element.  
*Exceptions*: <span class="exception">FormatAsnException</span>, <span class="exception">EndOfContainerAsnException</span>;

- ### <span class="method">end</span>  
```java
    void end() throws EndNotReachedAsnException;
```
A decoding process can call this method when all elements corresponding to the ASN.1 syntax of a data structure are decoded. Call this method if the presence of any additional elements in the encoded SEQUENCE is considered by the decoding process as an exception that must be handled.  
*Exceptions*: <span class="exception">EndNotReachedAsnException</span>;

- ### <span class="method">exists</span>  
```java
    boolean exists(int tag);
    boolean exists(UType type);
```
These methods are used in Decoding optional and CHOICE elements, which is explained in that section.  
*Exceptions*: no exceptions;

- ### <span class="method">isUniversal</span>  
```java
    boolean isUniversal() throws EndOfContainerAsnException;
```
Checks if the tag class of the current element in the SEQUENCE encoding is ASN.1 Universal.  
*Exceptions*: <span class="exception">EndOfContainerAsnException</span>;

- ### <span class="method">isclass</span>  
```java
    boolean isClass(TagClass tc) throws EndOfContainerAsnException;
```
Checks if the tag class of the current element in the SEQUENCE encoding is equal to the class specified in the "tc" parameter. The parameter value can be one of three classes: ContextSpecific, Application, and Private.  
*Exceptions*: <span class="exception">EndOfContainerAsnException</span>;

- ### <span class="method">isNull</span>  
```java
    boolean isNull() throws EndOfContainerAsnException;
```
Checks if the current element in the SEQUENCE encoding is ASN.1 NULL. Handling NULL elements is described in [Handling ASN.1 NULL]({{ site.baseurl }}{% link docs/decoder/api/SequenceDecoder.md %}#handling-asn1-null).  
*Exceptions*: <span class="exception">FormatAsnException</span>, <span class="exception">EndOfContainerAsnException</span>;

- ### <span class="method">validateClass</span>  
```java
    void validateClass(boolean flag);
```
Sets the tag class validation parameter of the SEQUENCE decoder to true or false. This topic is described in Tag class validation.  
*Exceptions*: no exceptions.

- ### <span class="method">Texp</span>  
```java
    TexpDecoder Texp() throws AsnException;
    TexpDecoder Texp(TagClass tc) throws AsnException;
```
The method creates a decoder of type <span class="datatype">TexpDecoder</span> that provides an API to decode the current element of an encoded SEQUENCE if it is an explicitly tagged element. The <span class="datatype">TexpDecoder</span> interface is described in section 4.4. If the tag class validation is not used, the method without the "tc" parameter is used to create a decoder, whatever the tag class of the explicitly tagged element is. Otherwise, if the tag class validation is used, the first overload of the method validates when creating the decoder that the element's tag class is ContextSpecific. As for other tag classes, the second overload of the method allows an application to specify any of the classes from <span class="datatype">TagClass</span> in the "tc" parameter.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);

- ### <span class="method">Sequence</span>  
```java
    SequenceDecoder Sequence() throws AsnException;
    SequenceDecoder Sequence(TagClass tc) throws AsnException;
```
Creates a SEQUENCE decoder of type <span class="datatype">SequenceDecoder</span> that allows an application to decode a nested SEQUENCE from the current position of an encoded SEQUENCE and returns the decoder to the application.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);

- ### <span class="method">SequenceOf</span>  
```java
    SequenceOfDecoder SequenceOf(UType uType) throws AsnException;
    SequenceOfDecoder SequenceOf(UType uType, TagClass tc) throws AsnException;
```
Creates a SEQUENCE OF decoder of type <span class="datatype">SequenceOfDecoder</span> that allows an application to decode a nested SEQUENCE OF from the current position of an encoded SEQUENCE and returns the decoder to the application.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);

- ### <span class="method">Int32</span>  
```java
    int Int32() throws AsnException;
    int Int32(TagClass tc) throws AsnException;

    int Int32(int minValue, int maxValue) throws AsnException;
    int Int32(int minValue, int maxValue, TagClass tc) throws AsnException;
```
Decodes the current element of an encoded SEQUENCE as being of ASN.1 INTEGER and returns a 32-bit Java integer value to the application. If the decoded value doesn’t fit to 32-bit Java integer, an exception of type <span class="exception">OverflowAsnException</span> is thrown. As for the overloads of Int32 with minValue and maxValue parameters, they allow an application to apply constraints on the value being decoded. If it doesn’t fit to the range [minValue, maxValue] inclusive, <span class="exception">ConstraintAsnException</span> is thrown.  
*Exceptions*:
    - [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);
    - <span class="exception">OverflowAsnException</span>;
    - <span class="exception">ConstraintAsnException</span> can be thrown only by the <span class="method">Int32</span> overloads with minValue and maxValue constraint parameters;

- ### <span class="method">Int64</span>  
```java
    long Int64() throws AsnException;
    long Int64(TagClass tc) throws AsnException;
```
Decodes the current element of an encoded SEQUENCE as being of ASN.1 INTEGER and returns a 64-bit Java integer value to the application. If the decoded value doesn’t fit to 64-bit Java integer, an exception of type OverflowAsnException is thrown.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions), <span class="exception">OverflowAsnException</span>;

- ### <span class="method">Boolean</span>  
```java
    boolean Boolean() throws AsnException;
    boolean Boolean(TagClass tc) throws AsnException;
```
Decodes the current element of an encoded SEQUENCE as being of ASN.1 BOOLEAN and returns a Java boolean value to the application.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);

- ### <span class="method">Real32</span>  
```java
    float Real32() throws AsnException;
    float Real32(TagClass tc) throws AsnException;

    float Real32(boolean checkForUnderflow) throws AsnException;
    float Real32(boolean checkForUnderflow, TagClass tc) throws AsnException;
```
Decodes the current element of an encoded SEQUENCE as being of ASN.1 REAL and returns a 32-bit Java float value to the application. If the decoded value is greater than 32-bit Java float can represent, an exception of type <span class="exception">OverflowAsnException</span> is thrown. Such can happen if the value encoded in this ASN.1 REAL element was originally a 64-bit floating point value. The other edge case is when an ASN.1 REAL element cannot be decoded as a 32-bit Java float without losing precision. If you want to detect loss of precision with throwing <span class="exception">UnderflowAsnException</span>, call the Real32 overload with true for the checkForUnderflow parameter. Losing precision can happen if the value encoded in ASN.1 REAL element was originally a 64-bit floating point value.  
*Exceptions*:
    - [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);
    - <span class="exception">OverflowAsnException</span>;
    - <span class="exception">UnderflowAsnException</span>;

- ### <span class="method">Real64</span>  
```java
    double Real64() throws AsnException;
    double Real64(TagClass tc) throws AsnException;
    
    double Real64(boolean checkForUnderflow) throws AsnException;
    double Real64(boolean checkForUnderflow, TagClass tc) throws AsnException;
```
Decodes the current element of an encoded SEQUENCE as being of ASN.1 REAL and returns a 64-bit Java double value to the application. If the decoded value is greater than 64-bit Java double can represent, an exception of type <span class="exception">OverflowAsnException</span> is thrown. The other edge case is when an ASN.1 REAL element cannot be decoded as a 64-bit Java double without losing precision. If you want to detect loss of precision with throwing <span class="exception">UnderflowAsnException</span>, call the Real64 overload with true for the checkForUnderflow parameter.  
*Exceptions*:
    - [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);
    - <span class="exception">OverflowAsnException</span>;
    - <span class="exception">UnderflowAsnException</span>;

- ### <span class="method">UTF8String</span>  
```java
    String UTF8String() throws AsnException;
    String UTF8String(TagClass tc) throws AsnException;

    String UTF8String(int requiredLength) throws AsnException;
    String UTF8String(int requiredLength, TagClass tc) throws AsnException;

    String UTF8String(int minLength, int maxLength) throws AsnException;
    String UTF8String(int minLength, int maxLength, TagClass tc) throws AsnException;
```
Decodes the current element of an encoded SEQUENCE as being of ASN.1 UTF8String and returns a Java String value to the application. If an application expects a string of a specific length, it can call the method overload with the requiredLength parameter. If the length is limited to a certain range, it can call the method overload with the minLength and maxLength parameters. If the decoded value does not conform to the constraints, an exception of type ConstraintAsnException is thrown.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions), <span class="exception">ConstraintAsnException</span>;

- ### <span class="method">BMPString</span>  
```java
    String BMPString() throws AsnException;
    String BMPString(TagClass tc) throws AsnException;

    String BMPString(int requiredLength) throws AsnException;
    String BMPString(int requiredLength, TagClass tc) throws AsnException;

    String BMPString(int minLength, int maxLength) throws AsnException;
    String BMPString(int minLength, int maxLength, TagClass tc) throws AsnException;
```
Decodes the current element of an encoded SEQUENCE as being of ASN.1 BMPString and returns a Java String value to the application. If an application expects a string of a specific length, it can call the method overload with the requiredLength parameter. If the length is limited to a certain range, it can call the method overload with the minLength and maxLength parameters. If the decoded value does not conform to the constraints, an exception of type ConstraintAsnException is thrown.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions), <span class="exception">ConstraintAsnException</span>;

- ### <span class="method">IA5String</span>  
```java
    String IA5String() throws AsnException;
    String IA5String(TagClass tc) throws AsnException;

    String IA5String(int requiredLength) throws AsnException;
    String IA5String(int requiredLength, TagClass tc) throws AsnException;

    String IA5String(int minLength, int maxLength) throws AsnException;
    String IA5String(int minLength, int maxLength, TagClass tc) throws AsnException;
```
Decodes the current element of the encoded SEQUENCE as being of ASN.1 IA5String and returns a Java String value to the application. If an application expects a string of a specific length, it can call the method overload with the requiredLength parameter. If the length is limited to a certain range, it can call the method overload with the minLength and maxLength parameters. If the decoded value does not conform to the constraints, an exception of type ConstraintAsnException is thrown.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions), <span class="exception">ConstraintAsnException</span>;

- ### <span class="method">PrintableString</span>  
```java
    String PrintableString() throws AsnException;
    String PrintableString(TagClass tc) throws AsnException;
```
Decodes the current element of the encoded SEQUENCE as being of ASN.1 PrintableString and returns a Java String value to the application.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);

- ### <span class="method">GndTimeToGC</span>  
```java
    java.util.GregorianCalendar GndTimeToGC() throws AsnException;
    java.util.GregorianCalendar GndTimeToGC(TagClass tc) throws AsnException;
```
Decodes the current element of the encoded SEQUENCE as being of ASN.1 GeneralizedTime and returns a <span class="datatype">GregorianCalendar</span> instance to the application.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions);

- ### <span class="method">OctetString</span>  
```java
    byte[] OctetString() throws AsnException;
    byte[] OctetString(TagClass tc) throws AsnException;

    byte[] OctetString(int requiredLength) throws AsnException;
    byte[] OctetString(int requiredLength, TagClass tc) throws AsnException;

    byte[] OctetString(int minLength, int maxLength) throws AsnException;
    byte[] OctetString(int minLength, int maxLength, TagClass tc) throws AsnException;
```
Decodes the current element of the encoded SEQUENCE as being of ASN.1 OctetString and returns a Java byte array to the application. If an application expects a byte array of a specific length, it can call the method overload with the requiredLength parameter. If the length is limited to a certain range, it can call the method overload with the minLength and maxLength parameters. If the decoded byte array does not conform to the constraints, an exception of type ConstraintAsnException is thrown.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions), <span class="exception">ConstraintAsnException</span>;

- ### <span class="method">OctetStringToUUID</span>  
```java
    java.util.UUID OctetStringToUUID() throws AsnException;
    java.util.UUID OctetStringToUUID(TagClass tc) throws AsnException;
```
Decodes the current element of the encoded SEQUENCE as being of ASN.1 OctetString and expects it to be 16 bytes long. If so, the method converts it to an instance of java.util.UUID, and returns it to the application. If the length of the decoded byte array is not 16 bytes, an exception of type ConstraintAsnException is thrown.  
*Exceptions*: [Standard Exceptions]({{ site.baseurl }}{% link docs/decoder/decoder.md %}#standard-exceptions), <span class="exception">ConstraintAsnException</span>;

### <span class="subsection">A use case for the SequenceDecoder interface:</span>

This example demonstrates how to decode optional and CHOICE elements. It also demonstrates handling the possible presence of an ASN.1 NULL element in an encoding. Assume we have the following data structure in ASN.1 syntax, representing a simple user profile:
```
    UserProfile := SEQUENCE {
        userName	PritableString,
        nickName	PritableString OPTIONAL,
        contact		UserContact
    }
    UserContact ::= CHOICE {
        email		PritableString,
        phoneNumber	PritableString
    }
```
There are two shortcomings in this definition that makes it impossible for the decoding process to unambiguously decode the second element of an encoded UserProfile instance. The second element can be a nickName, which is of type PrintableString, if the encoding process had provided it. Otherwise, it can be one of the UserContact CHOICE elements: email or phoneNumber, which are also of type PrintableString. Thus, we have three possible elements in the second position.
So, we redefine the UserContact CHOICE to resolve these ambiguities:  
```
    UserProfile := SEQUENCE {
        userName	PrintableString,
        nickName	PrintableString OPTIONAL,
        contact		UserContact
    }
    UserContact ::= CHOICE {
        email		[1] IMPLICIT PrintableString,
        phoneNumber	[2] IMPLICIT PrintableString
    }
```
We applied implicit tagging to the CHOICE elements, and now each element has enough context information to be unambiguously decoded.  

The following Java code demonstrates the process of decoding a UserProfile instance. If nickName is not provided, its value is set to "User". However, the encoding process can provide ASN.1 NULL to make it set to null. In the Java equivalent of UserProfile, whether the contact is an email or a phoneNumber is determined by the contactCode value of 1 or 2, respectively.
```java
    public class UserProfile {
        String	userName;
        String	nickName;	// "User" if not provided
        int	contactCode;	// 1 - email, 2 - phoneNumber
        String	contact;
    }

    public UserProfile decodeUserProfile(byte[] encoding) throws AsnException {
        UserProfile user = new UserProfile();
        SequenceDecoder asnUserProfile = ASNDecoder.Sequence(encoding);
        user.userName = asnUserProfile.PrintableString();
            
        if(asnUserProfile.exists(UType.PrintableString))
            user.nickName = asnUserProfile.PrintableString();
        else if(asnUserProfile.isNull()) // Handling ASN.1 NULL
            user.nickName = null;
        else
            user.nickName = "User";
            
        if(asnUserProfile.exists(1)) {
            user.contactCode = 1;
            user.contact = asnUserProfile.PrintableString(); 
        }
        else if(asnUserProfile.exists(2)) {
            user.contactCode = 2;
            user.contact = asnUserProfile.PrintableString();
        }
        else
            throw new FormatAsnException();
            
        return user;
    }
```
