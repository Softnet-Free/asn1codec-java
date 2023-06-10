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

class Int32Encoder implements ElementEncoder
{
	private int value;
	
	public Int32Encoder(int value)
	{
		this.value = value;
	}
	
    public boolean isConstructed() {
    	return false;
    }

	public int estimateSize()
	{
		if (value > -128)
        {
            if (value <= 127)
            {
                return 3;
            }
            else if (value <= 32767)
            {
                return 4;
            }
            else if (value <= 8388607)
            {
                return 5;
            }
            else 
                return 6;
        }
        else if (value >= -32768) {
            return 4;
        }
        else if (value >= -8388608) {
            return 5;
        }
        else
            return 6;	
	}
	
	public int encodeTLV(BinaryStack binStack)
	{
		int LV_length = encodeLV(binStack);
		binStack.stack(UniversalTag.Integer);
        return 1 + LV_length;
	}
		    
	public int encodeLV(BinaryStack binStack)
    {    	
    	byte b0 = (byte) (value & 0x000000ff);
    	byte b1 = (byte) ((value & 0x0000ff00) >> 8);
    	byte b2 = (byte) ((value & 0x00ff0000) >> 16);
    	byte b3 = (byte) ((value & 0xff000000) >> 24);
        
    	if(b3 == 0)
    	{
    		if(b2 == 0)
    		{
    			if(b1 == 0)
    			{
    				if(b0 == 0)
    				{
	    				binStack.stack((byte)0);
	    	            binStack.stack((byte)1);	
	    	            return 2;
    				}
    				else if(b0 > 0)
    				{
    					binStack.stack(b0);
	    	            binStack.stack((byte)1);	
	    	            return 2;
    				}
    				else
    				{
    					binStack.stack(b0);
	    	            binStack.stack((byte)0);	
	    	            binStack.stack((byte)2);	
	    	            return 3;    					
    				}
    			}
    			else if (b1 > 0)
    			{
    				binStack.stack(b0);
    				binStack.stack(b1);
    	            binStack.stack((byte)2);	
    	            return 3;    				
    			}
    			else
    			{
    				binStack.stack(b0);
    				binStack.stack(b1);
    				binStack.stack((byte)0);
    	            binStack.stack((byte)3);	
    	            return 4;    				
    			}
    		}
    		else if(b2 > 0)
    		{
    			binStack.stack(b0);
				binStack.stack(b1);
				binStack.stack(b2);
	            binStack.stack((byte)3);	
	            return 4;   
    		}
    		else
    		{
    			binStack.stack(b0);
				binStack.stack(b1);
				binStack.stack(b2);
				binStack.stack((byte)0);
	            binStack.stack((byte)4);	
	            return 5;   
    		}    		
    	}
    	else if(b3 > 0)
    	{
    		binStack.stack(b0);
			binStack.stack(b1);
			binStack.stack(b2);
			binStack.stack(b3);
            binStack.stack((byte)4);	
            return 5;  
    	}
    	else if(b3 == -1)
    	{
			if(b2 == -1)
			{
				if(b1 == -1)
				{
					if(b0 < 0)
					{
						binStack.stack(b0);
			            binStack.stack((byte)1);	
			            return 2;
					}
					else
					{
						binStack.stack(b0);
			            binStack.stack((byte)-1);	
			            binStack.stack((byte)2);	
			            return 3;
					}
				}
				else if(b1 < 0)
				{
					binStack.stack(b0);
					binStack.stack(b1);
		            binStack.stack((byte)2);	
		            return 3;  	
				}
				else
				{
					binStack.stack(b0);
					binStack.stack(b1);
					binStack.stack((byte)-1);
		            binStack.stack((byte)3);	
		            return 4;
				}
			}
			else if(b2 < 0)
			{
				binStack.stack(b0);
				binStack.stack(b1);
				binStack.stack(b2);
	            binStack.stack((byte)3);	
	            return 4;  			
			}
			else
			{
				binStack.stack(b0);
				binStack.stack(b1);
				binStack.stack(b2);
	            binStack.stack((byte)-1);	
	            binStack.stack((byte)4);	
	            return 5;  			
			}
    	}
    	else
    	{
    		binStack.stack(b0);
			binStack.stack(b1);
			binStack.stack(b2);
			binStack.stack(b3);
            binStack.stack((byte)4);	
            return 5;  
    	}
    }
}