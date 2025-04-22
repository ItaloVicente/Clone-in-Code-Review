
	@Override
	public boolean fileExists(String path) {
		Path filePath = Paths.get(path);
		return Files.exists(filePath);
	}

	@Override
	public boolean isDirectory(String path) {
		Path filePath = Paths.get(path);
		return Files.isDirectory(filePath);
	}
