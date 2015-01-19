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
