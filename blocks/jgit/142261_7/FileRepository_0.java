	@Override
	public String getPath() {
		File directory = getDirectory();
		if (directory != null) {
			return directory.getPath();
		} else {
			throw new IllegalStateException();
		}
	}

