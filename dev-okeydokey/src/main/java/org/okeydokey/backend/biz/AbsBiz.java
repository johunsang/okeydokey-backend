package org.okeydokey.backend.biz;

import javax.servlet.http.HttpServletResponse;

import org.okeydokey.backend.consts.AppConstants;
import org.okeydokey.backend.context.ApplicationContext;
import org.okeydokey.backend.context.IApplicationContext;
import org.okeydokey.backend.context.IOkeyDokeyContext;
import org.okeydokey.backend.service.Service;
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
