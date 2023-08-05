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

class LengthDecoder
{
    public static PairInt32 decode2(byte[] buffer, int offset) throws FormatAsnException
    {
    	return null;
    }
    
    public static void decode(byte[] buffer, int offset, PairInt32 result) throws FormatAsnException
    {
    	try
    	{    	
	        int L1_Byte = buffer[offset] & 0xFF;
	
	        if (L1_Byte <= 127)
	        {
	        	result.first = L1_Byte;
	        	result.second = 1;
	        	return;
	        }
	
	        if (L1_Byte == 128)
	            throw new FormatAsnException("ASN1 codec does not support the indefinite length form.");
	
	        int L_Size = L1_Byte & 0x7F;
	        offset++;
	
	        if (L_Size == 1)
	        {
	            int length = buffer[offset] & 0xFF;
	            result.first = length;
	        	result.second = 2;
	        	return;
	        }
	        else if (L_Size == 2)
	        {
	            int b1 = buffer[offset] & 0xFF;
	            int b0 = buffer[offset + 1] & 0xFF;
	            int length = (b1 << 8) | b0;
	            result.first = length;
	        	result.second = 3;
	        	return;
	        }
	        else if (L_Size == 3)
	        {
	            int b2 = buffer[offset] & 0xFF;
	            int b1 = buffer[offset + 1] & 0xFF;
	            int b0 = buffer[offset + 2] & 0xFF;
	            int length = (b2 << 16) | (b1 << 8) | b0;
	            result.first = length;
	        	result.second = 4;
	        	return;
	        }
	        else if (L_Size == 4)
	        {
	            int b3 = buffer[offset] & 0xFF;
	            if (b3 >= 128)
	                throw new FormatAsnException("ASN1 codec does not support the length of content more than 2GB.");
	
	            int b2 = buffer[offset + 1] & 0xFF;
	            int b1 = buffer[offset + 2] & 0xFF;
	            int b0 = buffer[offset + 3] & 0xFF;
	            int length = (b3 << 24) | (b2 << 16) | (b1 << 8) | b0;
	            result.first = length;
	        	result.second = 5;
	        	return;
	        }
	
	        throw new FormatAsnException("ASN1 codec does not support the length of content more than 2GB.");
    	}
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new FormatAsnException("The size of the input buffer is not enough to contain all the Asn1 data.");
        }
    }
}
