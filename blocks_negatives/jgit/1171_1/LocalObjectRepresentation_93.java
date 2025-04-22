	@Override
	public void write(final byte[] b, final int off, final int len)
			throws IOException {
		out.write(b, off, len);
		crc.update(b, off, len);
		md.update(b, off, len);
		count += len;
