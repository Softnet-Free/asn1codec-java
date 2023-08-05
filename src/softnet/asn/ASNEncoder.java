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

public class ASNEncoder 
{
    private SequenceEncoderImp sequence;
    
    public ASNEncoder() {
	    sequence = new SequenceEncoderImp();
    }

    public int getSize() {
        return sequence.estimateSize();
    }
	
    public SequenceEncoder Sequence() {
       return sequence;
    }

    public byte[] getEncoding()
    {
    	int estimatedSize = sequence.estimateSize();
        BinaryStack binStack = new BinaryStack();
        binStack.allocate(estimatedSize);
        sequence.encodeTLV(binStack);
      	return binStack.buffer();
    }

    public byte[] getEncoding(int prefixSize)
    {
        if (prefixSize < 0)
            throw new IllegalArgumentException("'prefixSize' must not be negative.");
        int estimatedSize = sequence.estimateSize();
        BinaryStack binStack = new BinaryStack();
        binStack.allocate(prefixSize + estimatedSize);
        sequence.encodeTLV(binStack);
       	return binStack.buffer();
    }	
}























