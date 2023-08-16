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

import java.util.regex.Pattern;

class IA5StringEncoder implements ElementEncoder
{
	private byte[] V_bytes;

    private IA5StringEncoder(byte[] valueBytes)
    {
    	V_bytes = valueBytes;
    }

    public static IA5StringEncoder create(String value)
    {
    	if (Pattern.matches("^[\\u0000-\\u007F]*$", value) == false)
            throw new IllegalArgumentException(String.format("The string '%s' contains characters that are not allowed in 'ASN.1 IA5String'.", value));

    	byte[] valueBytes = value.getBytes(java.nio.charset.StandardCharsets.US_ASCII);    	
        return new IA5StringEncoder(valueBytes);
    }

    public boolean isConstructed() {
    	return false;
    }

    public int estimateSize() {
        return 1 + LengthEncoder.estimateSize(V_bytes.length) + V_bytes.length;
    }

    public int encodeTLV(BinaryStack binStack)
    {
        binStack.stack(V_bytes);
        int L_length = LengthEncoder.encode(V_bytes.length, binStack);
        binStack.stack(UniversalTag.IA5String);
        return 1 + L_length + V_bytes.length;
    }
    
    public int encodeLV(BinaryStack binStack)
    {
        binStack.stack(V_bytes);
        int L_length = LengthEncoder.encode(V_bytes.length, binStack);
        return L_length + V_bytes.length;
    }
}