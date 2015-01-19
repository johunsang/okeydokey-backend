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
