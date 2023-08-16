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

class Real32Decoder
{
	public static float decode(byte[] buffer, int offset, int V_length, boolean checkForUnderflow) throws FormatAsnException, UnderflowAsnException, OverflowAsnException
	{
        if (V_length == 0)
            return 0.0f;

        int headerByte = buffer[offset];
        offset++;

        if ((headerByte & 0xC0) == 0x40)
        {
        	if(V_length != 1)
        		throw new FormatAsnException();
        	
        	if(headerByte == 0x40)
        		return Float.POSITIVE_INFINITY;

        	if(headerByte == 0x41)
        		return Float.NEGATIVE_INFINITY;
        	
        	if(headerByte == 0x42)
        		return Float.NaN;

        	if(headerByte == 0x43)
        		return -0.0f;

    		throw new FormatAsnException();
        }
        
        if ((headerByte & 0x80) == 0)
            throw new FormatAsnException("The ASN1 codec only supports base 2 binary format for encoding floating point numbers.");

        if ((headerByte & 0x30) != 0)
            throw new FormatAsnException("The ASN1 codec only supports base 2 binary format for encoding floating point numbers.");

        if ((headerByte & 0x0C) != 0)  // scaling factor must be zero for base 2 binary format
            throw new FormatAsnException();

        int exponent;
        int exponent_flags = headerByte & 0x03;
        int exponent_bytes_number;

        if (exponent_flags == 0)
        {
            if (V_length < 3)
                throw new FormatAsnException();

            exponent = buffer[offset];
            offset++;
            exponent_bytes_number = 1;
        }
        else if (exponent_flags == 1)
        {
            if (V_length < 4)
                throw new FormatAsnException();

            int byte_a = buffer[offset] & 0xFF;
            int byte_b = buffer[offset + 1] & 0xFF;
            offset += 2;
            exponent_bytes_number = 2;

            if (byte_a >= 128)
                exponent = /* 0xFFFF0000 */ -65536 | (byte_a << 8) | byte_b;
            else
                exponent = (byte_a << 8) | byte_b;
        }
        else if (exponent_flags == 2)
        {
            if (V_length < 5)
                throw new FormatAsnException();

            int byte_a = buffer[offset] & 0xFF;
            if (byte_a >= 128)
            {
                if (checkForUnderflow)
                    throw new UnderflowAsnException("The precision of the input real is outside of the scope of 64-bit IEEE-754 real.");
                return 0.0f;
            }
            else
                throw new OverflowAsnException("The value of the input real is outside of the scope of 64-bit IEEE-754 real.");
        }
        else // exponent_flags == 3
        {
            if (V_length < 7)
                throw new FormatAsnException();

            int byte_a = buffer[offset + 1] & 0xFF;
            if (byte_a >= 128)
            {
                if (checkForUnderflow)
                    throw new UnderflowAsnException("The precision of the input real is outside of the scope of 64-bit IEEE-754 real.");
                return 0.0f;
            }
            else
                throw new OverflowAsnException("The value of the input real is outside of the scope of 64-bit IEEE-754 real.");
        }

        int mantissa_bytes_number = V_length - (1 + exponent_bytes_number);

        int lsb_index = offset + mantissa_bytes_number - 1;
        if ((buffer[lsb_index] & 0x01) == 0)
            throw new FormatAsnException();

        int ms_byte = buffer[offset] & 0xFF;
        if (ms_byte == 0)
            throw new FormatAsnException();

        int ms_bits_number = 0;
        if (ms_byte >= 128) ms_bits_number = 8;
        else if (ms_byte >= 64) ms_bits_number = 7;
        else if (ms_byte >= 32) ms_bits_number = 6;
        else if (ms_byte >= 16) ms_bits_number = 5;
        else if (ms_byte >= 8) ms_bits_number = 4;
        else if (ms_byte >= 4) ms_bits_number = 3;
        else if (ms_byte >= 2) ms_bits_number = 2;
        else ms_bits_number = 1;

        int mantissa_width = ms_bits_number + (mantissa_bytes_number - 1) * 8;        
        int mantissa = 0;
        
        if (mantissa_width <= 24)
        {
	        for (int i = lsb_index, j = 0; i >= offset; i--, j++)
	            mantissa = mantissa | ((buffer[i] & 0xFF) << (8 * j));
        }
        else // mantissa_width > 24 && mantissa_bytes_number >= 4  ==>  pick out the most significant 24 bits
        {
            if (checkForUnderflow)
                throw new UnderflowAsnException("The precision of the input real is outside of the scope of 32-bit IEEE-754 real.");

	        for (int i = offset + 3, j = 0; i >= offset; i--, j++)
	            mantissa = mantissa | ((buffer[i] & 0xFF) << (8 * j));
	        mantissa = mantissa >> ms_bits_number;
        }
        
        exponent = exponent + mantissa_width + 126; // exponent + (mantissa_width - 1) + 127

        if (exponent >= 1) 
        {
            if (exponent > 254)
                throw new OverflowAsnException("The value of an input real is outside of the scope of 32-bit IEEE-754 real.");

            int floatBits = exponent << 23;

            if ((headerByte & 0x40) != 0) // check for sign  
            	floatBits = floatBits | -2147483648; /* 0x80000000 */

            if(mantissa_width < 24)
            	mantissa = (mantissa << (24 - mantissa_width)) & 0x007FFFFF;
            else
            	mantissa = mantissa & 0x007FFFFF;;           	

            floatBits = floatBits | mantissa;
            
            return Float.intBitsToFloat(floatBits); 
        }
        else // exponent <= 0 
        {
        	int floatBits = 0;

        	if ((headerByte & 0x40) != 0) // check for sign  
            	floatBits = floatBits | -2147483648; /* 0x80000000 */
            
            if(mantissa_width < 24)
            {
            	int shift = 23 - mantissa_width + exponent;
            	if(shift > 0)
            		mantissa = mantissa << shift;
            	else if (shift < 0)
            	{
                    if (checkForUnderflow)
                        throw new UnderflowAsnException("The precision of the input real is outside of the scope of 32-bit IEEE-754 real.");
            		mantissa = mantissa >> shift;
            	}
            }
            else // mantissa_width == 24
            {
                if (checkForUnderflow)
                    throw new UnderflowAsnException("The precision of the input real is outside of the scope of 32-bit IEEE-754 real.");
            	mantissa = mantissa >> (-exponent + 1);  
            }
            
            floatBits = floatBits | mantissa;
            
            return Float.intBitsToFloat(floatBits);
        }        
	}		
}
















