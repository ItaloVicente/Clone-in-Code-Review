	ByteArrayWindow read(final long pos, int size) throws IOException {
		if (length < pos + size)
			size = (int) (length - pos);
		final byte[] buf = new byte[size];
		IO.readFully(fd.getChannel(), pos, buf, 0, size);
		return new ByteArrayWindow(this, pos, buf);
