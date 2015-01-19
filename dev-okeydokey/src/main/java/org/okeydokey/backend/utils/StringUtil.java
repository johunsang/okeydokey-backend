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