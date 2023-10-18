/*
*	Copyright 2023 Robert Koifman
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
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

    java.util.Date GndTimeToDate() throws AsnException;
    java.util.Date GndTimeToDate(TagClass tc) throws AsnException;

	java.util.GregorianCalendar GndTimeToGC() throws AsnException;
	java.util.GregorianCalendar GndTimeToGC(TagClass tc) throws AsnException;

	byte[] OctetString() throws AsnException;
	byte[] OctetString(TagClass tc) throws AsnException;
}
