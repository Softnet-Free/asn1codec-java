---
layout: default
title: TexpEncoder
parent: Encoder Specification
---

## Interface TexpEncoder

An encoder of this type creates the encoding of an explicitly tagged element. Explicit tagging is used when ambiguity in the decoding of structure elements cannot be avoided by using implicit tagging. Explicit tag defines the element context explicitly, while preserving the original context of the element.  

It is important to note that an encoder of type <span class="datatype">TexpEncoder</span> must contain one and only one element to encode. That is, an application must call one and only one of the methods shown in the declaration below that adds an instance of a Java data type to the encoder.

### <span class="subsection">Interface declaration:</span>  
```java
public interface TexpEncoder {
	void setThis(int tag);
	void setThis(int tag, TagClass tc);

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
}
```

### <span class="subsection">Interface method descriptions:</span>  

The interface declares methods for placing a single instance of a Java data type to the encoder. These methods are subset of the <span class="datatype">SequenceEncoder</span> interface methods and their descriptions are the same. Therefore, these descriptions are omitted to avoid cluttering the manual with repetitive information. Along with those methods, the interface declares two overloads of the <span class="method">setTag</span> method described below:

- ### <span class="method">setThis</span> 
The signatures of the method overloads:
```java
    void setThis(int tag)
    void setThis(int tag, TagClass tc)
```
This method sets an explicit tag that provides a decoding process with an explicit context to unambiguously decode the element. Note that the element itself can also be tagged implicitly or even explicitly (though nested explicit tagging is a rather ugly style). The first overload of the method sets the tag value as specified in the tag parameter and the tag class as <span class="datatype">TagClass.ContextSpecific</span>. The second overload of the method allows an application to specify a different tag class in the tc parameter.

### <span class="subsection">A use case for the TexpEncoder interface:</span>  

This example demonstrates a use case where an explicit tagging is required to avoid ambiguity when extracting ASN.1 elements from a binary representation. The example defines a simplified ASN.1 data structure representing a pupil, which consists of three elements: the name and age of the pupil, and the contact of one of his/her parents. The parentContact element is defined as a CHOICE between the motherContact and fatherContact elements, which are both of type ParentContact. The ParentContact type itself is defined as a CHOICE between the email and phoneNumber elements, which are of PrintableString and IA5String types, respectively. The question is what type of tagging, implicit or explicit, helps to avoid ambiguity between the motherContact and fatherContact elements of the CHOICE when decoding the parentContact element. If we had used implicit tagging, this would redefine the tag field of the <tag, length, value> triple in the encoding of the parentContact element, which is an element chosen between the ParentContact CHOICE elements: email and phoneNumber. The implicit tagging would allow the decoder to identify whether the parent is mother or father, but would cause ambiguity between email and phoneNumber as it redefines the tag field of the chosen contact causing to lose the type context. To preserve the type context of the element an explicit tagging has been used.  

ASN.1 syntax of the example:
```
    Pupil ::= SEQUENCE {
        name            PrintableString,
        age             INTEGER,
        parentContact   CHOICE {
	        motherContact	[1] EXPLICIT ParentContact,
	        fatherContact	[2] EXPLICIT ParentContact
        }
    }
    ParentContact ::= CHOICE {
        email		PrintableString,
        phoneNumber	IA5String
    }
```
The Java method that encodes an instance of the ASN.1 structure, Pupil, is shown below. The example also defines a Java structure, <span class="datatype">Pupil</span>, which has the same fields as the ASN.1 structure: name, age, and parentContact, plus two more fields: parentCode and contactCode. The parentCode field indicates whether the contact belongs to the mother (code 1) or father (code 2). The contactCode field indicates whether the contact is an email (code 1) or a phone number (code 2). It is worth noting that the information conveyed by these three fields is encapsulated in only one element of the ASN.1 encoding, parentContact.

```java
    public class Pupil
    {
        public String   name;
        public int      age;
        public int      parentCode;
        public int      contactCode;		
        public String   parentContact;
    }

    public static byte[] encodePupil(Pupil pupil)
    {
        ASNEncoder asnEncoder = new ASNEncoder();
        SequenceEncoder asnPupil = asnEncoder.Sequence();
        asnPupil.PrintableString(pupil.name);
        asnPupil.Int32(pupil.age);
            
        TexpEncoder asnTexpContact = asnPupil.Texp();
        if(pupil.parentCode == 1)
            asnTexpContact.setThis(1);
        else
            asnTexpContact.setThis(2);
            
        if(pupil.contactCode == 1)
            asnTexpContact.PrintableString(pupil.parentContact);
        else
            asnTexpContact.IA5String(pupil.parentContact);
            
        return asnEncoder.getEncoding();
    }
```