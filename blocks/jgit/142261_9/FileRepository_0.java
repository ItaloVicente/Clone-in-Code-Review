	@Override
	public String getIdentifier() {
		File directory = getDirectory();
		if (directory != null) {
			return directory.getPath();
		} else {
			throw new IllegalStateException();
		}
	}

