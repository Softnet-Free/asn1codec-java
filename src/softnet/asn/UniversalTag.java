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

class UniversalTag
{
	public final static byte Boolean = 1;
	public final static byte Integer = 2;
	public final static byte OctetString = 4;
	public final static byte Null = 5;
	public final static byte Real = 9;
	public final static byte UTF8String = 12;           
	public final static byte Sequence = 16;
	public final static byte PrintableString = 19;	// A-Z a-z 0-9 space ' ( ) + , - . / : = ?
	public final static byte IA5String = 22;		// ASCII 0-127
	public final static byte GeneralizedTime = 24;
	public final static byte BMPString = 30;		// Big-Endian UTF-16
}
