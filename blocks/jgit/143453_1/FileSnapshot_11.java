
	private boolean isFileKeyChanged(Object currFileKey) {
		return currFileKey != MISSING_FILEKEY && !currFileKey.equals(fileKey);
	}

	private boolean isSizeChanged(long currSize) {
		return currSize != UNKNOWN_SIZE && currSize != size;
	}
