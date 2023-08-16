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

class IA5StringDecoder
{
	public static String decode(byte[] buffer, int offset, int V_length) throws FormatAsnException
    {
        if (V_length == 0)
            return "";
        
        String value = new String(buffer, offset, V_length, java.nio.charset.StandardCharsets.US_ASCII);
        
        if (Pattern.matches("[^\u0000-\u007F]", value))
        	throw new FormatAsnException("The input data contains characters that are not permitted in Asn1 IA5String.");
        
        return value;
    }
}
