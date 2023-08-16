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

public enum UType {
	Boolean(1),
	Integer(2),
	OctetString(4),
	Null(5),
	Real(9),
	UTF8String(12),
	Sequence(16),
	PrintableString(19),
	IA5String(22),
	GeneralizedTime(24),
	BMPString(30);

	public final int tag;
	private UType(int tag) { 
		this.tag = tag; 
	}
}
