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

class BinaryStack
{
	private byte[] m_buffer = null;
	private int m_position = 0;

    public byte[] buffer()
    {
        return m_buffer;
    }

    public int position()
    {
        return m_position;
    }

    public int count()
    {
        return m_buffer.length - m_position;
    }

    public void allocate(int memorySize)
    {
        m_buffer = new byte[memorySize];
        m_position = memorySize;
    }

    public void stack(byte value)
    {
    	m_position -= 1;
        m_buffer[m_position] = value;
    }

    public void stack(int byteValue)
    {
    	m_position -= 1;
        m_buffer[m_position] = (byte)byteValue;
    }
    
    public void stack(byte[] data)
    {
    	m_position -= data.length;
        System.arraycopy(data, 0, m_buffer, m_position, data.length);
    }
    
    public void stack(byte[] data, int offset, int size)
    {
    	m_position -= size;
    	System.arraycopy(data, offset, m_buffer, m_position, size);
    }
}
