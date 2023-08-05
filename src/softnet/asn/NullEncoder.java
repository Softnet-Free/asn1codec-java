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

public class NullEncoder implements ElementEncoder
{
	public int estimateSize() {
		return 2;		
	}
	
    public boolean isConstructed() {
    	return false;
    }

	public int encodeTLV(BinaryStack binStack) {
        binStack.stack((byte)0);
        binStack.stack(UniversalTag.Null);
        return 2;
	}
	
	public int encodeLV(BinaryStack binStack) {
        binStack.stack((byte)0);
        return 1;
	}
}