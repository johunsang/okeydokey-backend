package org.okeydokey.backend.exception;

/**
 * <pre>
 * Biz class can throw only BizException
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
@SuppressWarnings("serial")
public class BizException extends BaseException {

	/**
	 * Throw BizException
	 * 
	 * @param throwable
	 */
	public BizException(Throwable throwable) {
		super(throwable);
	}
	
	/**
	 * Throw BizException
	 * 
	 * @param messageName
	 */
	public BizException(String messageName) {
		super(messageName);
	}

	/**
	 * Throw BizException
	 * 
	 * @param messageName
	 * @param throwable
	 */
	public BizException(String messageName, Throwable throwable) {
		super(messageName, throwable);
	}
}
