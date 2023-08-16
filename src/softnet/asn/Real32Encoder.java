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

class Real32Encoder implements ElementEncoder
{
	private byte[] V_bytes;
	private int V_length;
	
    private Real32Encoder()
    {
    	V_bytes = new byte[6];
    	V_length = 0;
    }

    public static Real32Encoder create(float value)
    {
    	Real32Encoder encoder = new Real32Encoder();
        encoder.encodeV(value);
        return encoder;
    }
        
    public boolean isConstructed() {
    	return false;
    }

    public int estimateSize() {
    	return 2 + V_length;
    }

    public int encodeTLV(BinaryStack binStack)
    {
    	if(V_length > 0)
    		binStack.stack(V_bytes, 0, V_length);
    	binStack.stack((byte)V_length);    	
        binStack.stack(UniversalTag.Real);
        return 2 + V_length;
    }

    public int encodeLV(BinaryStack binStack)
    {
    	if(V_length > 0)
    		binStack.stack(V_bytes, 0, V_length);
    	binStack.stack((byte)V_length);    	
        return 1 + V_length;
    }
    
    private void encodeV(float value)
    {
    	int floatBits = Float.floatToIntBits(value);
        int exponent = (floatBits & 0x7f800000) >> 23;

        if (exponent >= 1) // normal float / +infinity / -infinity / NaN
        {
        	if(exponent < 255) // normal float
        	{
        		exponent -= 127;
        		int mantissa = (floatBits & 0x007fffff) | 0x00800000;
        		
        		int insignificant_bits_number = countInsignificantBits(mantissa);
        		exponent -= (23 - insignificant_bits_number);
       			mantissa = mantissa >> insignificant_bits_number;
        	        		
        		encodeExponent(exponent);
        		encodeHeader(floatBits);
        		encodeMantissa(mantissa);
        	}
        	else // exponent == 255 /// +infinity / -infinity / NaN
        	{
        		int mantissa = floatBits & 0x007fffff;
        		if(mantissa == 0) // +infinity / -infinity
        		{
        			if(floatBits >= 0) //  +infinity
        			{
        				V_bytes[0] = 0x40;
        				V_length = 1;
        			}
        			else // -infinity
        			{
        				V_bytes[0] = 0x41;
        				V_length = 1;        				
        			}
        		}
        		else // NaN
        		{
    				V_bytes[0] = 0x42;
    				V_length = 1;        			
        		}
        	}
        }
        else // exponent == 0 /// subnormal float / +0 / -0
        {
        	int mantissa = floatBits & 0x007fffff;
            if(mantissa > 0) // subnormal float
            {
                exponent = -126;
                int insignificant_bits_number = countInsignificantBits(mantissa);
       			exponent -= (23 - insignificant_bits_number);
       			mantissa = mantissa >> insignificant_bits_number;
        	
        		encodeExponent(exponent);
        		encodeHeader(floatBits);
        		encodeMantissa(mantissa);
            }
            else // +0 / -0
            {
            	if(floatBits >= 0) // +0 
            	{
            		V_length = 0;
            	}
            	else // -0
            	{
            		V_bytes[0] = 0x43; 
            		V_length = 1;
            	}
            }
        }
    }

