/* ------------------------------------------------------------------------
 *    Copyright (C) 2015  www.okeydokeyframework.org
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *    
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License along
 *    with this program; if not, write to the Free Software Foundation, Inc.,
 *    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ------------------------------------------------------------------------ 
 */
package org.okeydokey.backend.utils;

/**
 * <pre>
 * String Utility class
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public class StringUtil {

	/**
	 * Checks if a String is empty ("") or null.
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {

		if (input == null) {
			return true;
		}
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) > ' ') {
				return false;
			}
		}

		return true;
	}

	/**
	 * Compares two Strings, returning true if they are equal.
	 * 
	 * @param a
	 * @param b
	 * @return boolean
	 */
	public static boolean equals(String a, String b) {
		if (a == null) {
			return b == null;
		} else {
			return a.equals(b);
		}
	}
}