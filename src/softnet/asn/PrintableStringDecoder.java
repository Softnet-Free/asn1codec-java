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

import java.util.regex.Pattern;

class PrintableStringDecoder
{
    public static String decode(byte[] buffer, int offset, int V_length) throws FormatAsnException
    {
        if (V_length == 0)
            return "";
        
        String value = new String(buffer, offset, V_length, java.nio.charset.StandardCharsets.US_ASCII);
        
        if (Pattern.matches("^[\\u0020-\\u007F]+$", value) == false)
        	throw new FormatAsnException("The input data contains characters that are not permitted in Asn1 PrintableString.");
        
        if (Pattern.matches("^[A-Za-z0-9\\s'()+,\\-./:=?]*$", value) == false)
        	throw new FormatAsnException("The input data contains characters that are not permitted in Asn1 PrintableString.");
        
        return value;
    }
}
