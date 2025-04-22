	synchronized void beginCopyRawData() throws IOException {
		if (++activeCopyRawData == 1 && activeWindows == 0)
			doOpen();
	}

	synchronized void endCopyRawData() {
		if (--activeCopyRawData == 0 && activeWindows == 0)
			doClose();
	}

	synchronized boolean beginWindowCache() throws IOException {
		if (++activeWindows == 1) {
			if (activeCopyRawData == 0)
				doOpen();
			return true;
		}
		return false;
	}

	synchronized boolean endWindowCache() {
		final boolean r = --activeWindows == 0;
		if (r && activeCopyRawData == 0)
			doClose();
		return r;
	}

	private void doOpen() throws IOException {
