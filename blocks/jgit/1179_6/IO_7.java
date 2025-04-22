	public static ByteBuffer readWholeStream(InputStream in
			throws IOException {
		byte[] out = new byte[sizeHint];
		int pos = 0;
		while (pos < out.length) {
			int read = in.read(out
			if (read < 0)
				return ByteBuffer.wrap(out
			pos += read;
		}

		int last = in.read();
		if (last < 0)
			return ByteBuffer.wrap(out

		TemporaryBuffer.Heap tmp = new TemporaryBuffer.Heap(Integer.MAX_VALUE);
		tmp.write(out);
		tmp.write(last);
		tmp.copy(in);
		return ByteBuffer.wrap(tmp.toByteArray());
	}

