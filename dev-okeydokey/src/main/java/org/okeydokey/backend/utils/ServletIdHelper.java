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
package org.okeydokey.backend.utils;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.okeydokey.backend.utils.BaseUtil;

/**
 * <pre>
 * Make unique servlet id
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public class ServletIdHelper {

	/**
	 * delimiter
	 */
	private static String DELIMITER = "_";

	/**
	 * single ServletIdHelper instance
	 */
	private volatile static ServletIdHelper instance = null;

	/**
	 * Return single instance of ServletIdHelper
	 * 
	 * @return single ServletIdHelper
	 */
	public static ServletIdHelper getInstance() {
		if (instance == null) {
			synchronized (ServletIdHelper.class) {
				if (instance == null) {
					instance = new ServletIdHelper(99999, 99);
				}
			}
		}
		return instance;
	}

	/**
	 * sequence max
	 */
	private int servletSequenceMax = 99999;

	/**
	 * sequence value
	 */
	private int servletSequence = 0;

	public ServletIdHelper(int servletSequenceMax, int asyncSequenceMax) {
		this.servletSequenceMax = servletSequenceMax;
	}

	public int getServletSequenceMax() {
		return this.servletSequenceMax;
	}

	public void setGlobalSequenceMax(int servletSequenceMax) {
		this.servletSequenceMax = servletSequenceMax;
	}

	synchronized private final int getServletSequence() {
		if (++servletSequence > servletSequenceMax) {
			servletSequence = 0;
		}
		return servletSequence;
	}

	/**
	 * Make unique id
	 * 
	 * hostname_sessionId_bizid_yyyyMMdd_HHmmssSSS_sequence
	 * 
	 * @param id
	 *            biz id
	 * @param sessionId
	 *            httpRequest session id
	 * @return unique string id
	 * @throws UnknownHostException
	 */
	public String newServletId(String id, String sessionId) throws UnknownHostException {
		String hostname = BaseUtil.getHostName();
		if (hostname.length() > 13) {
			hostname = hostname.substring(0, 13);
		}
		int seq = getServletSequence();
		String yyyyMMddHHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuilder buff = new StringBuilder(50);
		buff.append(hostname);
		buff.append(DELIMITER);
		buff.append(sessionId);
		buff.append(DELIMITER);
		buff.append(id);
		buff.append(DELIMITER);
		buff.append(yyyyMMddHHmmssSSS.substring(0, 8));
		buff.append(DELIMITER);
		buff.append(yyyyMMddHHmmssSSS.substring(8));
		buff.append(DELIMITER);
		buff.append(lpadByte(String.valueOf(seq), '0', 5));
		return buff.toString();
	}

	private String lpadByte(String src, char padChar, int len) {
		byte[] bb = src == null ? new byte[0] : src.getBytes();

		if (bb.length >= len) {
			return new String(bb, 0, len);
		}

		byte[] pad = new byte[len];
		int padLen = len - bb.length;
		System.arraycopy(bb, 0, pad, padLen, bb.length);

		for (int i = 0; i < padLen; i++) {
			pad[i] = (byte) padChar;
		}
		return new String(pad);
	}

}
