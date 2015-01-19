package org.okeydokey.backend.context;

import java.util.List;
import java.util.Map;

import org.okeydokey.backend.service.Service;
import org.okeydokey.backend.service.ServiceList;

/**
 * <pre>
 * Interface that contains context of web applicaiton
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public interface IApplicationContext {

	/**
	 * Return name of application context
	 * 
	 * @return name of application context
	 */
	public String getContextName();

	/**
	 * Set name of application context
	 * 
	 * @param contextName
	 *            name of application context
	 */
	public void setContextName(String contextName);

	/**
	 * Return root path of application context
	 * 
	 * @return root path of application context
	 */
	public String getContextRootPath();

	/**
	 * Set root path of application context
	 * 
	 * @param contextRootPath
	 *            root path of application context
	 */
	public void setContextRootPath(String contextRootPath);

	/**
	 * Get list of service
	 * 
	 * @return list of service
	 */
	public ServiceList<Service> getServiceList();

	/**
	 * Set list of service
	 * 
	 * @param serviceList
	 *            list of service
	 */
	public void setServiceList(ServiceList<Service> serviceList);

	/**
	 * Return map of service
	 * 
	 * @return map of service
	 */
	public Map<String, String> getServiceMap();

	/**
	 * Set map of service
	 * 
	 * @param serviceMap
	 *            map of service
	 */
	public void setServiceMap(Map<String, String> serviceMap);

	/**
	 * Return map of before service
	 * 
	 * @return map of service
	 */
	public Map<Integer, String> getBeforeServiceMap();

	/**
	 * Set map of before service
	 * 
	 * @param beforeServiceMap
	 *            map of before service
	 */
	public void setBeforeServiceMap(Map<Integer, String> beforeServiceMap);

	/**
	 * Return map of after service
	 * 
	 * @return map of after service
	 */
	public Map<Integer, String> getAfterServiceMap();

	/**
	 * Set map of after service
	 * 
	 * @param serviceMap
	 *            map of after service
	 */
	public void setAfterServiceMap(Map<Integer, String> afterServiceMap);

	/**
	 * Return map of application configuration
	 * 
	 * @return map of application configuration
	 */
	public Map<String, Object> getConfigMap();

	/**
	 * Set map of application configuration
	 * 
	 * @param configMap
	 *            map of application configuration
	 */
	public void setConfigMap(Map<String, Object> configMap);

	/**
	 * Get list of fully class name
	 * 
	 * @return list of fully class name
	 */
	public List<String> getBizClassName();

	/**
	 * Set list of fully class name
	 * 
	 * @param bizClassName
	 *            list of fully class name
	 */
	public void setBizClassName(List<String> bizClassName);

}
