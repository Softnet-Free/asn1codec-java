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

class LengthDecoder
{
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
