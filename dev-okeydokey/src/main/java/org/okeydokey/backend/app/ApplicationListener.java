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
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.okeydokey.backend.consts.LogConstants;
import org.okeydokey.backend.utils.BaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * An implementation of {@link ServletContextListener} web application lifecycle
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
@WebListener
public class ApplicationListener implements ServletContextListener {

	/**
	 * okeydokey.log
	 */
	private static Logger APP_LOG = LoggerFactory.getLogger("application");

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet. ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		write(LogConstants.LOG_BANNER_NUM);
		write("[OkeyDokey shutdown]");
		write(LogConstants.LOG_BANNER_NUM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext ctx = event.getServletContext();
			write(LogConstants.LOG_BANNER_NUM);
			write("[OkeyDokey start]");

			// 1. initialize application context
			IInitializeProcess appContextProcess = new InitializeAppContextProcessor();
			appContextProcess.process(ctx);

			// 2. initialize properties
			IInitializeProcess propertyProcess = new InitializePropertyProcessor();
			propertyProcess.process(ctx);

			// 3. initialize bizId
			IInitializeProcess bizIdProcess = new InitializeBizIdClassFileProcessor();
			bizIdProcess.process(ctx);

			write("[OkeyDokey started successfully]");
			write(LogConstants.LOG_BANNER_NUM);
		} catch (Exception e) {
			write(LogConstants.LOG_BANNER_NUM);
			write("[OkeyDokey fail]\n" + BaseUtil.toExceptionString(e));
			write(LogConstants.LOG_BANNER_NUM);
		}
	}

	private static void write(String logs) {
		APP_LOG.info(logs);
		System.out.println(logs);
	}

}