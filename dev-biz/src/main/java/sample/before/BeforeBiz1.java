package main.java.sample.before;

import org.okeydokey.backend.biz.AbsBiz;
import org.okeydokey.backend.biz.BeforeBiz;
import org.okeydokey.backend.context.IOkeyDokeyContext;
import org.okeydokey.backend.exception.BizException;

@BeforeBiz(seq = 1)
public class BeforeBiz1 extends AbsBiz {

	@Override
	public void execute(IOkeyDokeyContext context) throws BizException {
		try {
			BIZ_LOG.info("This class will be executed always before biz class  >> 1");
		} catch (Exception e) {
			throw new BizException(getMessage("ER001"), e);
		}
	}

	@Override
	public void executeFinally(IOkeyDokeyContext context) throws BizException {
		// If error occurred in execute method, write error response message 
		if (context.isBizExceptionThrown()) {
			context.setResponseTextString("before biz error occur!!");
		}
	}
}
