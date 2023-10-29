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

/**
 * @author Robert Koifman
**/

/**
 * The Softnet ASN.1 Codec (Java) utilizes data types and packages from Java SE 1.7.
 * If your application is assumed to use the Java 1.7 or Java 1.8 runtime,
 * you can also target this library to that runtime. In this case, to compile this library
 * you need to comment out the following code. Otherwise, starting with Java 1.9,
 * Java applications use Java Platform Module System (JMPS), and this code is required
 * to declare module-related information and dependencies.
**/
/*
module asncodec {
	exports softnet.asn;	
}
*/