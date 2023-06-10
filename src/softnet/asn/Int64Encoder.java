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

class Int64Encoder implements ElementEncoder
{
	private long value;
	
	public Int64Encoder(long value)
	{
		this.value = value;
	}
	
    public boolean isConstructed() {
    	return false;
    }

	public int estimateSize()
	{
        if (value > -128L)
        {
            if (value <= 127L)
            {
                return 3;
            }
            else if (value <= 32767L)
            {
                return 4;
            }
            else if (value <= 8388607L)
            {
                return 5;
            }
            else if (value <= 2147483647L)
            {
                return 6;
            }
            else if (value <= 549755813887L)
            {
                return 7;
            }
            else if (value <= 140737488355327L)
            {
                return 8;
            }
            else if (value <= 36028797018963967L)
            {
                return 9;
            }
            else
                return 10;
        }
        else if (value >= -32768L)
        {
            return 4;
        }
        else if (value >= -8388608L)
        {
            return 5;
        }
        else if (value >= -2147483648L)
        {
            return 6;
        }
        else if (value >= -549755813888L)
        {
            return 7;
        }
        else if (value >= -140737488355328L)
        {
            return 8;
        }
        else if (value >= -36028797018963967L)
        {
            return 9;
        }
        else
            return 10;	
	}
	
	public int encodeTLV(BinaryStack binStack)
	{
		int LV_length = encodeLV(binStack);
		binStack.stack(UniversalTag.Integer);
        return 1 + LV_length;
	}
	
