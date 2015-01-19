package org.okeydokey.backend.command;

import org.okeydokey.backend.context.IOkeyDokeyContext;

/**
 * <pre>
 * Interface commanding HTTP request process.
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public interface ICommand {

	/**
	 * <pre>
	 * Execute servlet Command
	 * </pre>
	 * 
	 * @param context
	 *            IOkeyDokeyContext
	 */
	public void execute(IOkeyDokeyContext context) throws Exception;

}
