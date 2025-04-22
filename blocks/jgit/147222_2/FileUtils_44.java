	static Instant lastModifiedInstant(Path path) {
		try {
			return Files.getLastModifiedTime(path
					.toInstant();
		} catch (IOException e) {
			LOG.error(MessageFormat
					.format(JGitText.get().readLastModifiedFailed
			return Instant.ofEpochMilli(path.toFile().lastModified());
		}
	}

