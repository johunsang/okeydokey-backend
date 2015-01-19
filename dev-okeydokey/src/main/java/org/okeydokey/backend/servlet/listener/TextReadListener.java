package org.okeydokey.backend.servlet.listener;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

import org.okeydokey.backend.command.ICommand;
import org.okeydokey.backend.command.ServiceCommand;
import org.okeydokey.backend.context.IOkeyDokeyContext;
import org.okeydokey.backend.utils.BaseUtil;
import org.okeydokey.backend.utils.BufferReuseByteArrayOutputStream;
import org.okeydokey.backend.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Implementation of {@link javax.servlet.ReadListener}
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public class TextReadListener implements ReadListener {
	/**
	 * default buffer size of reading and writing stream
	 */
	private static final int DEFAULT_BUFFER_SIZE = 1024;

	/**
	 * default charset
	 */
	private static final String DEFALUT_CAHRSET = "UTF-8";

	/**
	 * IOkeyDokeyContext
	 */
	private IOkeyDokeyContext context = null;

	/**
	 * AsyncContext
	 */
	private AsyncContext asycContext = null;

	/**
	 * biz.log
	 */
	private static Logger BIZ_LOG = LoggerFactory.getLogger("biz");

	/**
	 * queue of String
	 */
	private Queue<String> queue = new LinkedBlockingQueue<String>();

	/**
	 * 
	 */
	private ServletInputStream input = null;

	/**
	 * 
	 */
	private ServletOutputStream output = null;

	/**
	 * Set IOkeyDokeyContext
	 * 
	 * @param context
	 * @throws IOException
	 */
	public TextReadListener(IOkeyDokeyContext context) throws IOException {
		this.context = context;
		this.asycContext = context.getAsyncContext();
		this.input = context.getRequest().getInputStream();
		this.output = context.getResponse().getOutputStream();
	}

	@Override
	public void onDataAvailable() throws IOException {
		if (input.isReady()) {
			int len = -1;
			int size = 0;
			int bufferSize = getBufferSize();
			byte[] buffer = new byte[bufferSize];
			String charset = getCharSet();
			BufferReuseByteArrayOutputStream byteOutput = null;
			int limitSize = getInputLimitSize();
			try {
				byteOutput = new BufferReuseByteArrayOutputStream(buffer);
				while (input.isReady() && (len = input.read(buffer)) != -1) {
					byteOutput.write(buffer, 0, len);
					size += len;
					if (size > limitSize) {
						throw new IOException("Request exceeds limit, input : " + size + ", limit : " + limitSize);
					}
				}
				if (null == byteOutput || byteOutput.size() == 0) {
					throw new IOException("Request String is empty");
				}
				byteOutput.flush();
				// Set request text to context
				String requesString = byteOutput.toString(charset);
				context.setRequestTextString(requesString);
				queue.add(requesString);
			} catch (IOException e) {
				throw e;
			} finally {
				try {
					if (byteOutput != null)
						byteOutput.close();
				} catch (Exception e) {
				}
			}
		}
	}

	@Override
	public void onAllDataRead() throws IOException {
		output.setWriteListener(new WriteListener() {

			@Override
			public void onWritePossible() throws IOException {
				if (output.isReady() && input.isFinished()) {
					while (queue.peek() != null && output.isReady()) {
						// Set request text from queue
						context.setRequestTextString(queue.poll());
						// Execute service
						ICommand command = ServiceCommand.getInstance();
						try {
							command.execute(context);
						} catch (Exception e) {
						}
						String response = null;
						try {
							// String value from biz
							response = context.getResponseTextString();
							if (StringUtil.isEmpty(response)) {
								throw new IOException("Response String is empty");
							}
							byte[] bResopnse = response.getBytes(getCharSet());
							if (output.isReady()) {
								output.write(bResopnse);
								context.getResponse().setContentLength(bResopnse.length);
							}
						} catch (IOException e) {
							throw e;
						}
					}
					if (queue.peek() == null) {
						asycContext.complete();
					}
				}
			}

			@Override
			public void onError(Throwable exception) {
				BIZ_LOG.error("Writing stream error : " + BaseUtil.toExceptionString(exception));
				asycContext.complete();
			}
		});
	}

	@Override
	public void onError(final Throwable exception) {
		BIZ_LOG.error("Reading stream error : " + BaseUtil.toExceptionString(exception));
		asycContext.complete();
	}

	/**
	 * Get charset by message extension of URI
	 * 
	 * @return charSet
	 */
	private static String getCharSet() {
		String charSet = BaseUtil.getTextCharSet();
		if (StringUtil.isEmpty(charSet)) {
			charSet = DEFALUT_CAHRSET;
		}
		return charSet;
	}

	/**
	 * Get buffer size of reading and writing stream
	 * 
	 * @return size of buffer
	 */
	private static int getBufferSize() {
		int bufferSize = BaseUtil.getTextBufferSize();
		if (bufferSize == 0) {
			bufferSize = DEFAULT_BUFFER_SIZE;
		}
		return bufferSize;
	}

	/**
	 * Get size of limit
	 * 
	 * @return size of limit
	 */
	private static int getInputLimitSize() {
		int limitSize = BaseUtil.getInputLimitSize();
		int bufferSize = getBufferSize();
		if (limitSize <= 0 || limitSize < bufferSize) {
			limitSize = bufferSize;
		}
		return limitSize;
	}
}
