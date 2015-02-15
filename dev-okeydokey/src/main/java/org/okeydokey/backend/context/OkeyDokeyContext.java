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
package org.okeydokey.backend.context;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.okeydokey.backend.data.BizMap;
import org.okeydokey.backend.utils.ServletIdHelper;

/**
 * <pre>
 * This class will be initialized and instantiated when HTTP request event.
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.01.01
 */
public class OkeyDokeyContext implements IOkeyDokeyContext {
	/**
	 * value transfer object between biz class
	 */
	protected BizMap<String, Object> bizMap;

	/**
	 * if BizException thrown
	 */
	protected boolean bizExceptionThrown;

	/**
	 * exception of biz class
	 */
	protected Throwable bizException;

	/**
	 * unique servlet id
	 */
	protected String servletId;

	/**
	 * bizId
	 */
	protected String bizId;

	/**
	 * bizId which called first
	 */
	protected String originalBizId;

	/**
	 * messageExtensione
	 */
	protected String messageExtension;

	/**
	 * fully qualified name of class
	 */
	protected String className;

	/**
	 * HttpServletRequest
	 */
	protected final transient HttpServletRequest request;

	/**
	 * HttpServletResponse
	 */
	protected final transient HttpServletResponse response;

	/**
	 * request ByteBuffer
	 */
	protected ByteBuffer requestByteBuffer;

	/**
	 * response ByteBuffer
	 */
	protected ByteBuffer responseByteBuffer;

	/**
	 * request text string
	 */
	protected String requestTextString;

	/**
	 * response text string
	 */
	protected String responseTextString;

	/**
	 * context of async
	 */
	protected final transient AsyncContext asyncContext;

	/**
	 * constructor
	 * 
	 * Initialize ServletContext class. Set bizId and message extension. Make unique servlet id
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param asyncContext
	 *            AsyncContext
	 * @throws Exception
	 */
	public OkeyDokeyContext(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Set httpRequest, httpResponse, asyncContext
		this.request = request;
		this.response = response;
		this.asyncContext = request.startAsync(this.request, this.response);

		this.asyncContext.addListener(new AsyncListener() {
			public void onComplete(AsyncEvent event) throws IOException {
			}

			public void onError(AsyncEvent event) {
			}

			public void onStartAsync(AsyncEvent event) {
			}

			public void onTimeout(AsyncEvent event) {
			}
		});

		// Initialize bizmap
		bizMap = new BizMap<String, Object>();
		bizExceptionThrown = false;

		// Obtain bizId and message extension
		obtainBizIdMessageExtension(response);

		// Make an unique id of servlet by the bizId of servlet path and session id of HTTP request
		this.servletId = ServletIdHelper.getInstance().newServletId(this.bizId, request.getSession().getId());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#getBizMap()
	 */
	@Override
	public BizMap<String, Object> getBizMap() {
		return bizMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#setBizMap(org.okeydokey.backend.data.impl.BizMap)
	 */
	@Override
	public void setBizMap(BizMap<String, Object> bizMap) {
		this.bizMap = bizMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#isBizExceptionThrown()
	 */
	@Override
	public boolean isBizExceptionThrown() {
		return bizExceptionThrown;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#setBizExceptionThrown(boolean)
	 */
	@Override
	public void setBizExceptionThrown(boolean bizExceptionThrown) {
		this.bizExceptionThrown = bizExceptionThrown;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#getBizException()
	 */
	@Override
	public Throwable getBizException() {
		return bizException;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#setBizException(java.lang.Throwable)
	 */
	@Override
	public void setBizException(Throwable bizException) {
		this.bizException = bizException;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#getMessageExtension()
	 */
	@Override
	public String getMessageExtension() {
		return messageExtension;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#setMessageExtension(java.lang.String)
	 */
	@Override
	public void setMessageExtension(String messageExtension) {
		this.messageExtension = messageExtension;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#getClassName()
	 */
	@Override
	public String getClassName() {
		return className;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#setClassName(java.lang.String)
	 */
	@Override
	public void setClassName(String className) {
		this.className = className;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#getResponse()
	 */
	@Override
	public HttpServletResponse getResponse() {
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#getRequest()
	 */
	@Override
	public HttpServletRequest getRequest() {
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#getServletId()
	 */
	@Override
	public String getServletId() {
		return servletId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#setServletId(java.lang.String)
	 */
	@Override
	public void setServletId(String servletId) {
		this.servletId = servletId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#getBizId()
	 */
	@Override
	public String getBizId() {
		return bizId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#setBizId(java.lang.String)
	 */
	@Override
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#getOriginalBizId()
	 */
	@Override
	public String getOriginalBizId() {
		return originalBizId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#setOriginalBizId(java.lang.String)
	 */
	@Override
	public void setOriginalBizId(String originalBizId) {
		this.originalBizId = originalBizId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#getRequestByteBuffer()
	 */
	@Override
	public ByteBuffer getRequestByteBuffer() {
		return requestByteBuffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#setRequestByteBuffer(java.nio.ByteBuffer)
	 */
	@Override
	public void setRequestByteBuffer(ByteBuffer requestByteBuffer) {
		this.requestByteBuffer = requestByteBuffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#getResponseByteBuffer()
	 */
	@Override
	public ByteBuffer getResponseByteBuffer() {
		return responseByteBuffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#setResponseByteBuffer(java.nio.ByteBuffer)
	 */
	@Override
	public void setResponseByteBuffer(ByteBuffer responseByteBuffer) {
		this.responseByteBuffer = responseByteBuffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#getRequestTextString()
	 */
	@Override
	public String getRequestTextString() {
		return requestTextString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#setRequestTextString(java.lang.String)
	 */
	@Override
	public void setRequestTextString(String requestTextString) {
		this.requestTextString = requestTextString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#getResponseTextString()
	 */
	@Override
	public String getResponseTextString() {
		return responseTextString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IServletContext#setResponseTextString(java.lang.String)
	 */
	@Override
	public void setResponseTextString(String responseTextString) {
		this.responseTextString = responseTextString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IOkeyDokeyContext#getAsyncContext()
	 */
	@Override
	public AsyncContext getAsyncContext() {
		return asyncContext;
	}

	/**
	 * Obtain bizId and message extension by request servlet path. Expression of servlet path is {bizId}.{MessageExtnsion}
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	private void obtainBizIdMessageExtension(HttpServletResponse response) throws IOException {
		String pathInfo = this.request.getServletPath();
		final int delimIndex = pathInfo.indexOf(".");
		if (delimIndex != -1) {
			this.bizId = pathInfo.substring(1, delimIndex);
			this.messageExtension = pathInfo.substring(delimIndex + 1);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Servlet mapping Error!! can't obtain bizId and messageExtnsion");
		}
	}

}
