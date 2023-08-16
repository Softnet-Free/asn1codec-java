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
