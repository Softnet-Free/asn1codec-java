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
