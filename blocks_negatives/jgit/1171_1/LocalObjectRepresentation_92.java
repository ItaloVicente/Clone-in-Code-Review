	@Override
	public void write(final int b) throws IOException {
		out.write(b);
		crc.update(b);
		md.update((byte) b);
		count++;
