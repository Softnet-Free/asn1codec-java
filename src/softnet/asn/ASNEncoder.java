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

    public byte[] getEncoding(int headerSize)
    {
        if (headerSize < 0 || headerSize > 1024)
            throw new IllegalArgumentException("'headerSize' must be in the range between 0 and 1024 inclusive.");
        int estimatedSize = sequence.estimateSize();
        BinaryStack binStack = new BinaryStack();
        binStack.allocate(headerSize + estimatedSize);
        sequence.encodeTLV(binStack);
       	return binStack.buffer();
    }	
}























