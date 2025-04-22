	public File getCommonDirectory() {
		return gitCommonDir != null ? gitCommonDir : gitDir;
	}

	public boolean hasCommonDirectory() {
		return gitCommonDir != null;
	}

