	@Override
	public boolean fileExists(String path) {
		return fileExistsAnswers.get(path);
	}

	@Override
	public boolean isDirectory(String path) {
		return isDirectoryAnswers.get(path);
	}

