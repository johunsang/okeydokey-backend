package main.java.sample.helloworld;

import java.nio.ByteBuffer;

import org.okeydokey.backend.biz.AbsBiz;
import org.okeydokey.backend.biz.Biz;
import org.okeydokey.backend.context.IOkeyDokeyContext;
import org.okeydokey.backend.exception.BizException;

@Biz(bizId = "HelloWorldBinary")
public class HelloWorldBinary extends AbsBiz {

	@Override
	public void execute(IOkeyDokeyContext context) throws BizException {
		try {
			ByteBuffer inMessageByte = context.getRequestByteBuffer();

			inMessageByte.position(0);
			byte[] tempbyte = new byte[9];
			inMessageByte.get(tempbyte);
			String first = new String(tempbyte);
			BIZ_LOG.info("FIRST VALUE : " + first);

			inMessageByte.position(9);

			int second = inMessageByte.getInt();
			BIZ_LOG.info("SECOND VALUE : " + second);

			ByteBuffer outMessageByte = ByteBuffer.wrap("Hello World! I am OkeyDokey!".getBytes());
			outMessageByte.flip();
			context.setResponseByteBuffer(outMessageByte);

		} catch (Exception e) {
			throw new BizException(getMessage("ER001"), e);
		}
	}

	@Override
	public void executeFinally(IOkeyDokeyContext context) throws BizException {
		// if BizException thrown
		if (context.isBizExceptionThrown()) {
			// Set response message
			ByteBuffer outMessageByte = ByteBuffer.wrap("Error !! ".getBytes());
			outMessageByte.flip();
			context.setResponseByteBuffer(outMessageByte);
		}
	}

}
