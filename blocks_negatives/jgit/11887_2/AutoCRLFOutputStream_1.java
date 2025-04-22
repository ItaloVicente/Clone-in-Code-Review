	private int buffer(byte b) throws IOException {
		if (binbufcnt > binbuf.length)
			return 1;
		binbuf[binbufcnt++] = b;
		if (binbufcnt == binbuf.length)
			decideMode();
		return 0;
	}

