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

class BooleanEncoder implements ElementEncoder
{
	private boolean value;
	
	public BooleanEncoder(boolean value)
	{
		this.value = value;
	}
	
    public boolean isConstructed() {
    	return false;
    }
	
	public int estimateSize()
	{
		return 3;		
	}
	
	public int encodeTLV(BinaryStack binStack)
	{		
        if (value)
        {
            binStack.stack((byte)0xFF);
            binStack.stack((byte)1);
            binStack.stack(UniversalTag.Boolean);
        }
        else
        {
        	binStack.stack((byte)0);
            binStack.stack((byte)1);
            binStack.stack(UniversalTag.Boolean);            
        }
        return 3;
	}
	
	public int encodeLV(BinaryStack binStack)
	{		
        if (value)
        {
            binStack.stack((byte)0xFF);
            binStack.stack((byte)1);
        }
        else
        {
        	binStack.stack((byte)0);
            binStack.stack((byte)1);
        }
        return 2;
	}
}
