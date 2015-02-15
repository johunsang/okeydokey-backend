package org.okeydokey.backend.biz;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.okeydokey.backend.consts.AppConstants;
import org.okeydokey.backend.context.ApplicationContext;
import org.okeydokey.backend.context.IApplicationContext;
import org.okeydokey.backend.context.IOkeyDokeyContext;
import org.okeydokey.backend.service.Service;
import org.okeydokey.backend.utils.PropertyLoader;
import org.okeydokey.backend.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Abstract biz class.
 * All of biz class should extend this class.
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public abstract class AbsBiz implements IBiz {

	/**
	 * biz.log
	 */
	public static Logger BIZ_LOG;

	/**
	 * default constructor
	 */
	public AbsBiz() {
		BIZ_LOG = LoggerFactory.getLogger("biz");
	}

	/**
	 * Get String value of config properties
	 * 
	 * @param key
	 * @return String value of config properties
	 */
	public static String getConfig(String key) {
		return PropertyLoader.getPropertyInstance().getConfig(key);
	}

	/**
	 * Get int value of config properties
	 * 
	 * @param key
	 * @return int value of config properties
	 */
	public static int getIntConfig(String key) {
		return PropertyLoader.getPropertyInstance().getIntConfig(key);
	}

	/**
	 * Get String value of code properties
	 * 
	 * @param code
	 * @return String value of code properties
	 */
	public static String getCode(String code) {
		return PropertyLoader.getPropertyInstance().getCode(code);
	}

	/**
	 * Get String value of message properties
	 * 
	 * @param id
	 * @return String value of message properties
	 */
	public static String getMessage(String id) {
		return PropertyLoader.getPropertyInstance().getMessage(id);
	}

	/**
	 * Get String value of okeydokey properties
	 * 
	 * @param id
	 * @return String value of okeydokey properties
	 */
	public static String getOkeydokey(String id) {
		return PropertyLoader.getPropertyInstance().getOkeydokey(id);
	}

	/**
	 * Get int value of okeydokey properties
	 * 
	 * @param id
	 * @return int value of okeydokey properties
	 */
	public static int getIntOkeydokey(String id) {
		return PropertyLoader.getPropertyInstance().getIntOkeydokey(id);
	}

	/**
	 * Return String of exception stack
	 * 
	 * @param th
	 * @return String of exception stack
	 */
	public static String toExceptionString(Throwable th) {
		if (th == null)
			return "";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		th.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * Call another biz class by bizId
	 * 
	 * @param context
	 *            context of servlet
	 * @param bizId
	 *            bizId
	 * @throws Exception
	 */
	protected void callBiz(IOkeyDokeyContext context, String bizId) throws Exception {

		// Get single instance of ApplicationContext
		IApplicationContext appContext = ApplicationContext.getInstance();
		// Get fully qualified class name from application context
		String fullyClassName = appContext.getServiceMap().get(bizId);

		if (StringUtil.isEmpty(fullyClassName)) {
			throw new Exception("can't find class by bizId : " + bizId + ", check out annotated class(@Biz) or class is deployed properly");
		}

		// Set class name to servlet context
		context.setClassName(fullyClassName);

		// Set original bizId
		if (StringUtil.isEmpty(context.getOriginalBizId())) {
			context.setOriginalBizId(context.getBizId());
		}

		// Set bizId
		context.setBizId(bizId);
		context.getBizMap().put(AppConstants.BIZID, bizId);

		// Execute service class
		Service service = appContext.getServiceList().getService(context);
		service.execute(context);
	}
}
