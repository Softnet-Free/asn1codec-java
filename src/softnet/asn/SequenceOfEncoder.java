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

public interface SequenceOfEncoder{
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
