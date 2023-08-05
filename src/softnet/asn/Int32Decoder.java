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

class Int32Decoder 
{
    public static int decode(byte[] buffer, int offset, int length) throws FormatAsnException, OverflowAsnException
    {
        if (length < 1)
            throw new FormatAsnException();

        int A = buffer[offset];
        if (length == 1)
        {
            return A;
        }

        int B = buffer[offset + 1] & 0xFF;
        if (length == 2)
        {
            return (A << 8) | B;
        }

        int C = buffer[offset + 2] & 0xFF;
        if (length == 3)
        {
            return (A << 16) | (B << 8) | C;
        }

        if (length == 4)
        {
            int D = buffer[offset + 3] & 0xFF;
            return (A << 24) | (B << 16) | (C << 8) | D;
        }

        // length >= 5
        throw new OverflowAsnException("The input number cannot be represented as a 32-bit signed integer.");
    }
}
