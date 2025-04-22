	synchronized void forceClose() throws IOException {
		activeWindows = 0;
		activeCopyRawData= 0;
		doClose();
	}

