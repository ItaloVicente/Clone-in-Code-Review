	/**
	 * Check if the file exists
	 *
	 * @return true if the file exists
	 */
	public boolean fileExists() {
		return !MISSING_FILEKEY.equals(this.fileKey);
	}