	public int encodeLV(BinaryStack binStack)
    {
    	byte b0 = (byte) (value & 0x00000000000000ffL);
    	byte b1 = (byte) ((value & 0x000000000000ff00L) >> 8);
    	byte b2 = (byte) ((value & 0x0000000000ff0000L) >> 16);
    	byte b3 = (byte) ((value & 0x00000000ff000000L) >> 24);
    	byte b4 = (byte) ((value & 0x000000ff00000000L) >> 32);
    	byte b5 = (byte) ((value & 0x0000ff0000000000L) >> 40);
    	byte b6 = (byte) ((value & 0x00ff000000000000L) >> 48);
    	byte b7 = (byte) ((value & 0xff00000000000000L) >> 56);
    	
    	if(b7 == 0)
    	{
    		if(b6 == 0)
    		{
	    		if(b5 == 0)
	    		{
		    		if(b4 == 0)
		    		{
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
				    	else
				    	{
				    		binStack.stack(b0);
							binStack.stack(b1);
							binStack.stack(b2);
							binStack.stack(b3);
							binStack.stack((byte)0);
				            binStack.stack((byte)5);	
				            return 6;  
				    	}
		    		}
		    		else if(b4 > 0)
		    		{
		    			binStack.stack(b0);
						binStack.stack(b1);
						binStack.stack(b2);
						binStack.stack(b3);
						binStack.stack(b4);
			            binStack.stack((byte)5);	
			            return 6;  
		    		}
		    		else
		    		{
		    			binStack.stack(b0);
						binStack.stack(b1);
						binStack.stack(b2);
						binStack.stack(b3);
						binStack.stack(b4);
						binStack.stack((byte)0);
			            binStack.stack((byte)6);	
			            return 7;  
		    		}
	    		}
	    		else if (b5 > 0)
	    		{
	    			binStack.stack(b0);
					binStack.stack(b1);
					binStack.stack(b2);
					binStack.stack(b3);
					binStack.stack(b4);
					binStack.stack(b5);
		            binStack.stack((byte)6);	
		            return 7;  
	    		}
	    		else 
	    		{
	    			binStack.stack(b0);
					binStack.stack(b1);
					binStack.stack(b2);
					binStack.stack(b3);
					binStack.stack(b4);
					binStack.stack(b5);
					binStack.stack((byte)0);
		            binStack.stack((byte)7);	
		            return 8;  	    			
	    		}
    		}
    		else if(b6 > 0)
    		{
    			binStack.stack(b0);
				binStack.stack(b1);
				binStack.stack(b2);
				binStack.stack(b3);
				binStack.stack(b4);
				binStack.stack(b5);
				binStack.stack(b6);
	            binStack.stack((byte)7);	
	            return 8;  
    		}
    		else
    		{
    			binStack.stack(b0);
				binStack.stack(b1);
				binStack.stack(b2);
				binStack.stack(b3);
				binStack.stack(b4);
				binStack.stack(b5);
				binStack.stack(b6);
				binStack.stack((byte)0);
	            binStack.stack((byte)8);	
	            return 9;
    		}
    	}
    	else if(b7 > 0)
    	{
    		binStack.stack(b0);
			binStack.stack(b1);
			binStack.stack(b2);
			binStack.stack(b3);
			binStack.stack(b4);
			binStack.stack(b5);
			binStack.stack(b6);
			binStack.stack(b7);
            binStack.stack((byte)8);	
            return 9;
    	}
    	else if(b7 == -1)
    	{
    		if(b6 == -1)
			{
				if(b5 == -1)
				{
					if(b4 == -1)
					{
						if(b3 == -1)
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
						else if(b3 < 0)
						{
							binStack.stack(b0);
							binStack.stack(b1);
							binStack.stack(b2);
							binStack.stack(b3);
				            binStack.stack((byte)4);	
				            return 5;  			
						}
						else
						{
							binStack.stack(b0);
							binStack.stack(b1);
							binStack.stack(b2);
							binStack.stack(b3);
				            binStack.stack((byte)-1);	
				            binStack.stack((byte)5);	
				            return 6;  			
						}
					}
					else if(b4 < 0)
					{
						binStack.stack(b0);
						binStack.stack(b1);
						binStack.stack(b2);
						binStack.stack(b3);
						binStack.stack(b4);
			            binStack.stack((byte)5);	
			            return 6;  			
					}
					else
					{
						binStack.stack(b0);
						binStack.stack(b1);
						binStack.stack(b2);
						binStack.stack(b3);
						binStack.stack(b4);
			            binStack.stack((byte)-1);	
			            binStack.stack((byte)6);	
			            return 7;  			
					}
				}
				else if(b5 < 0)
				{
					binStack.stack(b0);
					binStack.stack(b1);
					binStack.stack(b2);
					binStack.stack(b3);
					binStack.stack(b4);
					binStack.stack(b5);
		            binStack.stack((byte)6);	
		            return 7;  			
				}
				else
				{
					binStack.stack(b0);
					binStack.stack(b1);
					binStack.stack(b2);
					binStack.stack(b3);
					binStack.stack(b4);
					binStack.stack(b5);
		            binStack.stack((byte)-1);	
		            binStack.stack((byte)7);	
		            return 8;  			
				}
			}
			else if(b6 < 0)
			{
				binStack.stack(b0);
				binStack.stack(b1);
				binStack.stack(b2);
				binStack.stack(b3);
				binStack.stack(b4);
				binStack.stack(b5);
				binStack.stack(b6);
	            binStack.stack((byte)7);	
	            return 8;  			
			}
			else
			{
				binStack.stack(b0);
				binStack.stack(b1);
				binStack.stack(b2);
				binStack.stack(b3);
				binStack.stack(b4);
				binStack.stack(b5);
				binStack.stack(b6);
	            binStack.stack((byte)-1);	
	            binStack.stack((byte)8);	
	            return 9;  			
			}
    	}
    	else
    	{
    		binStack.stack(b0);
			binStack.stack(b1);
			binStack.stack(b2);
			binStack.stack(b3);
			binStack.stack(b4);
			binStack.stack(b5);
			binStack.stack(b6);
			binStack.stack(b7);
            binStack.stack((byte)8);	
            return 9;
    	}
    }
	
}