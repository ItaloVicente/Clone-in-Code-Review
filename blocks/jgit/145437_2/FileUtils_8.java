	static Instant lastModifiedInstant(Path path) {
		try {
			return Files.getLastModifiedTime(path
					.toInstant();
		} catch (IOException e) {
			return Instant.ofEpochMilli(path.toFile().lastModified());
		}
	}

