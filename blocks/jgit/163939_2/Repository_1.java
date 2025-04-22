	public File getDirectoryChild(String child) {
		File dir = getDirectory();
		return dir == null ? null : new File(getDirectory()
	}

	public File resolveDirectoryChild(String child) {
		return getFS().resolve(getDirectory()
	}

