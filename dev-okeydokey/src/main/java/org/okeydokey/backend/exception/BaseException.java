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
