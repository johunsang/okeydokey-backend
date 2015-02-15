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
package org.okeydokey.backend.app;

import javax.servlet.ServletContext;

import org.okeydokey.backend.utils.PropertyLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Initialize properties
 * 
 * 1. config.properties 
 * 2. code.properties
 * 3. message.properties 
 * 4. okeydokey.properties
 * 
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public class InitializePropertyProcessor implements IInitializeProcess {
	/**
	 * okeydokey.log
	 */
	private static Logger APP_LOG = LoggerFactory.getLogger("application");

	@Override
	public void process(ServletContext ctx) {

		write("2. Initializing information of properties files");

		String configPropertyFile = ctx.getRealPath("/WEB-INF/config/config.properties");
		String codePropertyFile = ctx.getRealPath("/WEB-INF/config/code.properties");
		String messagePropertyFile = ctx.getRealPath("/WEB-INF/config/message.properties");
		String okeydokeyPropertyFile = ctx.getRealPath("/WEB-INF/config/okeydokey.properties");

		write("---> Config File : " + configPropertyFile);
		write("---> Code File : " + codePropertyFile);
		write("---> Message File : " + messagePropertyFile);
		write("---> Okeydokey File : " + okeydokeyPropertyFile);

		// Load Properties
		PropertyLoader.getPropertyInstance().setPropertyFile(configPropertyFile, codePropertyFile, messagePropertyFile, okeydokeyPropertyFile);

	}

	private static void write(String logs) {
		APP_LOG.info(logs);
		System.out.println(logs);
	}
}
