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

class LengthEncoder
{
	public static int estimateSize(int length)
    {
        if (length <= 0x0000007f) {
            return 1;
        }
        else if (length <= 0x000000ff) {
            return 2;
        }
        else if (length <= 0x0000ffff) {
            return 3;
        }
        else if (length <= 0x00ffffff) {
            return 4;
        }
        else {
            return 5;
        }
    }
	
	public static int encode(int length, BinaryStack binStack)
    {
        if (length <= 0x0000007f)
        {
            binStack.stack((byte)length);
            return 1;
        }
        else if (length <= 0x000000ff)
        {
            binStack.stack((byte)length);
            binStack.stack((byte)(129));

            return 2;
        }
        else if (length <= 0x0000ffff)
        {
            byte b0 = (byte)(length & 0x000000ff);
            byte b1 = (byte)((length >> 8) & 0x000000ff);

            binStack.stack(b0);
            binStack.stack(b1);
            binStack.stack((byte)(130));

            return 3;
        }
        else if (length <= 0x00ffffff)
        {
            byte b0 = (byte)(length & 0x000000ff);
            byte b1 = (byte)((length >> 8) & 0x000000ff);
            byte b2 = (byte)((length >> 16) & 0x000000ff);

            binStack.stack(b0);
            binStack.stack(b1);
            binStack.stack(b2);
            binStack.stack((byte)(131));

            return 4;
        }
        else
        {
            byte b0 = (byte)(length & 0x000000ff);
            byte b1 = (byte)((length >> 8) & 0x000000ff);
            byte b2 = (byte)((length >> 16) & 0x000000ff);
            byte b3 = (byte)((length >> 24) & 0x000000ff);

            binStack.stack(b0);
            binStack.stack(b1);
            binStack.stack(b2);
            binStack.stack(b3);
            binStack.stack((byte)(132));

            return 5;
        }
    }
}
