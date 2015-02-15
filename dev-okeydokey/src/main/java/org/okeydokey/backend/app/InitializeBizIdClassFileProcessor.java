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
package org.okeydokey.backend.app;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

import org.okeydokey.backend.context.ApplicationContext;
import org.okeydokey.backend.context.IApplicationContext;
import org.okeydokey.backend.service.Service;
import org.okeydokey.backend.service.ServiceList;
import org.okeydokey.backend.utils.BaseUtil;
import org.okeydokey.backend.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Get bizId from biz properties and put bizId and full class name in ApplicationContext instance
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public class InitializeBizIdClassFileProcessor implements IInitializeProcess {
	/** Default OkeyDokey framework logger */
	private static Logger APP_LOG = LoggerFactory.getLogger("application");

	@Override
	public void process(ServletContext ctx) {

		// Set bizId Property
		write("3. Initialzing informaiton of bizId and classname by annotation");

		processAnnotation(ctx);

	}

	/**
	 * Load mapping information of bizId and class by annotation
	 * 
	 * @param ctx
	 *            ServletContext
	 */
	private void processAnnotation(ServletContext ctx) {
		try {
			String bizClassStorePath = getBizclassStorePath();

			write("---> class path: " + bizClassStorePath);

			IApplicationContext appcontext = ApplicationContext.getInstance();
			appcontext.setServiceList(new ServiceList<Service>());

			// Get file path
			File webDir = new File(findWebInfClassesPath(ctx));
			Path webDirPath = Paths.get(webDir.toURI());

			// Find annotated class
			AnnotationBizClassFileVisitor visitor = new AnnotationBizClassFileVisitor();
			Files.walkFileTree(webDirPath, visitor);

			// Set before service
			write("---> before biz");
			
			Map<Integer, String> beforeServiceMap = visitor.getBeforeBizClassIndex();
			Iterator<Integer> itrrb = beforeServiceMap.keySet().iterator();
			while (itrrb.hasNext()) {
				int key = itrrb.next();
				String className = (String) beforeServiceMap.get(key);
				write("--->---> seq : " + key + ", classname : " + className);
			}
			appcontext.setBeforeServiceMap(beforeServiceMap);

			// Set after service
			write("---> after biz");
			
			Map<Integer, String> afterServiceMap = visitor.getAfterBizClassIndex();
			Iterator<Integer> itrra = afterServiceMap.keySet().iterator();
			while (itrra.hasNext()) {
				int key = itrra.next();
				String className = (String) afterServiceMap.get(key);
				write("--->---> seq : " + key + ", classname : " + className);
			}
			appcontext.setAfterServiceMap(afterServiceMap);

			// Set service
			write("---> biz");
			Map<String, String> serviceMap = visitor.getBizClassIndex();
			Iterator<String> itrr = serviceMap.keySet().iterator();
			while (itrr.hasNext()) {
				String key = (String) itrr.next();
				String value = (String) serviceMap.get(key);
				write("--->---> bizId : " + key + ", classname : " + value);
			}
			appcontext.setServiceMap(serviceMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String findWebInfClassesPath(ServletContext servletContext) {
		String path = servletContext.getRealPath(getBizclassStorePath());
		if (path == null)
			return null;
		File fp = new File(path);
		if (fp.exists() == false)
			return null;
		return path;
	}

	private String getBizclassStorePath() {
		String value = BaseUtil.getBizClassStorePath();
		if (StringUtil.isEmpty(value)) {
			throw new RuntimeException("Store path of biz class is empty");
		}
		return value;
	}

	private static void write(String logs) {
		APP_LOG.info(logs);
		System.out.println(logs);
	}
}
