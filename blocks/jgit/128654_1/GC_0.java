	private boolean isEmpty(Path p) {
		try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(p)) {
			boolean hasNext = dirStream.iterator().hasNext();
			return !hasNext;
		} catch (IOException e) {
			return false;
		}
	}

