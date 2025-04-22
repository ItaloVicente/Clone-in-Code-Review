		this.in = in;
		this.detectBinary = detectBinary;
		this.abortIfBinary = abortIfBinary;
	}

	@Override
	public int read() throws IOException {
		final int read = read(single, 0, 1);
		return read == 1 ? single[0] & 0xff : -1;
	}

	@Override
	public int read(byte[] bs, final int off, final int len) throws IOException {
		if (len == 0)
			return 0;

		if (cnt == -1)
			return -1;

		int i = off;
		final int end = off + len;

		while (i < end) {
			if (ptr == cnt && !fillBuffer()) {
				break;
			}

			byte b = buf[ptr++];
			if (isBinary || b != '\r') {
				bs[i++] = b;
				continue;
			}

			if (ptr == cnt && !fillBuffer()) {
				bs[i++] = '\r';
				break;
			}

			if (buf[ptr] == '\n') {
				bs[i++] = '\n';
				ptr++;
			} else
				bs[i++] = '\r';
		}

		return i == off ? -1 : i - off;
