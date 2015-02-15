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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <pre>
 * Base Utility class
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public final class BaseUtil {

	/**
	 * Get name of system OS
	 * 
	 * @return name of system OS
	 */
	public static String getOsName() {
		return System.getProperty("os.name");
	}

	/**
	 * Get name of java version
	 * 
	 * @return name of java version
	 */
	public static String getVmVersion() {
		return System.getProperty("java.version");
	}

	/**
	 * Get class loader of context
	 * 
	 * @return ClassLoader
	 */
	public static ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * Get name of local host
	 * 
	 * @return name of local host
	 */
	public static String getHostName() {
		String returnValue = null;
		try {
			returnValue = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			returnValue = "";
		}
		return returnValue;
	}

	/**
	 * Get String value of config properties
	 * 
	 * @param key
	 * @return String value of config properties
	 */
	public static String getConfig(String key) {
		return PropertyLoader.getPropertyInstance().getConfig(key);
	}

	/**
	 * Get int value of config properties
	 * 
	 * @param key
	 * @return int value of config properties
	 */
	public static int getIntConfig(String key) {
		return PropertyLoader.getPropertyInstance().getIntConfig(key);
	}

	/**
	 * Get String value of code properties
	 * 
	 * @param code
	 * @return String value of code properties
	 */
	public static String getCode(String code) {
		return PropertyLoader.getPropertyInstance().getCode(code);
	}

	/**
	 * Get String value of message properties
	 * 
	 * @param id
	 * @return String value of message properties
	 */
	public static String getMessage(String id) {
		return PropertyLoader.getPropertyInstance().getMessage(id);
	}

	/**
	 * Get String value of okeydokey properties
	 * 
	 * @param id
	 * @return String value of okeydokey properties
	 */
	public static String getOkeydokey(String id) {
		return PropertyLoader.getPropertyInstance().getOkeydokey(id);
	}

	/**
	 * Get int value of okeydokey properties
	 * 
	 * @param id
	 * @return int value of okeydokey properties
	 */
	public static int getIntOkeydokey(String id) {
		return PropertyLoader.getPropertyInstance().getIntOkeydokey(id);
	}

	/**
	 * Return String of exception stack
	 * 
	 * @param th
	 * @return String of exception stack
	 */
	public static String toExceptionString(Throwable th) {
		if (th == null)
			return "";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		th.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * Get path of class files
	 * 
	 * @return path of class files
	 */
	public static String getBizClassStorePath() {
		return getOkeydokey("okeydokey.bizclass.store.path");
	}

	/**
	 * Get buffer size of binary
	 * 
	 * @return buffer size of binary
	 */
	public static int getBinaryBufferSize() {
		return getIntOkeydokey("okeydokey.binary.custom.read.write.buffer.size");
	}

	/**
	 * Get buffer size of text
	 * 
	 * @return buffer size of text
	 */
	public static int getTextBufferSize() {
		return getIntOkeydokey("okeydokey.text.custom.read.write.buffer.size");
	}

	/**
	 * Get charSet of text
	 * 
	 * @return charSet of text
	 */
	public static String getTextCharSet() {
		return getOkeydokey("okeydokey.text.custom.charSet");
	}

	/**
	 * Get max size of input stream
	 * 
	 * @return max size of input stream
	 */
	public static int getInputLimitSize() {
		return getIntOkeydokey("okeydokey.limit.input.size");
	}

	/**
	 * Get min size of input stream
	 * 
	 * @return min size of input stream
	 */
	public static int getInputMinSize() {
		return getIntOkeydokey("okeydokey.min.input.size");
	}

}
