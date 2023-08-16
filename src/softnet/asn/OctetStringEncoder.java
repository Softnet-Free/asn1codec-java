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

class OctetStringEncoder implements ElementEncoder
{
	private byte[] buffer;
	private int offset;
	private int length;
	
	public OctetStringEncoder(byte[] buffer, int offset, int length)
	{
		this.buffer = buffer;
		this.offset = offset;
		this.length = length;
	}
	
    public boolean isConstructed() {
    	return false;
    }

	public int estimateSize() {
		return 1 + LengthEncoder.estimateSize(length) + length;
	}
	
	public int encodeTLV(BinaryStack binStack)
	{
		binStack.stack(buffer, offset, length);
        int L_length = LengthEncoder.encode(length, binStack);
	    binStack.stack(UniversalTag.OctetString);
	    return 1 + L_length + length;
	}	

	public int encodeLV(BinaryStack binStack)
	{
		binStack.stack(buffer, offset, length);
        int L_length = LengthEncoder.encode(length, binStack);
	    return L_length + length;
	}	
}
