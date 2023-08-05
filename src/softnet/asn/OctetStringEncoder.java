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
*/

package softnet.asn;

class OctetStringEncoder implements ElementEncoder
{
	private byte[] buffer;
	private int offset;
	private int length;
	
	public OctetStringEncoder(byte[] buffer, int offset, int length)
	{
		this.buffer = buffer;
		this.offset = offset;
		this.length = length;
	}
	
    public boolean isConstructed() {
    	return false;
    }

	public int estimateSize() {
		return 1 + LengthEncoder.estimateSize(length) + length;
	}
	
	public int encodeTLV(BinaryStack binStack)
	{
		binStack.stack(buffer, offset, length);
        int L_length = LengthEncoder.encode(length, binStack);
	    binStack.stack(UniversalTag.OctetString);
	    return 1 + L_length + length;
	}	

	public int encodeLV(BinaryStack binStack)
	{
		binStack.stack(buffer, offset, length);
        int L_length = LengthEncoder.encode(length, binStack);
	    return L_length + length;
	}	
}
