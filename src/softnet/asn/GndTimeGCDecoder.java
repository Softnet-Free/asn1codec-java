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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

class GndTimeGCDecoder
{
	public static GregorianCalendar decode(byte[] buffer, int offset, int V_length) throws FormatAsnException
	{
		if(V_length < 15 || V_length > 19)
        	throw new FormatAsnException();
		
		int upper_index = offset + V_length - 1;
		int digit = buffer[offset] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		int year = digit * 1000; 

		digit = buffer[offset + 1] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		year = year + digit * 100; 
		
		digit = buffer[offset + 2] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		year = year + digit * 10; 
		
		digit = buffer[offset + 3] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		year =  year + digit; 
		
		digit = buffer[offset + 4] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		int month = digit * 10; 

		digit = buffer[offset + 5] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		month = month + digit;
	
		digit = buffer[offset + 6] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		int day = digit * 10; 

		digit = buffer[offset + 7] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		day = day + digit; 

		digit = buffer[offset + 8] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		int hour = digit * 10; 

		digit = buffer[offset + 9] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		hour = hour + digit; 
		
		digit = buffer[offset + 10] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		int minute = digit * 10; 

		digit = buffer[offset + 11] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		minute = minute + digit; 
		
		digit = buffer[offset + 12] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		int second = digit * 10; 

		digit = buffer[offset + 13] - 48;
		if(digit < 0 || digit > 9)
        	throw new FormatAsnException();
		second = second + digit; 

        int millisecond = 0;

        offset = offset + 14;
        if (buffer[offset] == 46) // '.'
        {
            offset++;
            if(offset >= upper_index)
                throw new FormatAsnException();

            digit = buffer[offset] - 48;
            if (digit < 0 || digit > 9)
                throw new FormatAsnException();
            int d1 = digit;

            offset++;
            if (offset < upper_index)
            {
                digit = buffer[offset] - 48;
                if (digit < 0 || digit > 9)
                    throw new FormatAsnException();
                int d2 = digit;

                offset++;
                if (offset < upper_index)
                {
                    digit = buffer[offset] - 48;
                    if (digit < 0 || digit > 9)
                        throw new FormatAsnException();
                    int d3 = digit;
                    offset++;

                    millisecond = d1 * 100 + d2 * 10 + d3;
                }
                else
                {
                    millisecond = d1 * 100 + d2 * 10;
                }
            }
            else
            {
                millisecond = d1 * 100;
            }
        }

        if (offset != upper_index)
            throw new FormatAsnException();

        if (buffer[offset] != 90) // 'Z'
            throw new FormatAsnException();
		
		GregorianCalendar value = new GregorianCalendar();
		value.clear();		
		value.setTimeZone(TimeZone.getTimeZone("UTC"));
		value.set(Calendar.YEAR, year);
		value.set(Calendar.MONTH, month - 1);
		value.set(Calendar.DAY_OF_MONTH, day);
		value.set(Calendar.HOUR_OF_DAY, hour);
		value.set(Calendar.MINUTE, minute);
		value.set(Calendar.SECOND, second);
		value.set(Calendar.MILLISECOND, millisecond);
		value.get(Calendar.YEAR);
		value.setTimeZone(TimeZone.getDefault());
		
		return value;
	}
}
