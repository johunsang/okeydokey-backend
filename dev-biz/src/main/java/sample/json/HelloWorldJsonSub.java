package main.java.sample.json;

import org.okeydokey.backend.biz.AbsBiz;
import org.okeydokey.backend.biz.Biz;
import org.okeydokey.backend.context.IOkeyDokeyContext;
import org.okeydokey.backend.exception.BizException;
import org.okeydokey.backend.utils.BaseUtil;

@Biz(bizId = "HelloWorldJsonSub")
public class HelloWorldJsonSub extends AbsBiz {

	@Override
	public void execute(IOkeyDokeyContext context) throws BizException {
		try {
			BIZ_LOG.info("I am called!! , value from caller : " + context.getBizMap().getString("callerValue"));
			context.getBizMap().put("calleeValue", "I am called!!");
		} catch (Exception e) {
			throw new BizException(BaseUtil.getMessage("ER001"), e);
		}
	}

	@Override
	public void executeFinally(IOkeyDokeyContext context) throws BizException {
	}

}
