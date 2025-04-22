	public static final byte[] readFully(final InputStream in
			throws IOException {
		byte[] out = new byte[sizeHint];
		int pos = 0;
		while (pos < out.length) {
			int read = in.read(out
			if (read < 0) {
				byte[] res = new byte[pos];
				System.arraycopy(out
				return res;
			}

			pos += read;
		}

		int last = in.read();
		if (last < 0)
			return out;

		TemporaryBuffer.Heap tmp = new TemporaryBuffer.Heap(Integer.MAX_VALUE);
		tmp.write(out);
		tmp.write(last);
		tmp.copy(in);
		return tmp.toByteArray();
	}

