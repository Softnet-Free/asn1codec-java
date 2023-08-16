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
