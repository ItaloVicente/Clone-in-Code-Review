		ObjectLoader ldr = reader.open(id);
		if (!ldr.isLarge())
			return new RawText(ldr.getCachedBytes());

		long sz = ldr.getSize();
		if (Integer.MAX_VALUE <= sz)
			throw new LargeObjectException(id);

		byte[] buf = new byte[(int) sz];
		InputStream in = ldr.openStream();
		try {
			IO.readFully(in, buf, 0, buf.length);
		} finally {
			in.close();
		}
		return new RawText(buf);
