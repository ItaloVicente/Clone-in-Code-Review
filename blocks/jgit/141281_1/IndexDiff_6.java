	private boolean hasFiles(File directory) {
		try (DirectoryStream<java.nio.file.Path> dir = Files
				.newDirectoryStream(directory.toPath())) {
			return dir.iterator().hasNext();
		} catch (DirectoryIteratorException | IOException e) {
			return false;
		}
	}

