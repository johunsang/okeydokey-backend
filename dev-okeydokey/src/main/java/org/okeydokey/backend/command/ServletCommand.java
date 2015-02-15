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
package org.okeydokey.backend.command;

import java.io.IOException;

import javax.servlet.ReadListener;

import org.okeydokey.backend.consts.ExtensionConstants;
import org.okeydokey.backend.context.IOkeyDokeyContext;
import org.okeydokey.backend.servlet.listener.BinaryReadListener;
import org.okeydokey.backend.servlet.listener.TextReadListener;
import org.okeydokey.backend.utils.StringUtil;

/**
 * <pre>
 * Implementation of {@link org.okeydokey.backend.context.IServletContext}
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public class ServletCommand implements ICommand {

	/**
	 * single ServletCommand instance
	 */
	private volatile static ServletCommand instance;

	/**
	 * Return single ServletCommand instance
	 * 
	 * @return ServletCommand instance
	 */
	public static ServletCommand getInstance() {
		if (instance == null) {
			synchronized (ServletCommand.class) {
				if (instance == null) {
					instance = new ServletCommand();
				}
			}
		}
		return instance;
	}

	/**
	 * prevent instantiation
	 */
	private ServletCommand() {

	}

	@Override
	public void execute(IOkeyDokeyContext context) throws Exception {
		// Start async processing
		context.getRequest().getInputStream().setReadListener(getListenerByMessageExtension(context));

	}

	/**
	 * Get ReadListener by messageExtension
	 * 
	 * @throws IOException
	 * 
	 */
	private ReadListener getListenerByMessageExtension(IOkeyDokeyContext context) throws IOException {
		String messageExtension = context.getMessageExtension();

		if (StringUtil.isEmpty(messageExtension)) {
			throw new RuntimeException("MessageExtension is empty!!");
		}

		if (StringUtil.equals(ExtensionConstants.TEXT_EXTENSION, messageExtension)) { // txt
			return new TextReadListener(context);
		} else if (StringUtil.equals(ExtensionConstants.BINARAY_EXTENSION, messageExtension)) { // bnry
			return new BinaryReadListener(context);
		} else {
			throw new IllegalArgumentException("MessageExtension is wrong! : " + messageExtension);
		}
	}

}
