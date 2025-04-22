	private boolean hasDirectoryChildren(File file) {
		try (DirectoryStream<java.nio.file.Path> dir = Files
				.newDirectoryStream(file.toPath())) {
			return dir.iterator().hasNext();
		} catch (DirectoryIteratorException | IOException e) {
			return false;
		}
	}

