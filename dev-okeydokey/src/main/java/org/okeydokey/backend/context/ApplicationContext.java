package org.okeydokey.backend.context;

import java.util.List;
import java.util.Map;

import org.okeydokey.backend.service.Service;
import org.okeydokey.backend.service.ServiceList;

/**
 * <pre>
 * Application context class. 
 * This will be initialized and instantiated when server start.
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public class ApplicationContext implements IApplicationContext {

	/**
	 * context root path of web application
	 */
	private String contextRootPath;

	/**
	 * context name of web application
	 */
	private String contextName;

	/**
	 * list of fully class name
	 */
	private List<String> bizClassName;

	/**
	 * list of service
	 */
	private ServiceList<Service> ServiceList;

	/**
	 * map of service
	 */
	private Map<String, String> serviceMap;

	/**
	 * map of after service
	 */
	private Map<Integer, String> afterServiceMap;

	/**
	 * map of before service
	 */
	private Map<Integer, String> beforeServiceMap;

	/**
	 * map of application configuration
	 */
	private Map<String, Object> configMap;

	/**
	 * single ApplicationContext instance
	 */
	private volatile static ApplicationContext instance = null;

	/**
	 * Return single instance of ApplicationContext
	 * 
	 * @return single instance of ApplicationContext
	 */
	public synchronized static ApplicationContext getInstance() {
		if (instance == null) {
			synchronized (ApplicationContext.class) {
				if (instance == null) {
					instance = new ApplicationContext();
				}
			}
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IApplicationContext#getContextName()
	 */
	@Override
	public String getContextName() {
		return contextName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IApplicationContext#setContextName(java.lang.String)
	 */
	@Override
	public void setContextName(String contextName) {
		this.contextName = contextName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IApplicationContext#getContextRootPath()
	 */
	@Override
	public String getContextRootPath() {
		return contextRootPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IApplicationContext#setContextRootPath(java.lang.String)
	 */
	@Override
	public void setContextRootPath(String contextRootPath) {
		this.contextRootPath = contextRootPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IApplicationContext#getServiceList()
	 */
	@Override
	public ServiceList<Service> getServiceList() {
		return ServiceList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IApplicationContext#setServiceList(org.okeydokey.backend.service.ServiceList)
	 */
	@Override
	public void setServiceList(ServiceList<Service> serviceList) {
		ServiceList = serviceList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IApplicationContext#getServiceMap()
	 */
	@Override
	public Map<String, String> getServiceMap() {
		return serviceMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IApplicationContext#setServiceMap(java.util.Map)
	 */
	@Override
	public void setServiceMap(Map<String, String> serviceMap) {
		this.serviceMap = serviceMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IApplicationContext#getConfigMap()
	 */
	@Override
	public Map<String, Object> getConfigMap() {
		return configMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IApplicationContext#setConfigMap(java.util.Map)
	 */
	@Override
	public void setConfigMap(Map<String, Object> configMap) {
		this.configMap = configMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IApplicationContext#getBizClassName()
	 */
	@Override
	public List<String> getBizClassName() {
		return bizClassName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.okeydokey.backend.context.IApplicationContext#setBizClassName(java.util.List)
	 */
	@Override
	public void setBizClassName(List<String> bizClassName) {
		this.bizClassName = bizClassName;
	}

	/* (non-Javadoc)
	 * @see org.okeydokey.backend.context.IApplicationContext#getBeforeServiceMap()
	 */
	@Override
	public Map<Integer, String> getBeforeServiceMap() {
		return beforeServiceMap;
	}

	/* (non-Javadoc)
	 * @see org.okeydokey.backend.context.IApplicationContext#setBeforeServiceMap(java.util.Map)
	 */
	@Override
	public void setBeforeServiceMap(Map<Integer, String> beforeServiceMap) {
		this.beforeServiceMap = beforeServiceMap;
	}

	/* (non-Javadoc)
	 * @see org.okeydokey.backend.context.IApplicationContext#getAfterServiceMap()
	 */
	@Override
	public Map<Integer, String> getAfterServiceMap() {
		return afterServiceMap;
	}

	/* (non-Javadoc)
	 * @see org.okeydokey.backend.context.IApplicationContext#setAfterServiceMap(java.util.Map)
	 */
	@Override
	public void setAfterServiceMap(Map<Integer, String> afterServiceMap) {
		this.afterServiceMap = afterServiceMap;
	}

}
