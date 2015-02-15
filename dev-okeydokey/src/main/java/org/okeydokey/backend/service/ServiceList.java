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

import java.util.ArrayList;

import org.okeydokey.backend.context.IOkeyDokeyContext;

/**
 * <pre>
 * Class that contains list of service
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.01.01
 */
@SuppressWarnings("serial")
public class ServiceList<E> extends ArrayList<Service> {

	/**
	 * Get Service from list of service. If service is not in list, instantiate class and return instance
	 * 
	 * @param context
	 *            IOkeyDokeyContext
	 * @return Service
	 */
	public Service getService(IOkeyDokeyContext context) {

		String bizId = context.getBizId();
		String className = context.getClassName();

		try {
			for (int i = 0; i < size(); i++) {
				Service service = (Service) get(i);
				if (service.getBizId().equals(bizId)) {
					return service;
				}
			}

			Service service = new Service(bizId, className);
			add(service);
			return service;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
