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

class Real32Encoder implements ElementEncoder
{
    private Real32Encoder() { }

    private void init(float value)
    {
        floatBits = Float.floatToRawIntBits(value);
        
        System.out.println(String.format("%08X ", floatBits));

        if (floatBits != 0)
        {
            insignificant_bits_number = countInsignificantBits();
            System.out.println(String.format("insignificant_bits_number %d", insignificant_bits_number));
        }
    }

    public static Real32Encoder create(float value)
    {
    	if(Float.isFinite(value) == false)
			 throw new IllegalArgumentException("The argument 'value' must not be NaN or Infinity");

    	Real32Encoder encoder = new Real32Encoder();
        encoder.init(value);
        return encoder;
    }

    private int floatBits = 0;
    private int insignificant_bits_number;

    public boolean isConstructed() {
    	return false;
    }

    public int estimateSize()
    {
        if (floatBits != 0)
        {
            return 8 - insignificant_bits_number / 8;
        }
        else
        {
            return 2;
        }
    }

    public int encodeTLV(BinaryStack binStack)
    {
        int LV_length = encodeLV(binStack);
        binStack.stack(UniversalTag.Real);
        return 1 + LV_length;
    }

    public int encodeLV(BinaryStack binStack)
    {
        if (floatBits == 0)
        {
            binStack.stack((byte)0);
            return 1;
        }

        int byte_a = (floatBits >> 24) & 0x0000007F;
        int byte_b = (floatBits >> 16) & 0x00000080;
        int exponent = (byte_a << 1) + (byte_b >> 7);

        int mantissa;
        if (exponent >= 1) // normalized
        {
            exponent -= 127;
            mantissa = (floatBits & 0x007FFFFF) | 0x00800000;
        }
        else
        {
            exponent = -126;
            mantissa = floatBits & 0x007FFFFF;
        }

        exponent -= (23 - insignificant_bits_number);
        mantissa = mantissa >> insignificant_bits_number;

        int mantissa_bytes_number = encodeMantissa(mantissa, binStack);
        int exponent_bytes_number = encodeExponent(exponent, binStack);
        encodeInfoByte(exponent_bytes_number, binStack);            

        int V_length = 1 + exponent_bytes_number + mantissa_bytes_number;
        binStack.stack((byte)V_length);

        return 1 + V_length;
    }

    private int encodeMantissa(int mantissa, BinaryStack binStack)
    {
        int b0 = mantissa & 0x000000ff;
        int b1 = (mantissa & 0x0000ff00) >> 8;
        int b2 = (mantissa & 0x00ff0000) >> 16;

        if (b2 == 0)
        {
            if (b1 == 0)
            {
                binStack.stack(b0);
                return 1;
            }
            else
            {
                binStack.stack(b0);
                binStack.stack(b1);
                return 2;
            }
        }
        else
        {
            binStack.stack(b0);
            binStack.stack(b1);
            binStack.stack(b2);
            return 3;
        }
    }

    private int encodeExponent(int exponent, BinaryStack binStack)
    {
        int b0 = exponent & 0x000000ff;
        int b1 = (exponent & 0x0000ff00) >> 8;

        if (b1 == 255)
        {
            if (b0 >= 128)
            {
                binStack.stack(b0);
                return 1;
            }
            else
            {
                binStack.stack(b0);
                binStack.stack(255);
                return 2;
            }
        }
        else if (b1 == 0)
        {
            if (b0 <= 127)
            {
                binStack.stack(b0);
                return 1;
            }
            else
            {
                binStack.stack(b0);
                binStack.stack(0);
                return 2;
            }
        }
        else
        {
            binStack.stack(b0);
            binStack.stack(b1);
            return 2;
        }
    }

    private void encodeInfoByte(int exponent_bytes_number, BinaryStack binStack)
    {
        boolean isPositive = floatBits >= 0 ? true : false;
        if (isPositive)
        {
            if (exponent_bytes_number == 1)
            {
                binStack.stack(0x80);
            }
            else // exponent_bytes_number == 2
            {
                binStack.stack(0x81);
            }
        }
        else
        {
            if (exponent_bytes_number == 1)
            {
                binStack.stack(0xC0);
            }
            else // exponent_bytes_number == 2
            {
                binStack.stack(0xC1);
            }
        }
    }

    private int countInsignificantBits()
    {
        for (int i = 0; i <= 2; i++)
        {
            int b = ((floatBits >> (i * 8)) & 0xFF);
            if (b > 0)
            {
                if ((b & 0x01) != 0) return i * 8;
                if ((b & 0x02) != 0) return i * 8 + 1;
                if ((b & 0x04) != 0) return i * 8 + 2;
                if ((b & 0x08) != 0) return i * 8 + 3;
                if ((b & 0x10) != 0) return i * 8 + 4;
                if ((b & 0x20) != 0) return i * 8 + 5;
                if ((b & 0x40) != 0) return i * 8 + 6;
                return i * 8 + 7;
            }
        }
        return 23;
    }
}
















