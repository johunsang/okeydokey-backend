package main.java.sample.json;

import java.util.HashMap;
import java.util.Map;

import org.okeydokey.backend.biz.AbsBiz;
import org.okeydokey.backend.biz.Biz;
import org.okeydokey.backend.context.IOkeyDokeyContext;
import org.okeydokey.backend.exception.BizException;

import com.google.gson.Gson;

@Biz(bizId = "HelloWorldJson")
public class HelloWorldJson extends AbsBiz {

	@Override
	public void execute(IOkeyDokeyContext context) throws BizException {
		try {
			// Get request text message
			String reqeustTextMessage = context.getRequestTextString();

			// Write request text message to biz log
			BIZ_LOG.info(reqeustTextMessage);

			// Use Gson(https://code.google.com/p/google-gson/) library to convert request message.. You can use the other json parsing library like jackson, json-lib, flexjson
			Map<String, Object> jsonmap = new Gson().fromJson(reqeustTextMessage, Map.class);
			// request mapping
			String value1 = (String) jsonmap.get("message1");
			String value2 = (String) jsonmap.get("message2");
			String value3 = (String) jsonmap.get("message3");

			BIZ_LOG.info("value1 >> " + value1);
			BIZ_LOG.info("value2 >> " + value2);
			BIZ_LOG.info("value3 >> " + value3);

			// Set value
			context.getBizMap().put("callerValue", value1 + value2 + value3);
			// Call another biz class
			callBiz(context, "HelloWorldJsonSub");

			BIZ_LOG.info("return value from sub biz >> " + context.getBizMap().getString("calleeValue"));

			// Make return text message
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("Message", "Hello World! I am OkeyDokey!");

			context.setResponseTextString(new Gson().toJson(resultMap));

		} catch (Exception e) {
			throw new BizException(getMessage("ER001"), e);
		}
	}

	@Override
	public void executeFinally(IOkeyDokeyContext context) throws BizException {
		// if exception thrown
		if (context.isBizExceptionThrown()) {
			// Make return error message
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("error", toExceptionString(context.getBizException()));
			context.setResponseTextString(new Gson().toJson(resultMap));

		}
	}

}
