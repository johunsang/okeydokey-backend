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
package org.okeydokey.backend.exception;
/**
 * <pre>
 * BaseException extends Exception
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
@SuppressWarnings("serial")
public class BaseException extends Exception {

	protected String messageName = null;

	public BaseException(Throwable throwable) {
		this(null, throwable);
	}
	
	public BaseException(String messageName) {
		this(messageName, null);
	}

	public BaseException(String messageName, Throwable throwable) {
		super(messageName, throwable);
	}

	public String getMessageName() {
		return this.messageName;
	}

	public Throwable getThrowable() {
		return super.getCause();
	}

}
