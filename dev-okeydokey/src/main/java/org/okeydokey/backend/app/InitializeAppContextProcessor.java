package org.okeydokey.backend.app;

import javax.servlet.ServletContext;

import org.okeydokey.backend.context.ApplicationContext;
import org.okeydokey.backend.context.IApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * initialize application context
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public class InitializeAppContextProcessor implements IInitializeProcess {
	/**
	 * okeydokey.log
	 */
	private static Logger APP_LOG = LoggerFactory.getLogger("application");
	
	@Override
	public void process(ServletContext ctx) {


		write("1. Initializing application context");

		IApplicationContext appcontext = ApplicationContext.getInstance();
		appcontext.setContextRootPath(ctx.getRealPath(""));

		write("--->" + "Context root path : " + appcontext.getContextRootPath());

	}

	private static void write(String logs) {
		APP_LOG.info(logs);
		System.out.println(logs);
	}
}
