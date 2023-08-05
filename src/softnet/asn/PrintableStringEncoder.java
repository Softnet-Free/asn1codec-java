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

import java.util.regex.Pattern;

class PrintableStringEncoder implements ElementEncoder
{
	private byte[] V_bytes;

    private PrintableStringEncoder(byte[] valueBytes)
    {
    	V_bytes = valueBytes;
    }

    public static PrintableStringEncoder create(String value)
    {
    	if (Pattern.matches("^[\\u0020-\\u007F]+$", value) == false)
    		throw new IllegalArgumentException(String.format("The string '%s' contains characters that are not allowed in 'ASN1 PrintableString'.", value));
    		
		if (Pattern.matches("^[A-Za-z0-9\\s'()+,\\-./:=?]*$", value) == false)    	
    		throw new IllegalArgumentException(String.format("The string '%s' contains characters that are not allowed in 'ASN1 PrintableString'.", value));

		byte[] valueBytes = value.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        return new PrintableStringEncoder(valueBytes);
    }

    public boolean isConstructed() {
    	return false;
    }

    public int estimateSize() {
        return 1 + LengthEncoder.estimateSize(V_bytes.length) + V_bytes.length;
    }

    public int encodeTLV(BinaryStack binStack)
    {
        binStack.stack(V_bytes);
        int L_length = LengthEncoder.encode(V_bytes.length, binStack);
        binStack.stack(UniversalTag.PrintableString);
        return 1 + L_length + V_bytes.length;
    }

    public int encodeLV(BinaryStack binStack)
    {
        binStack.stack(V_bytes);
        int L_length = LengthEncoder.encode(V_bytes.length, binStack);
        return L_length + V_bytes.length;
    }
}
