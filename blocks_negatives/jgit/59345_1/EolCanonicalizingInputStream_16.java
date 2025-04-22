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
	}

	/**
	 * @return true if the stream has detected as a binary so far
	 * @since 3.3
	 */
	public boolean isBinary() {
		return isBinary;
	}

	@Override
	public void close() throws IOException {
		in.close();
	}

	private boolean fillBuffer() throws IOException {
		cnt = in.read(buf, 0, buf.length);
		if (cnt < 1)
			return false;
		if (detectBinary) {
			isBinary = RawText.isBinary(buf, cnt);
			detectBinary = false;
			if (isBinary && abortIfBinary)
				throw new IsBinaryException();
		}
		ptr = 0;
		return true;
	}
