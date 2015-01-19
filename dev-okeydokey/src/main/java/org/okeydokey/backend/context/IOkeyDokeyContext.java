package org.okeydokey.backend.context;

import java.nio.ByteBuffer;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.okeydokey.backend.data.BizMap;

/**
 * <pre>
 * Interface contains context of HTTP servlet
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public interface IOkeyDokeyContext {

	/**
	 * Return BizMap<String, Object> object in the IServletContext instance
	 * 
	 * @See {@link org.okeydokey.backend.data.BizMap}
	 * @return BizMap<String, Object> object
	 */
	public BizMap<String, Object> getBizMap();

	/**
	 * Set BizMap<String, Object> object
	 * 
	 * @See {@link org.okeydokey.backend.data.BizMap}
	 * @param BizMap
	 *            BizMap<String, Object> object
	 * 
	 */
	public void setBizMap(BizMap<String, Object> bizMap);

	/**
	 * Indicates whether BizException {@link org.okeydokey.backend.exception.BizException} thrown when biz class is executed
	 * 
	 * @return whether BizException thrown
	 */
	public boolean isBizExceptionThrown();

	/**
	 * Set whether BizException {@link org.okeydokey.backend.exception.BizException} thrown
	 * 
	 * @param bizExceptionThrown
	 *            whether BizException thrown
	 */
	public void setBizExceptionThrown(boolean bizExceptionThrown);

	/**
	 * Return BizException{@link org.okeydokey.backend.exception.BizException} Throwable
	 * 
	 * @return BizException
	 */
	public Throwable getBizException();

	/**
	 * Set BizException Throwable
	 * 
	 * @param bizException
	 *            {@link org.okeydokey.backend.exception.BizException} BizException
	 */
	public void setBizException(Throwable bizException);

	/**
	 * Return message extension
	 * 
	 * @return message extension
	 */
	public String getMessageExtension();

	/**
	 * Set message extension
	 * 
	 * @param messageExtension
	 *            message extension
	 */
	public void setMessageExtension(String messageExtension);

	/**
	 * Return fully qualified name of class
	 * 
	 * @return fully qualified name of class
	 */
	public String getClassName();

	/**
	 * Set fully qualified name of class
	 * 
	 * @param className
	 *            fully qualified name of class
	 */
	public void setClassName(String className);

	/**
	 * Return unique servlet id
	 * 
	 * @return unique servlet id
	 */
	public String getServletId();

	/**
	 * Set unique servlet id
	 * 
	 * @param servletId
	 *            unique servlet id
	 */
	public void setServletId(String servletId);

	/**
	 * Return bizId from HTTP request sevlet path
	 * 
	 * @return bizId from HTTP request sevlet path
	 */
	public String getBizId();

	/**
	 * Set bizId from HTTP request sevlet path
	 * 
	 * @param bizId
	 *            bizId from HTTP request sevlet path
	 */
	public void setBizId(String bizId);

	/**
	 * Get original bizId from HTTP request sevlet path If biz class is executed by callService method, bizId is changed
	 * 
	 * @return original bizId from HTTP request sevlet path
	 */
	public String getOriginalBizId();

	/**
	 * Set original bizId from HTTP request sevlet path
	 * 
	 * @param originalBizId
	 *            original bizId from HTTP request sevlet path
	 */
	public void setOriginalBizId(String originalBizId);

	/**
	 * Return HttpServletResponse
	 * 
	 * @return HttpServletResponse
	 */
	public HttpServletResponse getResponse();

	/**
	 * Return HttpServletRequest
	 * 
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest();


	/**
	 * Return request message of ByteBuffer type
	 * 
	 * @return request message of ByteBuffer type
	 */
	public ByteBuffer getRequestByteBuffer();

	/**
	 * Set request message of ByteBuffer type
	 * 
	 * @param requestByteBuffer
	 *            request message of ByteBuffer type
	 */
	public void setRequestByteBuffer(ByteBuffer requestByteBuffer);

	/**
	 * Return response message of ByteBuffer type
	 * 
	 * @return response message of ByteBuffer type
	 */
	public ByteBuffer getResponseByteBuffer();

	/**
	 * Set response message of ByteBuffer type
	 * 
	 * @param responseByteBuffer
	 *            response message of ByteBuffer type
	 */
	public void setResponseByteBuffer(ByteBuffer responseByteBuffer);

	/**
	 * Return request message of text type
	 * 
	 * @return request message of text type
	 */
	public String getRequestTextString();

	/**
	 * Set request message of text type
	 * 
	 * @param requestTextString
	 *            request message of text type
	 */
	public void setRequestTextString(String requestTextString);

	/**
	 * Return response message of text type
	 * 
	 * @return response message of text type
	 */
	public String getResponseTextString();

	/**
	 * Set response message of text type
	 * 
	 * @param responseTextString
	 *            response message of text type
	 */
	public void setResponseTextString(String responseTextString);

	/**
	 * Get context of async
	 * 
	 * @return AsyncContext
	 */
	public AsyncContext getAsyncContext();


}
