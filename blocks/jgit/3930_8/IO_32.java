	public static int read(ReadableByteChannel channel
			int len) throws IOException {
		if (len == 0)
			return 0;
		int cnt = 0;
		while (0 < len) {
			int r = channel.read(ByteBuffer.wrap(dst
			if (r <= 0)
				break;
			off += r;
			len -= r;
			cnt += r;
		}
		return cnt != 0 ? cnt : -1;
	}

