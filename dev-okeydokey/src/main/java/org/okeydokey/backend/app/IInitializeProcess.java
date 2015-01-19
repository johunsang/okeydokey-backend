package org.okeydokey.backend.app;

import javax.servlet.ServletContext;

/**
 * <pre>
 * Interface that process initializing applicaiton context
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public interface IInitializeProcess {

	public void process(ServletContext ctx);

}
