/*
*	Copyright (c) 2023 Robert Koifman 
*	
*	This file is part of Softnet ASN.1 Codec (Java).
*	
*	Softnet ASN.1 Codec (Java) is free software: you can redistribute it and/or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation, either version 3 of the License, or
*	(at your option) any later version.
*	
*	Softnet ASN.1 Codec (Java) is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*	GNU General Public License for more details.
*	
*	You should have received a copy of the GNU General Public License
*	along with Softnet ASN.1 Codec (Java). If not, see <https://www.gnu.org/licenses/>.
*
*	-----------------------------------------------------------------------------------
*	The developer's guide to Softnet ASN.1 Codec (Java) is published at 
*	https://robert-koifman.github.io/asncodec-java/.
*
*	You can find use cases, Q&A, articles, and discussions about this project at 
*	https://github.com/robert-koifman/asncodec-java/discussions.
*/

package softnet.asn;

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
