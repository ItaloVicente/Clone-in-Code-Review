		if (bufferListener != null) {
			ITextFileBufferManager bufferManager = FileBuffers
					.getTextFileBufferManager();
			if (bufferManager != null) {
				bufferManager.removeFileBufferListener(bufferListener);
				bufferListener = null;
			}
		}
		for (IndexDiffCacheEntry entry : entries.values()) {
