		long sz = ldr.getSize();
		if (config.getBigFileThreshold() <= sz || Integer.MAX_VALUE < sz)
			throw new LargeObjectException(objId.copy());

		byte[] buf;
		try {
			buf = new byte[(int) sz];
		} catch (OutOfMemoryError noMemory) {
			LargeObjectException e;

			e = new LargeObjectException(objId.copy());
			e.initCause(noMemory);
			throw e;
		}
		InputStream in = ldr.openStream();
		try {
			IO.readFully(in, buf, 0, buf.length);
		} finally {
			in.close();
		}
		return buf;
