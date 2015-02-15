package main.java.sample.after;

import org.okeydokey.backend.biz.AbsBiz;
import org.okeydokey.backend.biz.AfterBiz;
import org.okeydokey.backend.context.IOkeyDokeyContext;
import org.okeydokey.backend.exception.BizException;

@AfterBiz(seq = 1)
public class AfterBiz1 extends AbsBiz {


	@Override
	public void execute(IOkeyDokeyContext context) throws BizException {
		try {
			BIZ_LOG.info("This class will be executed always after biz class >> 1");
		} catch (Exception e) {
			throw new BizException(getMessage("ER001"), e);
		}
	}

	@Override
	public void executeFinally(IOkeyDokeyContext context) throws BizException {
		// If error occurred in execute method, write error response message
		if (context.isBizExceptionThrown()) {
			context.setResponseTextString("after biz error occur!!");
		}
	}
}
