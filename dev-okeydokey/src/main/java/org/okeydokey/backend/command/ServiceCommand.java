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
package org.okeydokey.backend.command;

import java.util.Iterator;
import java.util.Map;

import org.okeydokey.backend.context.ApplicationContext;
import org.okeydokey.backend.context.IApplicationContext;
import org.okeydokey.backend.context.IOkeyDokeyContext;

/**
 * <pre>
 * Implementation of {@link org.okeydokey.backend.context.IServletContext}
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public class ServiceCommand implements ICommand {

	/**
	 * single ServiceCommand instance
	 */
	private volatile static ServiceCommand instance;

	/**
	 * Return single ServiceCommand instance
	 * 
	 * @return ServiceCommand instance
	 */
	public static ServiceCommand getInstance() {
		if (instance == null) {
			synchronized (ServiceCommand.class) {
				if (instance == null) {
					instance = new ServiceCommand();
				}
			}
		}
		return instance;
	}

	/**
	 * prevent instantiation
	 */
	private ServiceCommand() {

	}

	@Override
	public void execute(IOkeyDokeyContext context) throws Exception {
		String originClassName = context.getClassName();
		String originBizId = context.getBizId();

		IApplicationContext appContext = ApplicationContext.getInstance();

		// Execute before service
		Map<Integer, String> bmap = appContext.getBeforeServiceMap();
		if (bmap.size() > 0) {
			excuteServiceMap(context, bmap);
		}

		// Execute service
		backContextToOrigin(context, originBizId, originClassName);
		appContext.getServiceList().getService(context).execute(context);

		// Execute after service
		Map<Integer, String> amap = appContext.getAfterServiceMap();
		if (amap.size() > 0) {
			excuteServiceMap(context, amap);
		}
		// back
		backContextToOrigin(context, originBizId, originClassName);
	}

	private void excuteServiceMap(IOkeyDokeyContext context, Map<Integer, String> map) throws Exception {
		Iterator<Integer> itrr = map.keySet().iterator();
		while (itrr.hasNext()) {
			String clssname = map.get(itrr.next());
			excuteService(context, clssname, clssname);
		}

	}

	private void backContextToOrigin(IOkeyDokeyContext context, String originBizId, String originClassName) {
		context.setClassName(originClassName);
		context.setBizId(originBizId);
	}

	private void excuteService(IOkeyDokeyContext context, String bizId, String ClassName) throws Exception {
		IApplicationContext appContext = ApplicationContext.getInstance();

		context.setClassName(ClassName);
		context.setBizId(bizId);

		appContext.getServiceList().getService(context).execute(context);
	}

}
