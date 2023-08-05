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

public class ASNDecoder 
{
    private ASNDecoder() { }

    public static SequenceDecoder Sequence(byte[] encoding) throws AsnException
    {
        return Sequence(encoding, 0);
    }    

    public static SequenceDecoder Sequence(byte[] encoding, int offset) throws AsnException
    {
        try
        {
            int T = encoding[offset];

            if ((T & C_Mask_Class) != C_Universal_Class)
                throw new TypeMismatchAsnException();

            if ((T & C_Constructed_Flag) == 0)
                throw new TypeMismatchAsnException();

            if ((T & C_Mask_Tag) != UniversalTag.Sequence)
                throw new TypeMismatchAsnException();

            offset++;
            PairInt32 lengthResult = new PairInt32();
            LengthDecoder.decode(encoding, offset, lengthResult);
            offset += lengthResult.second;

            if (offset + lengthResult.first > encoding.length)
                throw new FormatAsnException("The size of the input buffer is not enough to contain all the ASN.1 data.");

            return new SequenceDecoderImp(encoding, offset, lengthResult.first, false); 
        }
        catch (java.lang.ArrayIndexOutOfBoundsException e)
        {
            throw new FormatAsnException("The size of the input buffer is not enough to contain all the ASN.1 data.");
        }
    }

	private static int C_Mask_Class = 0xC0;        
	private static int C_Mask_Tag = 0x1F;
	private static int C_Universal_Class = 0;
	private static int C_Constructed_Flag = 0x20;
}
