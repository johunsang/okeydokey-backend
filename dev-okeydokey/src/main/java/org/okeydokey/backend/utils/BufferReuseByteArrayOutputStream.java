package org.okeydokey.backend.utils;

import java.io.ByteArrayOutputStream;

public class BufferReuseByteArrayOutputStream extends ByteArrayOutputStream {

	public BufferReuseByteArrayOutputStream(byte[] buffer) {
		super.buf = buffer;
	}

	public byte[] getByteArray() {
		return super.buf;
	}

}
