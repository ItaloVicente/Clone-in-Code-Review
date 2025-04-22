		registerBufferListener();
	}

	private void registerBufferListener() {
		bufferListener = new ExternalFileBufferListener();
		ITextFileBufferManager bufferManager = FileBuffers
				.getTextFileBufferManager();
		if (bufferManager != null) {
			bufferManager.addFileBufferListener(bufferListener);
		}
