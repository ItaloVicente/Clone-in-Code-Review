
	@Override
	public DirectoryStream<Path> newDirectoryStream(String path, String glob) throws IOException {
		Path dirPath = Paths.get(path);
		return Files.newDirectoryStream(dirPath, glob);
	}
