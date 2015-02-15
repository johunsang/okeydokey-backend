package main.java.sample.helloworld;

import org.okeydokey.backend.biz.AbsBiz;
import org.okeydokey.backend.biz.Biz;
import org.okeydokey.backend.context.IOkeyDokeyContext;
import org.okeydokey.backend.exception.BizException;

@Biz(bizId = "HelloWorldText")
public class HelloWorldText extends AbsBiz {

	@Override
	public void execute(IOkeyDokeyContext context) throws BizException {
		try {
			String inputMessage = context.getRequestTextString();
			BIZ_LOG.info(inputMessage);
			context.setResponseTextString(inputMessage + " Hello World! I am OkeyDokey!");
		} catch (Exception e) {
			throw new BizException(getMessage("ER001"), e);
		}
	}

	@Override
	public void executeFinally(IOkeyDokeyContext context) throws BizException {
		// if exception thrown
		if (context.isBizExceptionThrown()) {
			// Set response message
			context.setResponseTextString(context.getBizException().toString());
		}
	}
}
