	public String getPath() {
		return path;
	}

	public String getAbsolutePath() {
		return repository.getWorkTree().getAbsolutePath() + File.separator
				+ path;
	}

