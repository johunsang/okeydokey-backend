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
package org.okeydokey.backend.service;

import java.io.IOException;

import org.okeydokey.backend.biz.AbsBiz;
import org.okeydokey.backend.context.IOkeyDokeyContext;
import org.okeydokey.backend.exception.BizException;
import org.okeydokey.backend.utils.BaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Service that execute biz class
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public class Service {

	/**
	 * bizId
	 */
	private String bizId;

	/**
	 * fully class name
	 */
	private String className;

	/**
	 * bizerror.log
	 */
	private static Logger ERROR_LOG = LoggerFactory.getLogger("bizerror");

	/**
	 * biz.log
	 */
	private static Logger BIZ_LOG = LoggerFactory.getLogger("biz");

	/**
	 * constructor
	 * 
	 * @param bizId
	 * @param className
	 * @throws IOException
	 */
	public Service(String bizId, String className) throws IOException {
		setBizId(bizId);
		setClassName(className);
	}

	/**
	 * Main transaction method that execute biz class by bizId
	 * 
	 * @param context
	 *            IOkeyDokeyContext
	 * @throws Exception
	 */
	public void execute(IOkeyDokeyContext context) throws Exception {
		context.setBizExceptionThrown(false);
		context.setBizException(null);
		Object object = null;
		try {
			object = Class.forName(className).newInstance();
			if (object instanceof AbsBiz) {
				// execute !!
				((AbsBiz) object).execute(context);
			} else {
				errorLog("Specified class is not Biz Class : " + object.getClass().getName());
				throw new RuntimeException("Specified class is not Biz Class : " + object.getClass().getName());
			}
		} catch (BizException be) {
			context.setBizExceptionThrown(true);
			context.setBizException(be);
			errorLog("!BizException!" + context.getServletId() + "!\n" + BaseUtil.toExceptionString(be));
			throw be;
		} catch (InstantiationException e) {
			context.setBizExceptionThrown(true);
			context.setBizException(e);
			errorLog("!InstantiationException!" + context.getServletId() + "!\n" + BaseUtil.toExceptionString(e));
			throw new BizException(e);
		} catch (IllegalAccessException e) {
			context.setBizExceptionThrown(true);
			context.setBizException(e);
			errorLog("!IllegalAccessException!" + context.getServletId() + "!\n" + BaseUtil.toExceptionString(e));
			throw new BizException(e);
		} catch (ClassNotFoundException e) {
			context.setBizExceptionThrown(true);
			context.setBizException(e);
			errorLog("!ClassNotFoundException!" + context.getServletId() + "!\n" + BaseUtil.toExceptionString(e));
			throw new BizException(e);
		} catch (Exception e) {
			context.setBizExceptionThrown(true);
			context.setBizException(e);
			errorLog("!Exception![" + context.getServletId() + "]\n" + BaseUtil.toExceptionString(e));
			throw new BizException(e);
		} finally {
			try {
				// executeFinally
				((AbsBiz) object).executeFinally(context);
			} catch (Exception e) {
				throw new BizException(e);
			}
		}

	}

	private void errorLog(String message) {
		ERROR_LOG.error(message);
		BIZ_LOG.error(message);
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
