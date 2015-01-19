package org.okeydokey.backend.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ReadListener;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.okeydokey.backend.consts.AppConstants;
import org.okeydokey.backend.consts.ExtensionConstants;
import org.okeydokey.backend.consts.LogConstants;
import org.okeydokey.backend.context.ApplicationContext;
import org.okeydokey.backend.context.IApplicationContext;
import org.okeydokey.backend.context.IOkeyDokeyContext;
import org.okeydokey.backend.context.OkeyDokeyContext;
import org.okeydokey.backend.servlet.listener.BinaryReadListener;
import org.okeydokey.backend.servlet.listener.TextReadListener;
import org.okeydokey.backend.utils.BaseUtil;
import org.okeydokey.backend.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * <h1>OkeyDokey backend default HttpServlet</h1>
 * A servlet that extends javax.servlet.http.HttpServlet.
 * 
 * If server URI is [http://host:port/{context-path}/{servlet-path}], pattern of servlet-path should consists of {bizId}.{message extension}.
 * 
 * -- e.g http:localhost:8080/okeydokey/helloworld.txt
 * 
 * context-path  = /okeydokey
 * servlet-path = /helloworld.txt
 * bizId   = helloworld
 * message extension  = txt
 * 
 * OkeyDokey framework will find out class by bizId, and execute it.
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
@WebServlet(urlPatterns = { "*.txt", "*.bnry" }, displayName = "OkeyDokeyBackendServlet", name = "OkeyDokeyBackendServlet", description = "Default Servlet", loadOnStartup = 1, asyncSupported = true)
public class OkeyDokeyBackendServlet extends HttpServlet {

	/**
	 * serial no
	 */
	private static final long serialVersionUID = 1463983679722179076L;

	/**
	 * HTTP request access log
	 */
	private static Logger ACCESS_LOG = LoggerFactory.getLogger("access");

	/**
	 * default minimum size of input stream(byte)
	 */
	private int minSize = 0;

	/**
	 * default maximum size of input stream(byte)
	 */
	private int maxSize = 1048580; // 1MB

	@Override
	/** Initialize ..  */
	public void init(ServletConfig config) throws ServletException {
		minSize = BaseUtil.getInputMinSize();
		maxSize = BaseUtil.getInputLimitSize();
	}

	@Override
	/** Destroy..  */
	public void destroy() {
	}

	/**
	 * Override doGet method of HttpServlet
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return;
	}

	/**
	 * Override doPost method of HttpServlet
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Write HttpRequest log when debugging mode
			if (ACCESS_LOG.isDebugEnabled()) {
				accessLog(request);
			}
			// Check size of content length(Header)
			int contentLength = request.getContentLength();

			if (contentLength == 0 || isSmallerThanMin(contentLength)) {
				// 411
				response.sendError(HttpServletResponse.SC_LENGTH_REQUIRED, "content Length is the smaller than " + minSize + ", input " + contentLength);
				return;
			}
			if (isBiggerThanMax(contentLength)) {
				// 413
				response.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, "content Length is the bigger than " + maxSize + ", input " + contentLength);
				return;
			}

			// Set the mime type of the response
			String content_type = request.getContentType();
			if (StringUtil.isEmpty(content_type)) {
				response.setContentType("text/plain");
			} else {
				response.setContentType(content_type);
			}

			// Analyze HttpRequest and initialize ServletContext
			// OkeyDokeyContext is instantiated when each HTTP request event
			final IOkeyDokeyContext context = new OkeyDokeyContext(request, response);

			// Obtain bizId from ServletContext
			String bizId = context.getBizId();

			// Get fully qualified className from ApplicationContext by biz id
			IApplicationContext appContext = ApplicationContext.getInstance();
			String fullyClassName = appContext.getServiceMap().get(bizId);
			// Set context name
			appContext.setContextName(request.getServerName());

			// Throw servlet exception when bizId is empty
			if (StringUtil.isEmpty(fullyClassName)) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "can't find class by bizId : " + bizId + ", check out annotated class(@Biz),  or class is deployed properly");
			}

			// Set id of biz class key in BizMap
			context.getBizMap().put(AppConstants.BIZID, bizId);

			// Set fully qualified className in ServletContext
			context.setClassName(fullyClassName);

			// Start async processing
			context.getRequest().getInputStream().setReadListener(getListenerByMessageExtension(context));

		} catch (Exception e) {
			throw new ServletException(e);
			// response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());

		}
	}

	/**
	 * <pre>
	 * Write HTTP request log(access.log)
	 * This log can overload
	 * So check out priority value in log4j.xml file
	 * This method is useful when analyze httpReqeust event
	 * </pre>
	 * 
	 * @param request
	 *            HttpServletRequest
	 */
	private void accessLog(HttpServletRequest request) {
		// session id
		String sSessionID = request.getSession().getId();
		if (StringUtil.isEmpty(sSessionID)) {
			sSessionID = "NULL";
		}
		StringBuffer sbAcess = new StringBuffer();
		sbAcess.append(LogConstants.LOG_BANNER_NUM);
		sbAcess.append("\n[" + sSessionID + "] " + "request uri : " + request.getRequestURI());
		sbAcess.append("\n[" + sSessionID + "] " + "content length : " + request.getContentLength());
		sbAcess.append("\n[" + sSessionID + "] " + "local addr : " + request.getLocalAddr());
		sbAcess.append("\n[" + sSessionID + "] " + "remote addr : " + request.getRemoteAddr());
		sbAcess.append("\n[" + sSessionID + "] " + "servlet path : " + request.getServletPath());
		sbAcess.append("\n[" + sSessionID + "] " + "character encoding : " + request.getCharacterEncoding());
		sbAcess.append("\n[" + sSessionID + "] " + "method : " + request.getMethod());
		sbAcess.append("\n[" + sSessionID + "] " + "content type : " + request.getContentType());
		sbAcess.append("\n[" + sSessionID + "] " + "context path : " + request.getContextPath());

		// header
		Enumeration<?> eHead = request.getHeaderNames();
		while (eHead.hasMoreElements()) {
			String sHead = (String) eHead.nextElement();
			sbAcess.append("\n[" + sSessionID + "] " + "headers : " + sHead + " " + request.getHeader(sHead));
		}

		// attribute
		Enumeration<?> eAttr = request.getAttributeNames();
		while (eAttr.hasMoreElements()) {
			String sAttr = (String) eAttr.nextElement();
			sbAcess.append("\n[" + sSessionID + "] " + "attributes : " + sAttr + " " + request.getAttribute(sAttr));
		}

		// parameter
		Enumeration<?> ePara = request.getParameterNames();
		while (ePara.hasMoreElements()) {
			String sPara = (String) ePara.nextElement();
			sbAcess.append("\n[" + sSessionID + "] " + "parameters : " + sPara + " " + request.getParameter(sPara));
		}
		sbAcess.append("\n" + LogConstants.LOG_BANNER_NUM + "\n");
		ACCESS_LOG.debug(sbAcess.toString());
	}

	/**
	 * @param contentLength
	 * @return boolean
	 */
	private boolean isSmallerThanMin(int contentLength) {
		if (contentLength <= minSize) {
			return true;
		}
		return false;
	}

	/**
	 * @param contentLength
	 * @return boolean
	 */
	private boolean isBiggerThanMax(int contentLength) {
		if (contentLength > maxSize) {
			return true;
		}
		return false;
	}

	/**
	 * Get ReadListener by messageExtension
	 * 
	 * @throws IOException
	 * 
	 */
	private ReadListener getListenerByMessageExtension(IOkeyDokeyContext context) throws IOException {
		String messageExtension = context.getMessageExtension();

		if (StringUtil.equals(ExtensionConstants.TEXT_EXTENSION, messageExtension)) { // txt
			return new TextReadListener(context);
		} else if (StringUtil.equals(ExtensionConstants.BINARAY_EXTENSION, messageExtension)) { // bnry
			return new BinaryReadListener(context);
		}
		return null;
	}
}
