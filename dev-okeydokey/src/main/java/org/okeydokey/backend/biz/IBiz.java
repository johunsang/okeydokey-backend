package org.okeydokey.backend.biz;

import org.okeydokey.backend.context.IOkeyDokeyContext;
import org.okeydokey.backend.exception.BizException;

/**
 * <pre>
 * Interface of biz class
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public interface IBiz {

	/**
	 * main transaction if exception occur during before method, this method won't be executed
	 * 
	 * @param context
	 *            IOkeyDokeyContext
	 * @throws Exception
	 */
	public abstract void execute(IOkeyDokeyContext context) throws BizException;


	/**
	 * post transaction although exception thrown during main or pre transaction, this method will be executed
	 * 
	 * @param context
	 *            IOkeyDokeyContext
	 * @throws Exception
	 */
	public abstract void executeFinally(IOkeyDokeyContext context) throws BizException;

}
