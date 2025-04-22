	private boolean fillBuffer() throws IOException {
		cnt = in.read(buf, 0, buf.length);
		if (cnt < 1)
			return false;
		if (detectBinary) {
			isBinary = RawText.isBinary(buf, cnt);
			detectBinary = false;
			if (isBinary && abortIfBinary)
				throw new IsBinaryException();
