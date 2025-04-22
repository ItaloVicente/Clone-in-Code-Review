	private synchronized void beginCopyAsIs(ObjectToPack otp)
			throws StoredObjectRepresentationNotAvailableException {
		if (++activeCopyRawData == 1 && activeWindows == 0) {
			try {
				doOpen();
			} catch (IOException thisPackNotValid) {
				StoredObjectRepresentationNotAvailableException gone;
