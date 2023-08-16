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

public class FormatAsnException extends AsnException
{
	private static final long serialVersionUID = 2272592628121722310L;

	public FormatAsnException(String message)
	{		
		super(message);
	}
	
    public FormatAsnException()
    {
    	super("The input data has an invalid ASN.1 format.");
    }
}
