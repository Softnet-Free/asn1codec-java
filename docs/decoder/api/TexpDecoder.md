---
layout: default
title: TexpDecoder
parent: Decoder Specification
---

## Interface TexpDecoder

A decoder of this type allows an application to decode an explicitly tagged element. Note that explicit tagging adds a new context to the element, while preserving its original context. This is a way to avoid ambiguity when decoding an element that could not be avoided by implicit tagging. As an explicit tagging is constructed of a single element, an application can only call one of the decode methods of the interface, and only once. The decode methods are those that start with Texp until the end in the interface declaration below.  

### <span class="subsection">Interface declaration:</span>
```java
public interface TexpDecoder {
	boolean isThis(int tag);
	boolean isThis(TagClass tc);
	
	void validateClass(boolean flag);
	
	boolean exists(UType type);
	boolean exists(int tag);
	
	boolean isUniversal();
	boolean isClass(TagClass tc);
	
	TexpDecoder Texp() throws AsnException;
	TexpDecoder Texp(TagClass tc) throws AsnException;

	SequenceDecoder Sequence() throws AsnException;
	SequenceDecoder Sequence(TagClass tc) throws AsnException;
	
	SequenceOfDecoder SequenceOf(UType uType) throws AsnException;
	SequenceOfDecoder SequenceOf(UType uType, TagClass tc) throws AsnException;
	
	int Int32() throws AsnException;
	int Int32(TagClass tc) throws AsnException;
	
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

	String BMPString() throws AsnException;
	String BMPString(TagClass tc) throws AsnException;

	String IA5String() throws AsnException;
	String IA5String(TagClass tc) throws AsnException;

	String PrintableString() throws AsnException;
	String PrintableString(TagClass tc) throws AsnException;

	java.util.GregorianCalendar GndTimeToGC() throws AsnException;
	java.util.GregorianCalendar GndTimeToGC(TagClass tc) throws AsnException;

	byte[] OctetString() throws AsnException;
	byte[] OctetString(TagClass tc) throws AsnException;
}
```
### <span class="subsection">Interface method descriptions:</span>

The interface declares methods for decoding a single tagged ASN.1 element into a Java data type instance. These methods are subset of the <span class="datatype">SequenceDecoder</span> interface methods and their descriptions are the same. Therefore, these descriptions are omitted to avoid cluttering the manual with repetitive information. Along with those methods, the interface declares two overloads of the <span class="method">isThis</span> method described below:

- ### <span class="method">isThis</span>  
```java
	boolean isThis(int tag);
	boolean isThis(TagClass tc);
```
The first method with a "tag" parameter allows an application to check the explicit tag of the element, regardless of the tag class. The second method with a "tc" parameter is used to check the class of this tag. These methods are named with "This" prefix to differentiate them from the methods that operate on the element enclosed in the explicit tagging. And these are the rest of the interface methods. Note that the element itself can also be tagged implicitly or even explicitly. Nested explicit tags are technically possible but not recommended.

### <span class="subsection">A use case for the TexpDecoder interface:</span>

In the end of the "Interface TexpEncoder" section, there is an [example]({{ site.baseurl }}{% link docs/encoder/api/TexpEncoder.md %}#a-use-case-for-the-texpencoder-interface) that demonstrates the process of encoding a simple Pupil structure. The structure is repeated below. That example uses an explicit tagging to avoid ambiguity when decoding elements of a Pupil instance. Here, we continue that example, and provide the Java code that decodes a Pupil instance.

ASN.1 syntax of the structure:
```
Pupil ::= SEQUENCE {
    name            PrintableString,
    age             INTEGER,
    parentContact   CHOICE {
	    motherContact   [1] EXPLICIT ParentContact,
	    fatherContact   [2] EXPLICIT ParentContact
    }
}
ParentContact ::= CHOICE {
	email       PrintableString,
	phoneNumber IA5String
}
```
The Java code defines a <span class="datatype">Pupil</span> structure, which has the same fields as the ASN.1 structure: name, age, and parentContact, plus two more fields: parentCode and contactCode. The parentCode field indicates whether the contact belongs to the mother (code 1) or father (code 2). The contactCode field indicates whether the contact is an email (code 1) or a phone number (code 2):

```java
public class Pupil
{
	public String   name;
	public int      age;
	public int      parentCode;
	public int      contactCode;		
	public String   contact;
}

public Pupil decodePupil(byte[] buffer) throws AsnException
{
	Pupil pupil = new Pupil();
	SequenceDecoder asnPupil = ASNDecoder.Sequence(buffer);
	pupil.name = asnPupil.PrintableString();
	pupil.age = asnPupil.Int32();

	TexpDecoder asnTexpContact = asnPupil.Texp();

	if(asnTexpContact.isThis(1)) 
		pupil.parentCode = 1;
	else if(asnTexpContact.isThis(2))
		pupil.parentCode = 2;
	else
		throw new FormatAsnException();				
		
	if(asnTexpContact.exists(UType.PrintableString)) {
		pupil.contactCode = 1;
		pupil.contact = asnTexpContact.PrintableString();
	}
	else if(asnTexpContact.exists(UType.IA5String)) {
		pupil.contactCode = 2;
		pupil.contact = asnTexpContact.IA5String();
	}
	else
		throw new FormatAsnException();
		
	return pupil;
}
```