    private int countInsignificantBits(int mantissa)
    {
    	int masked_mantissa = mantissa & 0x00fff000;
    	if(masked_mantissa == mantissa) 
    	{
    		masked_mantissa = mantissa & 0x00fc0000;
    		if(masked_mantissa == mantissa) 
    		{
        		masked_mantissa = mantissa & 0x00e00000;
        		if(masked_mantissa == mantissa) 
        		{
        			masked_mantissa = mantissa & 0x00c00000;
            		if(masked_mantissa != mantissa)
            			return 21;

        			masked_mantissa = mantissa & 0x00800000;
            		if(masked_mantissa != mantissa)
            			return 22;

            		return 23;
        		}
        		else
        		{
        			masked_mantissa = mantissa & 0x00f80000;
            		if(masked_mantissa != mantissa)
            			return 18;

        			masked_mantissa = mantissa & 0x00f00000;
            		if(masked_mantissa != mantissa)
            			return 19;
        			
            		return 20;
        		}
    		}
    		else    			
    		{
    			masked_mantissa = mantissa & 0x00ff8000;
    			if(masked_mantissa == mantissa) 
    			{
        			masked_mantissa = mantissa & 0x00ffe000;
            		if(masked_mantissa != mantissa)
            			return 12;

        			masked_mantissa = mantissa & 0x00ffc000;
            		if(masked_mantissa != mantissa)
            			return 13;

        			return 14;
    			}
    			else
    			{
    				masked_mantissa = mantissa & 0x00ff0000;
            		if(masked_mantissa != mantissa)
            			return 15;

    				masked_mantissa = mantissa & 0x00fe0000;
            		if(masked_mantissa != mantissa)
            			return 16;

        			return 17;
    			}
    		}    		
    	}
    	else
    	{
    		masked_mantissa = mantissa & 0x00ffffc0;
    		if(masked_mantissa == mantissa) 
    		{
        		masked_mantissa = mantissa & 0x00fffe00;
        		if(masked_mantissa == mantissa) 
        		{
        			masked_mantissa = mantissa & 0x00fffc00;
            		if(masked_mantissa != mantissa)
            			return 9;

        			masked_mantissa = mantissa & 0x00fff800;
            		if(masked_mantissa != mantissa)
            			return 10;

            		return 11;
        		}
        		else
        		{
        			masked_mantissa = mantissa & 0x00ffff80;
            		if(masked_mantissa != mantissa)
            			return 6;

        			masked_mantissa = mantissa & 0x00ffff00;
            		if(masked_mantissa != mantissa)
            			return 7;
        			
        			return 8;
        		}    			
    		}
    		else
    		{
        		masked_mantissa = mantissa & 0x00fffff8;
        		if(masked_mantissa == mantissa) 
        		{
        			masked_mantissa = mantissa & 0x00fffff0;
            		if(masked_mantissa != mantissa)
            			return 3;

        			masked_mantissa = mantissa & 0x00ffffe0;
            		if(masked_mantissa != mantissa)
            			return 4;

        			return 5;
        		}
        		else
        		{
        			masked_mantissa = mantissa & 0x00fffffe;
            		if(masked_mantissa != mantissa)
            			return 0;
        			
        			masked_mantissa = mantissa & 0x00fffffc;
            		if(masked_mantissa != mantissa)
            			return 1;
            		
            		return 2;
        		}
    		}
    	}
    }
    
    private void encodeHeader(int floatBits)
    {
        if (floatBits >= 0) // positive value
        {
            if (V_length == 1)
            	V_bytes[0] = (byte)0x80;                
            else // == 2
            	V_bytes[0] = (byte)0x81;
        }
        else // negative value
        {
            if (V_length == 1)
            	V_bytes[0] = (byte)0xC0;
            else // == 2
            	V_bytes[0] = (byte)0xC1;
        }
        V_length++;
    }

    private void encodeExponent(int exponent)
    {
        int b0 = exponent & 0x000000FF;
        int b1 = (exponent & 0x0000FF00) >> 8;
        
        if(b1 == 255)
        {
        	if(b0 >= 128)
        	{
        		V_bytes[1] = (byte)(b0 - 256);
        		V_length = 1;
        	}
        	else
        	{
        		V_bytes[1] = (byte)(-1);
        		V_bytes[2] = (byte)b0;
        		V_length = 2;
        	}
        }
        else if (b1 == 0)
        {
        	if (b0 <= 127)
            {
        		V_bytes[1] = (byte)b0;
        		V_length = 1;
            }
            else
            {
        		V_bytes[1] = (byte)0;
        		V_bytes[2] = (byte)(b0 - 256);
        		V_length = 2;
            }
        }
        else if (b1 >= 128)
        {
    		V_bytes[1] = (byte)(b1 - 256);
        	if(b0 >= 128)
        		V_bytes[2] = (byte)(b0 - 256);
        	else
        		V_bytes[2] = (byte)b0;
    		V_length = 2;
        }
        else // b1 <= 127
        {
    		V_bytes[1] = (byte)b1;
        	if(b0 >= 128)
        		V_bytes[2] = (byte)(b0 - 256);
        	else
        		V_bytes[2] = (byte)b0;
    		V_length = 2;
        }
    }

    private void encodeMantissa(int mantissa)
    {
        byte b0 = (byte)(mantissa & 0x000000ff);
        byte b1 = (byte)((mantissa & 0x0000ff00) >> 8);
        byte b2 = (byte)((mantissa & 0x00ff0000) >> 16);

        if (b2 == 0)
        {
            if (b1 == 0)
            {
               	V_bytes[V_length] = b0;
                V_length += 1;
            }
            else
            {
               	V_bytes[V_length] = b1;
               	V_bytes[V_length + 1] = b0;
                V_length += 2;
            }
        }
        else
        {
           	V_bytes[V_length] = b2;
           	V_bytes[V_length + 1] = b1;
           	V_bytes[V_length + 2] = b0;
            V_length += 3;
        }
    }
}
















