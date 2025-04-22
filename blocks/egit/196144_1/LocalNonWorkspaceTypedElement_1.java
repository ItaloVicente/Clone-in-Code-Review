			timestamp = getTimestamp(file);
		}
	}

	private FileTime getTimestamp(File file) {
		try {
			return Files.getLastModifiedTime(file.toPath(),
					LinkOption.NOFOLLOW_LINKS);
		} catch (IOException e) {
			return null;
