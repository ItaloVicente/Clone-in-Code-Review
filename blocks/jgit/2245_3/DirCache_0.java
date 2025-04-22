	public boolean isOutdated() throws IOException {
		if (liveFile == null || !liveFile.exists())
			return false;
		return liveFile.lastModified() != lastModified;
	}

