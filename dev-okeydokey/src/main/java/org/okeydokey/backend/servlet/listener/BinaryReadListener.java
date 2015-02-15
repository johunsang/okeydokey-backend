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
package org.okeydokey.backend.servlet.listener;

import java.io.IOException;
import java.nio.ByteBuffer;
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
public class BinaryReadListener implements ReadListener {
	/**
	 * default buffer size of reading and writing stream
	 */
	private static final int DEFAULT_BUFFER_SIZE = 1024;

	/**
	 * queue of ByteBuffer
	 */
	private Queue<ByteBuffer> queue = new LinkedBlockingQueue<ByteBuffer>();

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
	 * ServletInputStream
	 */
	private ServletInputStream input = null;

	/**
	 * ServletOutputStream
	 */
	private ServletOutputStream output = null;

	/**
	 * Set AsyncContext,IOkeyDokeyContext
	 * 
	 * @param context
	 * @throws IOException
	 */
	public BinaryReadListener(IOkeyDokeyContext context) throws IOException {
		this.context = context;
		this.asycContext = context.getAsyncContext();
		this.input = context.getRequest().getInputStream();
		this.output = context.getResponse().getOutputStream();
	}

	@SuppressWarnings("resource")
	@Override
	public void onDataAvailable() throws IOException {
		if (input.isReady()) {
			int len = -1;
			int size = 0;
			int bufferSize = getBufferSize();
			byte[] buffer = new byte[bufferSize];
			BufferReuseByteArrayOutputStream byteOutput = null;
			int limitSize = getInputLimitSize();
			try {
				byteOutput = new BufferReuseByteArrayOutputStream(buffer);
				while (input.isReady() && !input.isFinished() && (len = input.read(buffer)) != -1) {
					byteOutput.write(buffer, 0, len);
					size += len;
					if (size > limitSize) {
						throw new IOException("Request exceeds limit, input : " + size + ", limit : " + limitSize);
					}
				}
				ByteBuffer requstByteBuffer = ByteBuffer.wrap(byteOutput.toByteArray());
				if (null == requstByteBuffer || requstByteBuffer.capacity() == 0) {
					throw new IOException("Request bytebuffer is empty");
				}
				byteOutput.flush();
				// Set byte buffer to context
				context.setRequestByteBuffer(requstByteBuffer);
				queue.add(requstByteBuffer);
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
					while (queue.peek() != null && context.getResponse().getOutputStream().isReady()) {
						// Set request ByteBuffer from queue
						context.setRequestByteBuffer(queue.poll());
						// Execute service
						ICommand command = ServiceCommand.getInstance();
						try {
							command.execute(context);
						} catch (Exception e) {
						}
						// Writing ByteBuffer stream
						ByteBuffer resopnseByteBuffer = null;
						try {
							// Get response byte buffer from context
							resopnseByteBuffer = context.getResponseByteBuffer();
							if (null == resopnseByteBuffer || resopnseByteBuffer.capacity() == 0) {
								throw new IOException("Response bytebuffer is empty");
							}
							if (output.isReady()) {
								output.write(resopnseByteBuffer.array());
								context.getResponse().setContentLength(resopnseByteBuffer.capacity());
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
	 * Get buffer size of reading and writing stream
	 * 
	 * @return size of buffer
	 */
	private static int getBufferSize() {
		int bufferSize = BaseUtil.getBinaryBufferSize();
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
